/*
 * Copyright 2010 Jens Dietrich Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/agpl.html Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package nz.ac.massey.cs.guery.impl;

import java.util.Collection;
import java.util.List;
import java.util.Stack;
import edu.uci.ics.jung.graph.*;
import nz.ac.massey.cs.guery.*;

/**
 * Improved graph query engine supporting multithreading.
 * The number of threads can be set in the constructor, if not set, the number of processors will be used.
 * Each query should be run using a new instance - instances maintain state to keep track of working threads. 
 * @author jens dietrich
 */
public class MultiThreadedGQLImpl<V extends Vertex<E>,E extends Edge<V>> extends GQLImplCore<V,E> {
	
	
	private int activeThreadCount = 0;
	private int numberOfThreads = -1;
	

	public MultiThreadedGQLImpl() {
		super();
	}
	public MultiThreadedGQLImpl(int numberOfThreads) {
		super();
		this.setNumberOfThreads(numberOfThreads);
	}

	public int getNumberOfThreads() {
		return numberOfThreads==-1?Runtime.getRuntime().availableProcessors():numberOfThreads;
	}
	public void setNumberOfThreads(int n) {
		if (n<1) throw new IllegalArgumentException();
		this.numberOfThreads = n;
	}

	@Override
	public void query(final DirectedGraph<V,E> graph, final Motif<V,E> motif, final ResultListener<V,E> listener,final ComputationMode mode,final PathFinder<V, E> finder) {
		prepareGraph(graph,motif);
		
		// initial binding bindings.gotoChildLevel();
		assert !motif.getRoles().isEmpty();
    	final String role = scheduler.getInitialRole(graph, motif); 
    	Collection<V> vertices = graph.getVertices();
    	final int S = vertices.size();
    	final int stepSize = S<100?1:Math.round(S/100);
    	listener.progressMade(0,S);    	
    	
    	// prepare constraints
    	final List<Constraint> constraints = scheduler.getConstraints(graph, motif);
    	
    	// prepare agenda - parallelize only on top level (first role)
    	final Stack<V> agenda = new Stack<V>();
    	vertices = sort(vertices);
    	for (V v:vertices) {
    		agenda.push(v); // reverses order - could use agenda.add(0, v) to retain order
    	}

    	
    	// create workers
    	final ResultListener<V,E> l = mode==ComputationMode.CLASSES_REDUCED?new Reducer(listener):listener;
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
						}
					}
					//Thread.yield();
					if (nextNode!=null) {
						controller.bind(role, nextNode);
						resolve(graph, motif, controller, l,finder);
						counter = S-agenda.size();
			    		if (counter%stepSize==0) {
			    			listener.progressMade(counter,S);
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
    	listener.done();
	}
	@Override
	public String toString() {
		return super.toString() + "[" + this.getNumberOfThreads() + " threads]";
	}

	

}
