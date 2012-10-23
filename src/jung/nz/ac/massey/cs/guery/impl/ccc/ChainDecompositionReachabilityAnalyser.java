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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Predicate;
import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.adapters.jung.JungAdapter;
import static nz.ac.massey.cs.guery.impl.Logging.LOG_PATHFINDER_CCC;

/**
 * Reachability analyser implementation based on using chains to compress the reachability matrix.
 * Uses multiple threads to construct labels (references to chain no + positions).
 * TODO: configure no of threads
 * See also: H. V. Jagadish. A compression technique to materialize transitive closure. ACM Trans. Database Syst., 15(4):558Ð598, 1990.
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public class ChainDecompositionReachabilityAnalyser<V,E> implements ReachabilityAnalyser<V, E> {
	

	public ChainDecompositionReachabilityAnalyser(Direction direction,Predicate<E> edgeFilter) {
		super();
		this.direction = direction;
		this.edgeFilter = edgeFilter;
	}
	public ChainDecompositionReachabilityAnalyser(Direction direction) {
		super();
		this.direction = direction;
	}

	private Direction direction = Direction.OUTGOING;
	private GraphAdapter<V, E> graph = null;
	private List<List<V>> chains = new ArrayList<List<V>>();
	private Map<V,List<V>> chainsByVertex = new HashMap<V,List<V>>();
	private Map<V,Integer> positionsInChains = new HashMap<V,Integer>();
	// labels have the form [chain_no,position_in_chain]
	private Map<V,List<int[]>> minDominatingVertexPositions = new HashMap<V,List<int[]>>(); // min position in chain on which v depends
	private Map<V,List<int[]>> maxDominatingVertexPositions = new HashMap<V,List<int[]>>(); // max position in chain on which v depends
	private DirectedGraph<V, E> jungGraph;
	private Predicate<E> edgeFilter = NullFilter.DEFAULT;
	
	// utility - works only for JUNG Adapters
	
	
 	
	@Override
	public GraphAdapter<V, E> getGraph() {
		return graph;
	}

	@Override
	public void setGraph(GraphAdapter<V, E> graph,Predicate<E> edgeFilter) {
		this.graph = graph;
		if (edgeFilter!=null) this.edgeFilter = edgeFilter;
		
		// works only for JUNG adapters !!
		jungGraph = ((JungAdapter<V,E>)this.graph).getGraph();
		
		
		constructChains();
	}

	private void constructChains() {
		
		boolean DEBUG = LOG_PATHFINDER_CCC.isDebugEnabled();
		
		long before = System.currentTimeMillis();
		chains = new ArrayList<List<V>>();
		new RandomChainBuilder<V,E>().buildChains(graph, chains, chainsByVertex, edgeFilter);
		
		
		if (DEBUG) {
			LOG_PATHFINDER_CCC.debug("Finished building chains to compress reachability cache, no of chains built is " + chains.size());
			LOG_PATHFINDER_CCC.debug("Compression ratio (chains/vertices) is " + ((double)chains.size())/((double)graph.getVertexCount()));
		}
		
		int i=0;
		for (List<V> chain:chains) {
			for (i=0;i<chain.size();i++) {
				positionsInChains.put(chain.get(i),i);
			}
		}
		
		
		// print chains for debugging:
//		for (List<V> chain:chains) {
//			System.out.print("[");
//			for (V v:chain) {
//				System.out.print(v);
//				System.out.print(" ");
//			}
//			System.out.println("]");
//		}
		
		if (DEBUG) {
			LOG_PATHFINDER_CCC.debug("Building successor lists: associating vertices with labels (chain id / position in chain)");
		}
		

		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 100, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
		final boolean IN = direction==Direction.INCOMING || direction==Direction.BOTH;
		final boolean OUT = direction==Direction.OUTGOING || direction==Direction.BOTH;
		final Map<V,List<int[]>> minDominatingVertexPositions2 = Collections.synchronizedMap(minDominatingVertexPositions);
		final Map<V,List<int[]>> maxDominatingVertexPositions2 = Collections.synchronizedMap(maxDominatingVertexPositions);
		
		class Labeller implements Runnable {
			private V v = null;
			public Labeller(V v) {
				super();
				this.v = v;
			}

			@Override
			public void run() {
				
				if (OUT) {
					Collection<V> reachable = getReachableVerticesOL(v,false);
					List<int[]> labels = new ArrayList<int[]>();
					minDominatingVertexPositions2.put(v,labels);
					for (int j=0;j<chains.size();j++) {
						List<V> chain = chains.get(j);
						for (int i=0;i<chain.size();i++) {
							//if (spMap.get(chain.get(i))!=null) {
							if (reachable.contains(chain.get(i))) {
								labels.add(new int[]{j,i});
								break;
							}
						}
						// labels.add(new Label(j,-1));   // just dont add any label if there is no successor in this chain
					}
				}
				if (IN) {
					Collection<V> reachable = getReachableVerticesOL(v,true);
					List<int[]> labels = new ArrayList<int[]>();
					maxDominatingVertexPositions2.put(v,labels);
					for (int j=0;j<chains.size();j++) {
						List<V> chain = chains.get(j);
						for (int i=chain.size()-1;i>-1;i--) {
							if (reachable.contains(chain.get(i))) {
								labels.add(new int[]{j,i});
								break;
							}
						}
						// labels.add(new Label(j,-1));   // just dont add any label if there is no successor in this chain
					}
				}
			}
		};
		
		for (Iterator<V> iter = graph.getVertices();iter.hasNext();) {
			//if (ic++%10==0) System.out.println("Done " + ic + '/' + graph.getVertexCount());		
			V v = iter.next();
			executor.execute(new Labeller(v));
		}
		executor.shutdown();
		
		try {
			executor.awaitTermination(1,TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO
			e.printStackTrace();
		}
		
		if (DEBUG) {
			long after = System.currentTimeMillis();
			LOG_PATHFINDER_CCC.debug("Chain compression cache setup finished, this took " + (after-before) + "ms");
			
		}
	}




	@Override
	public Collection<V> getReachableVertices(V v, boolean reverse,boolean includeStart) {
		
		List<V> reachable = new ArrayList<V>();
		boolean startAdded = false;
		
		List<V> chain0 = this.chainsByVertex.get(v);
		if (reverse) {
			for (int[] label:this.maxDominatingVertexPositions.get(v)) {
				List<V> chain = this.chains.get(label[0]);
				if (label[1]>-1) {
					if (chain==chain0) {
						// we don't have to do this if we allow path length 0 !!
						for (V vv:chain.subList(0,label[1]+1)) {
							if (vv!=v) reachable.add(vv);
							else {
								startAdded = true;
								reachable.add(0,vv);
							}
						}
					}
					else {
						reachable.addAll(chain.subList(0,label[1]+1));
					}
				}
			}			
		}
		else {
			for (int[] label:this.minDominatingVertexPositions.get(v)) {
				List<V> chain = this.chains.get(label[0]);
				if (label[1]>-1) { // TODO check whether we still need this !!
					if (chain==chain0) {
						// we don't have to do this if we allow path length 0 !!
						for (V vv:chain.subList(label[1],chain.size())) {
							if (vv!=v) reachable.add(vv);
							else {
								startAdded = true;
								reachable.add(0,vv);
							}
						}
					}
					else {
						reachable.addAll(chain.subList(label[1],chain.size()));
					}
				}
			}
		}
		
		if (includeStart && !startAdded) reachable.add(0,v);
		
		return reachable;
	}

	@Override
	public boolean isReachable(V v1,V v2,boolean reverse) {
		if (reverse) {
			for (int[] label:this.maxDominatingVertexPositions.get(v1)) {
				List<V> chain = this.chains.get(label[0]);
				for (int i=label[1];i>-1;i--) {
					if (v2==chain.get(i)) return true;
				}
			}			
		}
		else {
			for (int[] label:this.minDominatingVertexPositions.get(v1)) {
				List<V> chain = this.chains.get(label[0]);
				for (int i=label[1];i<chain.size();i++) {
					if (v2==chain.get(i)) return true;
				}
			}
		}
		
		return false;
	}

	private void print(List<V> chain) {
		System.out.print("[");
		for (V v:chain) {
			System.out.print(v);
			System.out.print(" ");
		}
		System.out.println("]");
	}
	
	// online version of the algorithm, used to compress cache
	// will be invoked by different threads - do not use central queue
	private Collection<V> getReachableVerticesOL(V v, boolean reverse) {
		Set<V> _visited = new HashSet<V>();
		Queue<V> _queue = new LinkedList<V>();
		_queue.add(v);
		Set<V> set = new HashSet<V>();
		
		V next = null;
		while (_queue.size()>0) {
			next = _queue.poll();
			if (set.add(next)) { 
				if (reverse) {
					for (Iterator<E>  incoming = graph.getInEdges(next,edgeFilter);incoming.hasNext();) {
						V v2 = graph.getStart(incoming.next());
						if (v!=v2) _queue.add(v2);
					}
				}
				else {
					for (Iterator<E>  outgoing = graph.getOutEdges(next,edgeFilter);outgoing.hasNext();) {
						V v2 = graph.getEnd(outgoing.next());
						if (v!=v2) _queue.add(v2);
					}
				}
			}
			
		}
		return set;
	}
}
