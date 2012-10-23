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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.MapMaker;
import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.PathFinder;
import nz.ac.massey.cs.guery.impl.BreadthFirstPathFinder;
import nz.ac.massey.cs.guery.util.LazyPath;

/**
 * A path finder that uses precomputed and cached information about the reachability of vertices.
 * The actual path information (=the list of edges) is computed lazy. I.e., if a path is returned  it means that there is a path, 
 * but the actual path is only computed when getEdges() is called for the first time. 
 * This path finder only works if minLength==1 and maxLength==-1 (unbound), otherwise the (default) BreadthFirstPathFinder is used (by delegation). 
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public class CCCPathFinder<V, E> implements PathFinder<V, E> {
	
	public final static Logger LOG_PATHFINDER_CCC = Logger.getLogger(CCCPathFinder.class);
	
	// use a static instance of we cannot use the reachability cache
	private static BreadthFirstPathFinder delegate = new BreadthFirstPathFinder(false);
	
	private static Map<Key,ReachabilityAnalyser> cache = new MapMaker()
		.softValues()
		.makeComputingMap(new Function<Key,ReachabilityAnalyser>(){
			@Override
			public ReachabilityAnalyser apply(Key key) {
				return buildCache(key);
			}});
	

	// data structure to be used to access cached reachability data
	static class Key {
		private Predicate filter = null;
		private GraphAdapter g = null;
		
		public Key(GraphAdapter g, Predicate filter) {
			super();
			this.filter = filter;
			this.g = g;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((filter == null) ? 0 : filter.hashCode());
			result = prime * result + ((g == null) ? 0 : g.hashCode());
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
			Key other = (Key) obj;
			if (filter == null) {
				if (other.filter != null)
					return false;
			} else if (!filter.equals(other.filter))
				return false;
			if (g == null) {
				if (other.g != null)
					return false;
			} else if (!g.equals(other.g))
				return false;
			return true;
		}

	}
	
	
	@Override
	public Iterator<Path<V, E>> findLinks(final GraphAdapter<V, E> g, final V start,int minLength, int maxLength, final boolean outgoing, final Predicate<E> filter, boolean computeAll) {
		if ((minLength!=1 && minLength!=0) || maxLength>-1 || computeAll) {
//			if (LOG_PATHFINDER_CCC.isDebugEnabled()) {
//				LOG_PATHFINDER_CCC.debug("Cannot use " + this + " for findLinks with this set of parameters, delegate to " + delegate);
//			}
			return (Iterator<Path<V, E>>)delegate.findLinks(g, start, minLength, maxLength, outgoing, filter, computeAll);
		}
		else {
			Key key = new Key(g,filter);
			ReachabilityAnalyser<V,E> cached = cache.get(key); // by using compute map, lazy initialisation should be triggered
			Collection<V> reachableVertices = cached.getReachableVertices(start,!outgoing,minLength==0);
			return Iterators.transform(
					reachableVertices.iterator(), 
					new Function<V,Path<V,E>>() {

						@Override
						public Path<V, E> apply(V v) {
							return new LazyPath<V,E>(g,start,v,outgoing,filter);
						}
						
					}
			);
			
		}
	}
	
	private static ReachabilityAnalyser buildCache(Key key) {
		@SuppressWarnings("rawtypes")
		ReachabilityAnalyser analyser = new ChainDecompositionReachabilityAnalyser2(Direction.BOTH);
		analyser.setGraph(key.g,key.filter);
		return analyser;
	}

}
