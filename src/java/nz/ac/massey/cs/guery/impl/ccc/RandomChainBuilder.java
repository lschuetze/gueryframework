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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;

import nz.ac.massey.cs.guery.GraphAdapter;

/**
 * Simple chain builder. Building chains starts at random positions, and then tried to grow chains in 
 * both directions. Note that chains can have gaps. 
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */

public class RandomChainBuilder<V,E> implements ChainBuilder<V, E> {

	@Override
	public void buildChains(GraphAdapter<V,E> graph,List<List<V>> chains,Map<V,List<V>> chainsByVertex,Predicate<E> edgeFilter) {
		Set<V> agenda = new HashSet<V>();
		Set<V> visited = new HashSet<V>(); 
		List<V> queue = new ArrayList<V>(); 
		
		for (Iterator<V> iter = graph.getVertices();iter.hasNext();) {
			agenda.add(iter.next());
		}
		
		while (agenda.size()>0) {
			Iterator<V> iter = agenda.iterator();
			V v = iter.next();
			iter.remove();
			// new chain
			List<V> chain = new LinkedList<V>(); 
			chains.add(chain);
			chain.add(v);
			chainsByVertex.put(v,chain);
			
			this.addSuccessor(graph,chain,v,agenda,visited,queue,chainsByVertex,edgeFilter);
			this.addPredecessor(graph,chain,v,agenda,visited,queue,chainsByVertex,edgeFilter);
		}
		
//		long st = System.currentTimeMillis();
//		for (List<V> chain1:chains) {
//			for (List<V> chain2:chains) {
//				for (Iterator<E> iter1=graph.getOutEdges(chain1.get(chain1.size()-1));iter1.hasNext();) {
//					if (chain2.size()>1 && iter1.next()==chain2.get(1)) {
//						System.out.print("Can merge ");
//						print(chain1);
//						System.out.print(" and ");
//						print(chain2);
//						System.out.println();
//					}
//				}
//			}
//		}
//		long tt = System.currentTimeMillis();
//		System.out.println("Optimising chains took " + (tt-st) + "ms");
		
		System.out.println("Finished building chains, no of chains built is " + chains.size());
	}

	private void addSuccessor(GraphAdapter<V,E> graph,List<V> chain,V v,Set<V> agenda,Set<V> visited,List<V> queue,Map<V,List<V>> chainsByVertex,Predicate<E> edgeFilter) {
		visited.clear();
		queue.clear();
		queue.add(v);
		
		while (!queue.isEmpty()) {
			V next = queue.remove(0);
			if (agenda.remove(next)) { // not yet in a chain, add to current chain
				chain.add(next);
				chainsByVertex.put(next,chain);
				addSuccessor(graph,chain,next,agenda,visited,queue,chainsByVertex,edgeFilter); // recursion
				return;
			}
			else if (agenda.size()>0) {  // try with next generation
				for (Iterator<E> iter = graph.getOutEdges(next,edgeFilter);iter.hasNext();) {
					V gChild = graph.getEnd(iter.next());
					if (!visited.contains(gChild)) { // control cycles
						queue.add(gChild);
						visited.add(gChild);
					}
				}
			}
		}
		
	}
	
	private void addPredecessor(GraphAdapter<V,E> graph,List<V> chain,V v,Set<V> agenda,Set<V> visited,List<V> queue,Map<V,List<V>> chainsByVertex,Predicate<E> edgeFilter) {
		visited.clear();
		queue.clear();
		queue.add(v);
		
		while (!queue.isEmpty()) {
			V next = queue.remove(0);
			if (agenda.remove(next)) { // not yet in a chain, add to current chain
				chain.add(0,next);
				chainsByVertex.put(next,chain);
				addPredecessor(graph,chain,next,agenda,visited,queue,chainsByVertex,edgeFilter); // recursion
				return;
			}
			else if (agenda.size()>0) {  // try with next generation
				for (Iterator<E> iter = graph.getInEdges(next,edgeFilter);iter.hasNext();) {
					V gChild = graph.getStart(iter.next());
					if (!visited.contains(gChild)) { // control cycles
						queue.add(gChild);
						visited.add(gChild);
					}
				}
			}
		}
		
	}
}
