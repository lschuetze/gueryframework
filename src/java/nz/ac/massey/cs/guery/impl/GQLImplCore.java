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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import edu.uci.ics.jung.graph.*;
import nz.ac.massey.cs.guery.*;

/**
 * Abstract superclass for GQL implementations. The backtracking / communication
 * with the controller is done here, the top level orchestration has to be done in subclasses.
 * A agendaComparator can be used to arrange the vertices in an order that will then be used in initial bindings.
 * This makes the solver deterministic.
 * @author jens dietrich
 */
public abstract class GQLImplCore<V extends Vertex<E>,E extends Edge<V>> extends Logging implements GQL<V,E> {
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

	public void query(DirectedGraph<V,E> graph,Motif<V,E> motif,ResultListener<V,E> listener,ComputationMode mode) {
		PathFinder<V,E> finder = new BreadthFirstPathFinder<V,E>(true);
		query(graph,motif,listener,mode,finder);
	}
	
	protected Collection<V> sort (Collection<V> vertices) {
		if (agendaComparator!=null) {
			LOG_GQL.debug("Sorting vertices used for initial binding");
			TreeSet<V> sorted = new TreeSet<V>(agendaComparator);
			sorted.addAll(vertices);
			return sorted;
		}
		else {
			return vertices;
		}
		
	}
	@SuppressWarnings("unchecked")
	protected void resolve(DirectedGraph<V,E> graph, Motif<V,E> motif, Controller<V,E> controller, ResultListener<V,E> listener,PathFinder<V, E> finder) {
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
			for (V v:sort(graph.getVertices())) {
				controller.bind(role,v);
				resolve(graph,motif,controller,listener,finder);
			}
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

	private void resolveNextLevel(DirectedGraph<V,E> graph, Motif<V,E> motif, Controller<V,E> controller, ResultListener<V,E> listener, 
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

	protected MotifInstance<V,E> createInstance(Graph<V,E> graph, Motif<V,E> motif, Controller<V,E> bindings) {
		return new MotifInstanceImpl<V,E>(motif,bindings);
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
	
	protected void prepareGraph(DirectedGraph<V,E> graph,Motif<V,E> motif) {
		// process graph
		if(motif.getGraphProcessors().size()!=0){
			for(Processor<V,E> processor:motif.getGraphProcessors()){
				processor.process(graph);
			}
		}

	}

	public ConstraintScheduler<V, E> getScheduler() {
		return scheduler;
	}

	public void setScheduler(ConstraintScheduler<V, E> scheduler) {
		this.scheduler = scheduler;
	}
}
