/*
 * Copyright 2011 Jens Dietrich Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/agpl.html Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package nz.ac.massey.cs.guery.impl.ccc;
import java.util.*;
import com.google.common.base.Predicate;
import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.adapters.jung.JungAdapter;

/**
 * Improved reachability analyser. Uses chain compression for the (acyclic) graph consisting of the strongly connected
 * components. This graph is built using Tarjan's algorithm. 
 * Uses chain compression on the strong component graph.  
 * @author jens dietrich
 */
public class ChainDecompositionReachabilityAnalyser2<V,E> implements ReachabilityAnalyser<V, E> {
	
	private GraphAdapter<V, E> graph = null;
	private ChainDecompositionReachabilityAnalyser<Set<V>,Integer> delegate = null;
	private DirectedGraph<Set<V>,Integer> sccGraph = null;
	private Map<V, Set<V>> membership = null;
	
	
	public ChainDecompositionReachabilityAnalyser2(Direction direction) {
		super();
		delegate = new ChainDecompositionReachabilityAnalyser<Set<V>,Integer>(direction);
	}
	
	@Override
	public GraphAdapter<V, E> getGraph() {
		return this.graph;
	}
	
	@Override
	public void setGraph(GraphAdapter<V, E> graph,Predicate<E> edgeFilter) {
		this.graph = graph;
		
		// first, build strong component graph
		TarjansAlgorithm<V,E> sccBuilder = new TarjansAlgorithm<V,E>();
		sccBuilder.buildComponentGraph(graph,edgeFilter);
		sccGraph = sccBuilder.getComponentGraph();
		membership = sccBuilder.getComponentMembership();
		
		delegate.setGraph(new JungAdapter<Set<V>, Integer>(sccGraph),null);  // edges are already filtered
	}
	
	
	@Override
	public boolean isReachable(V v1,V v2,boolean reverse) {
		Set<V> scc1 = this.membership.get(v1);
		Set<V> scc2 = this.membership.get(v2);
		
		// check whether they are in the same SCC
		if (scc1==scc2 && v1!=v2) return true;
		
		// check in reachable SCCs
		return delegate.isReachable(scc1, scc2, reverse);
	}

	@Override
	public Collection<V> getReachableVertices(V v, boolean reverse,boolean includeStart) {
		List<V> reachable = new ArrayList<V>();
		Set<V> scc0 = this.membership.get(v);
		Collection<Set<V>> reachableSCCs = this.delegate.getReachableVertices(scc0, reverse,false);
		
		// add this SCC, exclude v
		for (V v2:scc0) {
			if (v!=v2 || scc0.size()>1 || (scc0.size()==1 && includeStart)) reachable.add(v2);
		}
		
		for (Set<V> scc:reachableSCCs) {
			if (scc!=scc0) {
				reachable.addAll(scc);

			}
		}
		return reachable;
	}
}
