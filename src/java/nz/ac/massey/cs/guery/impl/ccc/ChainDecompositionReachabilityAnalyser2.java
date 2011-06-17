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
	private ChainDecompositionReachabilityAnalyser<List<V>,Integer> delegate = null;
	private DirectedGraph<List<V>,Integer> sccGraph = null;
	private Map<V, List<V>> membership = null;
	private Predicate<E> edgeFilter = null;
	
	public ChainDecompositionReachabilityAnalyser2(Direction direction,Predicate<E> filter) {
		super();
		// TODO: support filtering, must be applied when we build the SCC graph!
		delegate = new ChainDecompositionReachabilityAnalyser<List<V>,Integer>(direction);
	}
	
	public ChainDecompositionReachabilityAnalyser2(Direction direction) {
		super();
		delegate = new ChainDecompositionReachabilityAnalyser<List<V>,Integer>(direction);
	}
	
	@Override
	public GraphAdapter<V, E> getGraph() {
		return this.graph;
	}
	
	@Override
	public void setGraph(GraphAdapter<V, E> graph,Predicate<E> edgeFilter) {
		this.graph = graph;
		this.edgeFilter = edgeFilter;
		
		// first, build strong component graph
		TarjansAlgorithm<V,E> sccBuilder = new TarjansAlgorithm<V,E>();
		sccBuilder.buildComponentGraph(graph,edgeFilter);
		sccGraph = sccBuilder.getComponentGraph();
		membership = sccBuilder.getComponentMembership();
		
		delegate.setGraph(new JungAdapter<List<V>, Integer>(sccGraph),null);  // edges are already filtered
	}
	
	
	@Override
	public boolean isReachable(V v1,V v2,boolean reverse) {
		List<V> scc1 = this.membership.get(v1);
		List<V> scc2 = this.membership.get(v2);
		
		// check whether they are in the same SCC
		if (scc1==scc2 && v1!=v2) return true;
		
		// check in reachable SCCs
		return delegate.isReachable(scc1, scc2, reverse);
	}

	@Override
	public Collection<V> getReachableVertices(V v, boolean reverse) {
		List<V> reachable = new ArrayList<V>();
		List<V> scc0 = this.membership.get(v);
		Collection<List<V>> reachableSCCs = this.delegate.getReachableVertices(scc0, reverse);
		
		// add this SCC, exclude v
		for (int i=0;i<scc0.size();i++) {
			V v2 = scc0.get(i);
			if (v!=v2 || scc0.size()>1) reachable.add(v2);
		}
		
		for (List<V> scc:reachableSCCs) {
			for (int i=0;i<scc.size();i++) {
				reachable.add(scc.get(i));
			}				
		}
		return reachable;
	}
}
