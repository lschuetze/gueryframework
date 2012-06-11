/**
 * Copyright 2010 Jens Dietrich 
 * @license
 */

package nz.ac.massey.cs.guery.impl;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import nz.ac.massey.cs.guery.*;

/**
 * Improved graph query engine supporting multithreading.
 * The number of threads can be set in the constructor, if not set, the number of processors will be used.
 * Each query should be run using a new instance - instances maintain state to keep track of working threads. 
 * @author jens dietrich
 */
public class MultiThreadedGQLImpl<V,E> extends GQLImplCore<V,E> {
	
	private static int session_counter = 0;
	
	private int activeThreadCount = 0;
	private int numberOfThreads = -1;
	// only used if ignoreVariants is false - if this flag is false, for some queries some variants (computed in different threads)
	// could be returned. However, it can be faster to do do
	private boolean removeAllVariants = true;
	

	public MultiThreadedGQLImpl() {
		super();
	}
	public MultiThreadedGQLImpl(int numberOfThreads) {
		super();
		this.setNumberOfThreads(numberOfThreads);
	}
	
	public boolean isRemoveAllVariants() {
		return removeAllVariants;
	}
	public void setRemoveAllVariants(boolean removeAllVariants) {
		this.removeAllVariants = removeAllVariants;
	}
	public int getNumberOfThreads() {
		return numberOfThreads==-1?Runtime.getRuntime().availableProcessors():numberOfThreads;
	}
	public void setNumberOfThreads(int n) {
		if (n<1) throw new IllegalArgumentException();
		this.numberOfThreads = n;
	}

	@Override
	public void query(final GraphAdapter<V,E> graph, final Motif<V,E> motif, final ResultListener<V,E> listener,final ComputationMode mode,final PathFinder<V, E> finder) {
		prepareGraph(graph,motif);
		
		// initial binding bindings.gotoChildLevel();
		assert !motif.getRoles().isEmpty();
    	
    	final Iterator<V> vertices = graph.getVertices(agendaComparator);
    	int Stmp = -1;
    	try {
    		Stmp = graph.getVertexCount();
    	}
    	catch (UnsupportedOperationException x) {
    		Stmp = -1; // not all adapters will support this !
    	}
    	final int S = Stmp;
    	final int stepSize = S<100?1:Math.round(S/100);
    	   	
    	// prepare constraints
    	final List<Constraint> constraints = scheduler.getConstraints(graph, motif);
    	final String role = scheduler.getInitialRole(graph, motif);  
    	
    	final GQLMonitor monitor = new GQLMonitor() ;
    	monitor.setVertexCount(S);
    	
    	
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
		try {
			int id = 0;
			synchronized (MultiThreadedGQLImpl.class) {
				id = session_counter++;
			}
			ObjectName name = new ObjectName("nz.ac.massey.cs.guery.impl:type=GQLMonitor,id=instance-"+id);
			mbs.registerMBean(monitor, name); 
		} catch (Exception x) {
			LOG_GQL.error("Registering mbean for monitoring failed",x);
		} 
	    
    	
    	// create workers
    	final ResultListener<V,E> l = mode==ComputationMode.CLASSES_REDUCED?new Reducer(listener):listener;
    	l.progressMade(0,S); 
    	
    	final class Counter {
    		int value = 0;
    		void add() {
    			this.value=this.value+1;
    		}
    	}
    	final Counter counter = new Counter();
    	
    	Runnable worker = new Runnable() {
			@Override
			public void run() {
				V nextNode = null;
				Controller<V,E> controller = createController(motif,constraints,mode);
				while (!cancel && vertices.hasNext()) {
					nextNode = null;
					synchronized (vertices) {
						if (vertices.hasNext()) {
							nextNode = vertices.next();
							counter.add();
							if (S>-1)
								monitor.processedOneVertex();
						}
					}
					//Thread.yield();
					if (nextNode!=null) {
						controller.bind(role, nextNode);
						resolve(graph, motif, controller, l,finder);
			    		if (counter.value%stepSize==0) {
			    			l.progressMade(counter.value,S);
			    		}
			    		controller.reset();
					}
				}
				synchronized (MultiThreadedGQLImpl.this) {
					activeThreadCount = activeThreadCount-1;
				}
			}
    	};
    	
    	// start resolver
    	int N = getNumberOfThreads();
    	activeThreadCount = N;
    	for (int i=0;i<N;i++) {
    		new Thread(worker,"guery thread " + i).start();
    	}
    	
    	// the main thread keeps on running so that the listener can be notified that all threads have finished
    	while (activeThreadCount>0) {
    		try {
				Thread.sleep(100); // TODO make this configurable
			} catch (InterruptedException e) {
				// TODO exception handling
				e.printStackTrace();
			}
    		finally {
    			graph.closeIterator(vertices);
    		}
    	}
    	l.done();
	}
	@Override
	public String toString() {
		return super.toString() + "[" + this.getNumberOfThreads() + " threads]";
	}

	

}
