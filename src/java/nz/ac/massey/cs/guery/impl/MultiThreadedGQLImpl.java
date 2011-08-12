/**
 * Copyright 2010 Jens Dietrich 
 * @license
 */

package nz.ac.massey.cs.guery.impl;

import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
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
    	
    	Iterator<V> vertices = graph.getVertices(agendaComparator);
    	final int S = graph.getVertexCount(); // TODO handle unsupported operation exception
    	final int stepSize = S<100?1:Math.round(S/100);
    	   	
    	
    	// prepare constraints
    	final List<Constraint> constraints = scheduler.getConstraints(graph, motif);
    	final String role = scheduler.getInitialRole(graph, motif);  
    	
    	// prepare agenda - parallelize only on top level (first role)
    	final Stack<V> agenda = new Stack<V>();
    	while (vertices.hasNext()) {
    		agenda.push(vertices.next()); // reverses order - could use agenda.add(0, v) to retain order
    	}
    	
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
	    
		
		
		
    	graph.closeIterator(vertices);
    	// in general, aggregation needs to be enforced across different threads
    	// this is done by wrapping the controller
//    	ResultListener<V,E> _aggregationController = new ResultListener<V,E>() {
//    		Set<Object> instanceIdentifiers = new HashSet<Object>();
//    		GroupByAggregation<V,E> groupBy = new GroupByAggregation<V,E>();
//			@Override
//			public void done() {
//				listener.done();
//				instanceIdentifiers = null;
//			}
//
//			@Override
//			public synchronized boolean found(MotifInstance<V,E> instance) {
//				// check whether there already is a variant for this instance
//				if (instanceIdentifiers.add(groupBy.getGroupIdentifier(instance))) {
//					return listener.found(instance);
//				}
//				return true;
//			}
//
//			@Override
//			public void progressMade(int progress, int total) {
//				listener.progressMade(progress, total);
//			}
//    		
//    	} ;
    	
    	// create workers
    	final ResultListener<V,E> l = mode==ComputationMode.CLASSES_REDUCED?new Reducer(listener):listener;
    	l.progressMade(0,S); 
    	
    	Runnable worker = new Runnable() {
			@Override
			public void run() {
				V nextNode = null;
				int counter;
				Controller<V,E> controller = createController(motif,constraints,mode);
				while (!cancel && !agenda.isEmpty()) {
					nextNode = null;
					synchronized (agenda) {
						if (!agenda.isEmpty()) {
							nextNode = agenda.pop();
							monitor.setUnProcessedVertexCount(agenda.size());
						}
					}
					//Thread.yield();
					if (nextNode!=null) {
						controller.bind(role, nextNode);
						resolve(graph, motif, controller, l,finder);
						counter = S-agenda.size();
			    		if (counter%stepSize==0) {
			    			l.progressMade(counter,S);
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
    		new Thread(worker).start();
    	}
    	
    	// the main thread keeps on running so that the listener can be notified that all threads have finished
    	while (activeThreadCount>0) {
    		try {
				Thread.sleep(100); // TODO make this configurable
			} catch (InterruptedException e) {
				// TODO exception handling
				e.printStackTrace();
			}
    	}
    	l.done();
	}
	@Override
	public String toString() {
		return super.toString() + "[" + this.getNumberOfThreads() + " threads]";
	}

	

}
