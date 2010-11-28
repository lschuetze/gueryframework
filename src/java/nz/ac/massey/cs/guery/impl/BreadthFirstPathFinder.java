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

import java.util.*;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.PathFinder;
import nz.ac.massey.cs.guery.Vertex;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Utility to find paths in graphs. Uses breadth-first search.
 * @author jens dietrich
 */
public class BreadthFirstPathFinder<V extends Vertex<E>,E extends Edge<V>> implements PathFinder<V, E> {
	
	private Map<Predicate,Set> conditionCache = null; 
	
	public BreadthFirstPathFinder(boolean cacheConditions) {
		super();
		if (cacheConditions) {
			conditionCache = new HashMap<Predicate,Set>();
		}
	}

	/* (non-Javadoc)
	 * @see nz.ac.massey.cs.guery.util.PathFinder#findLinks(edu.uci.ics.jung.graph.DirectedGraph, V, int, int, boolean, com.google.common.base.Predicate, boolean)
	 */
	public  Iterator<Path<V,E>> findLinks(DirectedGraph<V,E> g,V start,final int minLength,int maxLength,boolean outgoing,Predicate<E> filter,boolean computeAll) {
		Iterator<Path<V,E>> iter =  new BreadthFirstPathIterator<V, E>(g,start,minLength,maxLength,outgoing,filter,computeAll);
		if (minLength>1) {
			return Iterators.filter(iter,new Predicate<Path<V,E>>() {
				@Override
				public boolean apply(Path<V, E> p) {
					return p.size()>=minLength;
				}});
		}
		else {
			return iter;
		}
	}
	
	/**
	 * Depth first path iterator.
	 * @author jens dietrich
	 * @param <V>
	 * @param <E>
	 */
	class BreadthFirstPathIterator<V extends Vertex<E>,E extends Edge<V>> implements Iterator<Path<V,E>> { 
		private DirectedGraph<V,E> g;
		private V start;
		private int minLength;
		private int maxLength;
		private boolean outgoing;
		private Predicate<E> filter;
		private boolean computeAll;
		private Queue<Path<V,E>> agenda = new LinkedList<Path<V,E>>();
		private Set<E> validEdges = null;
		private Set<V> alreadyVisited = null;
		
		BreadthFirstPathIterator(DirectedGraph<V,E> g,final V start, final int minLength, final int maxLength, final boolean outgoing, final Predicate<E> filter,boolean computeAll) {
			super();
			this.g=g;
			this.start=start;
			this.minLength=minLength;
			this.maxLength=maxLength;
			this.outgoing=outgoing;
			this.filter=filter;
			this.computeAll=computeAll;

			// init cache 
			if (conditionCache!=null) {
				synchronized(BreadthFirstPathFinder.this) {	
					validEdges = (Set<E>) conditionCache.get(filter);
					if (validEdges==null) {
						validEdges = new HashSet<E>();
						for (E e:g.getEdges()) {
							if (filter.apply(e)) {
								validEdges.add(e);
							}
						}
						//System.out.println("condition cache filled for " + filter + ", edges: " + validEdges.size());
						conditionCache.put(filter,validEdges);
					}
				}
			}
			
			if (!computeAll) {
				alreadyVisited = new HashSet<V>();
			}
			
			// init path
			Path<V,E> initialPath = outgoing?new LREmptyPath<V,E>(start):new RREmptyPath<V,E>(start);
			if (minLength==0) {
				agenda.add(initialPath);
				if (!computeAll) {
					alreadyVisited.add(start);
				}
			}
			else {
				addNextGeneration(initialPath);
			}
		}
		@Override
		public boolean hasNext() {
			return !agenda.isEmpty();
		}
		@Override
		public Path<V, E> next() {
			Path<V,E> next = agenda.poll();
			addNextGeneration(next);
			return next;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		private void addNextGeneration(Path<V,E> next) {
			int s = next.size();
			if (maxLength==-1 || s<maxLength) {
				V endPoint = outgoing?next.getEnd():next.getStart();
				s=s+1;
				for (E e:outgoing?endPoint.getOutEdges():endPoint.getInEdges()) {
					V nextEnd = outgoing?e.getEnd():e.getStart();
					// checks:
					// 1. if we have cached edges passing the filter use those, otherwise use filter directly
					// 2. if we computeAll (not recommended!!) just check whether new end point does not occur in parent path - this would mean that there are loops
					// otherwise, check whether we have already visited this vertex (each vertex will only be visited once)
					if ((validEdges==null?filter.apply(e):validEdges.contains(e)) && (computeAll?!next.contains(nextEnd):!alreadyVisited.contains(nextEnd))) {
						agenda.add(next.add(e));
						if (!computeAll && s>=minLength) {
							alreadyVisited.add(nextEnd);
						}
					}
				}
			}
			// System.out.println("agenda size: " + stack.size() + " depth: " + next.size());
		}

	}
	

	
}
