/**
 * Copyright 2010 Jens Dietrich 
 * @license
 */

package nz.ac.massey.cs.guery.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import nz.ac.massey.cs.guery.*;

/**
 * Abstract superclass for GQL implementations. The backtracking / communication
 * with the controller is done here, the top level orchestration has to be done in subclasses.
 * A agendaComparator can be used to arrange the vertices in an order that will then be used in initial bindings.
 * This makes the solver deterministic.
 * @author jens dietrich
 */
public abstract class GQLImplCore<V,E> extends Logging implements GQL<V,E> {
	public GQLImplCore() {
		super();
	}

	protected boolean cancel = false;
	protected ConstraintScheduler<V,E> scheduler = new SimpleScheduler<V,E>();
	protected Comparator<V> agendaComparator = null;


	public Comparator<V> getAgendaComparator() {
		return agendaComparator;
	}

	public void setAgendaComparator(Comparator<V> agendaComparator) {
		this.agendaComparator = agendaComparator;
	}

	public void query(GraphAdapter<V,E> graph,Motif<V,E> motif,ResultListener<V,E> listener,ComputationMode mode) {
		PathFinder<V,E> finder = new BreadthFirstPathFinder<V,E>(true);
		query(graph,motif,listener,mode,finder);
	}
	
	@SuppressWarnings("unchecked")
	protected void resolve(GraphAdapter<V,E> graph, Motif<V,E> motif, Controller<V,E> controller, ResultListener<V,E> listener,PathFinder<V, E> finder) {
		if (cancel) return;

		// check for termination
		if (controller.isDone()) {
			MotifInstance<V,E> instance = createInstance(graph,motif,controller);
			if (!listener.found(instance)) {
				this.cancel();
			}
			return;
		}
		
		// back jumping
		if (controller.isInJumpBackMode()) {
			return;
		}
		
		// recursion
		Constraint nextConstraint = controller.next();  // one level down
		
		
		if (LOG_GQL.isDebugEnabled()) {
			LOG_GQL.debug("recursion level "+controller.getPosition()+", resolving: "+nextConstraint);
		}
		
		if (nextConstraint instanceof PropertyConstraint) {
			PropertyConstraint constraint = (PropertyConstraint)nextConstraint;
			boolean result = false;
			if (constraint.isSingleRole()) {
				Object vertexOrPath = controller.lookupAny(constraint.getFirstRole());
				if (vertexOrPath!=null) {
					result = constraint.check(vertexOrPath);
				}
				else {
					LOG_GQL.warn("encountered unresolved role "+constraint.getFirstRole()+", cannot resolve: "+constraint);
				}
			}
			else {
				Map<String,Object> bind = new HashMap<String,Object>(constraint.getRoles().size());
				for (Object role:constraint.getRoles()) {
					Object vertexOrPath = controller.lookupAny(role.toString());
					if (vertexOrPath!=null) {
						bind.put(role.toString(),vertexOrPath);
					}
					else {
						LOG_GQL.warn("encountered unresolved role "+constraint.getFirstRole()+", cannot resolve: "+constraint);
					}	
				}
				result = constraint.check(bind); 
			}
			if (result) {
				resolve(graph,motif,controller,listener,finder);
			}

		}
		else if (nextConstraint instanceof LoopInstruction) {
			// full loop - this is the only way to progress
			LoopInstruction in = (LoopInstruction)nextConstraint; 
			String role = in.getRole();
			Iterator<V> vertices = graph.getVertices(agendaComparator);
			while (vertices.hasNext()) {
				controller.bind(role,vertices.next());
				resolve(graph,motif,controller,listener,finder);
			}
			graph.closeIterator(vertices);
		}
		else if (nextConstraint instanceof PathConstraint<?,?>) {
			PathConstraint<V,E> constraint = (PathConstraint<V,E>)nextConstraint; 
			String sourceRole = constraint.getSource();
			String targetRole = constraint.getTarget();
			V source = (V)controller.lookupVertex(sourceRole);
			V target = (V)controller.lookupVertex(targetRole);
			
			if (source!=null && target!=null) {
				Iterator<? extends Path<V,E>> iter = constraint.check(graph, source, target,finder); // path or edge
				resolveNextLevel(graph,motif,controller,listener,iter,target,sourceRole,constraint,finder);
			}
			else if (source==null && target!=null) {
				Iterator<? extends Path<V,E>> iter = constraint.getPossibleSources(graph,target,finder);
				resolveNextLevel(graph,motif,controller,listener,iter,target,sourceRole,constraint,finder);
				
			}
			else if (source!=null && target==null) {
				Iterator<? extends Path<V,E>> iter =constraint.getPossibleTargets(graph,source,finder);
				resolveNextLevel(graph,motif,controller,listener,iter,source,targetRole,constraint,finder);
			}
			else {
				throw new IllegalStateException("cannot resolve linke constraints with two open slots");
			}
		}
		controller.backtrack(); // one level up

	}

	private void resolveNextLevel(GraphAdapter<V,E> graph, Motif<V,E> motif, Controller<V,E> controller, ResultListener<V,E> listener, 
			Iterator<? extends Path<V,E>> iter,V end1,String end2Role,PathConstraint<V,E> constraint,PathFinder<V, E> finder) {
		
		while (iter.hasNext()) {
			Path<V,E> path = iter.next();
			controller.bind(constraint.getRole(),path);
			if (path.getStart()==end1) {
				controller.bind(end2Role,path.getEnd());
				resolve(graph,motif,controller,listener,finder);
			}
			else if (path.getEnd()==end1) {
				controller.bind(end2Role,path.getStart());
				resolve(graph,motif,controller,listener,finder);
			}
		}
	}

	protected MotifInstance<V,E> createInstance(GraphAdapter<V,E> graph, Motif<V,E> motif, Controller<V,E> bindings) {
		return new MotifInstanceImpl<V,E>(graph,motif,bindings);
	}

	@Override
	public void reset() {
	}

	@Override
	public void cancel() {
		cancel = true;
	}

	protected Controller<V,E> createController(Motif<V,E> motif,List<Constraint> constraints,ComputationMode mode) {
		return mode==ComputationMode.ALL_INSTANCES?new Controller<V,E>(motif,constraints):new BackJumpingController<V,E>(motif,constraints);
	}
	
	protected void prepareGraph(GraphAdapter<V,E> graph,Motif<V,E> motif) {
		// process graph
		if(motif.getGraphProcessors().size()!=0){
			for(Processor<V,E> processor:motif.getGraphProcessors()){
				processor.process(graph);
			}
		}

	}
}
