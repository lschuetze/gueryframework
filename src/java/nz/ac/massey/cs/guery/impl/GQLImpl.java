/**
 * Copyright 2010 Jens Dietrich 
 * @license
 */


package nz.ac.massey.cs.guery.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import nz.ac.massey.cs.guery.*;

/**
 * Improved graph query engine.
 * @author jens dietrich
 */
public class GQLImpl<V,E> extends GQLImplCore<V,E> {
	public GQLImpl() {
		super();
	}


	@Override
	public void query(GraphAdapter<V,E> graph, Motif<V,E> motif, ResultListener<V,E> listener,ComputationMode mode,PathFinder<V, E> finder) {
		prepareGraph(graph,motif);
		
		// initial binding bindings.gotoChildLevel();
		assert !motif.getRoles().isEmpty();
    	String role = motif.getRoles().get(0);  
    	Iterator<V> vertices = graph.getVertices(agendaComparator);
    	int S = graph.getVertexCount(); // TODO handle unsupported operation exception
    	int counter = 0;
    	int stepSize = S<100?1:Math.round(S/100);
    	
    	final ResultListener<V,E> listener2 = mode==ComputationMode.CLASSES_REDUCED?new Reducer(listener):listener;
    	
    	listener2.progressMade(0,S);
    	
    	// prepare constraints
    	List<Constraint> constraints = scheduler.getConstraints(graph, motif);
    	
    	// start resolver
    	Controller<V,E> controller = createController(motif,constraints,mode);
    	
    	while (vertices.hasNext()){
    		V v = vertices.next();
    		controller.bind(role, v);
    		counter = counter+1;
    		resolve(graph, motif, controller, listener2,finder);
    		if (counter%stepSize==0) {
    			listener2.progressMade(counter,S);
    		}
    		controller.reset();
	    }
    	
    	graph.closeIterator(vertices);
    	
    	listener2.done();

	}


}
