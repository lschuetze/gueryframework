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
import edu.uci.ics.jung.graph.*;
import nz.ac.massey.cs.guery.*;

/**
 * Improved graph query engine.
 * @author jens dietrich
 */
public final class GQLImpl<V extends Vertex<E>,E extends Edge<V>> extends GQLImplCore<V,E> {
	public GQLImpl() {
		super();
	}


	@Override
	public void query(DirectedGraph<V,E> graph, Motif<V,E> motif, ResultListener<V,E> listener,ComputationMode mode,PathFinder<V, E> finder) {
		prepareGraph(graph,motif);
		
		// initial binding bindings.gotoChildLevel();
		assert !motif.getRoles().isEmpty();
    	String role = scheduler.getInitialRole(graph, motif); 
    	Collection<V> vertices = graph.getVertices();
    	vertices = sort(vertices);
    	int S = vertices.size();
    	int counter = 0;
    	int stepSize = S<100?1:Math.round(S/100);
    	
    	final ResultListener<V,E> listener2 = mode==ComputationMode.CLASSES_REDUCED?new Reducer(listener):listener;
    	
    	listener2.progressMade(0,S);
    	
    	// prepare constraints
    	List<Constraint> constraints = scheduler.getConstraints(graph, motif);
    	
    	// start resolver
    	Controller<V,E> controller = createController(motif,constraints,mode);
    	
    	for(V v:vertices){
    		controller.bind(role, v);
    		counter = counter+1;
    		resolve(graph, motif, controller, listener2,finder);
    		if (counter%stepSize==0) {
    			listener2.progressMade(counter,S);
    		}
    		controller.reset();
	    }
    	
    	listener2.done();

	}


}
