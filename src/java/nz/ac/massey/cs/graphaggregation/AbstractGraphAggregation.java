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

package nz.ac.massey.cs.graphaggregation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Vertex;

public abstract class AbstractGraphAggregation<V1 extends Vertex<E1>, E1 extends Edge<V1>, V2 extends Vertex<E2>, E2 extends Edge<V2>> implements GraphAggregation<V1,E1,V2,E2>{
	static Supplier<Set> SetSupplier = new Supplier<Set>() {
		@Override
		public Set get() {
			return new HashSet();
		}
		
	};
	
	public class Pair {
		private Pair(Object o1,Object o2) {
			super();
			source=o1;
			target=o2;
		}
		Object source;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((source == null) ? 0 : source.hashCode());
			result = prime * result
					+ ((target == null) ? 0 : target.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (source == null) {
				if (other.source != null)
					return false;
			} else if (!source.equals(other.source))
				return false;
			if (target == null) {
				if (other.target != null)
					return false;
			} else if (!target.equals(other.target))
				return false;
			return true;
		}
		Object target;
		private AbstractGraphAggregation getOuterType() {
			return AbstractGraphAggregation.this;
		}
	}
	
	private DirectedGraph<V1,E1> source = null;
	private DirectedGraph<V2,E2> target = null;
	
	// maps to store parent-children associations
	private SetMultimap<E2,E1> edgeParent2Children = HashMultimap.create();
	private SetMultimap<V2,V1> vertexParent2Children = HashMultimap.create();
	private Map<E1,E2> edgeChildren2Parent = new HashMap<E1,E2>();
	private Map<V1,V2> vertexChildren2Parent = new HashMap<V1,V2>();
	
	public void setSource(DirectedGraph<V1,E1> source) {
		this.source = source;
	}
	public DirectedGraph<V1,E1> getSource() {
		return source;
	}
	public DirectedGraph<V2,E2> getTarget() {
		return target;
	}
	public boolean isReady() {
		return target!=null;
	}

	public Collection<E1> getChildren(E2 edge) {
		if (target==null) notYetAvailable();
		return this.edgeParent2Children.get(edge);
	}
	public E2 getParent(E1 edge) {
		if (target==null) notYetAvailable();
		return this.edgeChildren2Parent.get(edge);
	}
	public Collection<V1> getChildren(V2 vertex) {
		if (target==null) notYetAvailable();
		return this.vertexParent2Children.get(vertex);
	}

	public V2 getParent(V1 vertex){
		if (target==null) notYetAvailable();
		return this.vertexChildren2Parent.get(vertex);
	}
	
	private void notYetAvailable() {
		throw new IllegalStateException("The aggregated graph is not yet available");
	}
	
	public void aggregate(AggregationListener listener) {

		assert(source!=null);
		
		int total = 2*(this.source.getEdgeCount()+this.source.getVertexCount());
		int stepCount = 0;
		int stepSize = total<200?1:total/100;
		
		edgeParent2Children.clear();
		vertexParent2Children.clear();
		edgeChildren2Parent.clear();
		vertexChildren2Parent.clear();
		
		target = new DirectedSparseGraph<V2,E2>();
		
		// group vertices
		listener.activityStarted("analysing vertices");
		ArrayListMultimap<Object,V1> vMap = ArrayListMultimap.create();
		for (V1 vertex:source.getVertices()) {
			Object id = this.getGroupIdentifier(vertex);
			vMap.put(id,vertex);
			// progress
			stepCount=stepCount+1;
			if (stepCount%stepSize==0) listener.progressMade(stepCount,total);
		}
		
		// build new nodes
		listener.activityStarted("building new vertices");
		Map<Object,V2> newVerticesById = new HashMap<Object,V2>();
		for (Object id:vMap.keySet()) {
			Collection<V1> verticesInGroup = vMap.get(id);
			V2 vertex = buildVertex(id,verticesInGroup);
			newVerticesById.put(id,vertex);
			this.vertexParent2Children.putAll(vertex,verticesInGroup);
			for (V1 part:verticesInGroup) {
				this.vertexChildren2Parent.put(part,vertex);
			}
			target.addVertex(vertex);
			// progress
			stepCount=stepCount+1;
			if (stepCount%stepSize==0) listener.progressMade(stepCount,total);
		}
		
		// group edges
		listener.activityStarted("analysing edges");
		ArrayListMultimap<Pair,E1> eMap = ArrayListMultimap.create();
		for (E1 edge:source.getEdges()) {
			Pair id = new Pair(getGroupIdentifier(edge.getStart()),getGroupIdentifier(edge.getEnd()));
			eMap.put(id,edge);
			// progress
			stepCount=stepCount+1;
			if (stepCount%stepSize==0) listener.progressMade(stepCount,total);
		}
		
		// build new edges
		listener.activityStarted("building new edges");
		for (Pair ids:eMap.keySet()) {
			Collection<E1> edgesInGroup = eMap.get(ids);
			V2 s = newVerticesById.get(ids.source);
			V2 t = newVerticesById.get(ids.target);
			E2 edge = buildEdge(edgesInGroup,s,t);
			this.edgeParent2Children.putAll(edge,edgesInGroup);
			for (E1 part:edgesInGroup) {
				this.edgeChildren2Parent.put(part,edge);
			}
			
			// hook together
			edge.setStart(s);
			edge.setEnd(t);
			target.addEdge(edge,s,t);
			
			// progress
			stepCount=stepCount+1;
			if (stepCount%stepSize==0) listener.progressMade(stepCount,total);
		}
		listener.activityStarted("aggregation completed");
		listener.done();
		
		reset();
	}
	
	protected abstract E2 buildEdge(Collection<E1> edgesInGroup, V2 source, V2 target);
	protected abstract V2 buildVertex(Object id, Collection<V1> verticesInGroup);
	protected abstract Object getGroupIdentifier(V1 vertex); 
	
	// clean up after aggregation, release temporary data structures used
	protected void reset() {};
}
