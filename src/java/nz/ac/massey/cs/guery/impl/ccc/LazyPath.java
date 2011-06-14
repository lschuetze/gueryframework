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
import java.util.List;
import com.google.common.base.Predicate;
import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.impl.BreadthFirstPathFinder;

/**
 * A paths that is known to exist between vertices. Edges and vertices (other than start and end) are only computed 
 * upon request, i.e., if the respective getters are invoked. 
 * This is used when information about reachable vertices is cached (using some sort of compression). 
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public class LazyPath<V,E>  implements Path<V,E> {
	// can use one shared static instance
	private static BreadthFirstPathFinder helper = new BreadthFirstPathFinder(false);
	
	private Path<V,E> delegate = null;
	
	private V start = null;
	private V end = null;
	private GraphAdapter<V,E> graph = null;
	private Predicate<E> filter = null;
	private boolean outgoing = true;
	
	public LazyPath(GraphAdapter<V, E> graph, V start, V end, boolean outgoing,Predicate<E> filter) {
		super();
		this.graph = graph;
		this.start = start;
		this.end = end;
		this.outgoing = outgoing;
		this.filter = filter;
	}



	@Override
	public List<E> getEdges() {
		return getDelegate().getEdges();
	}
	
	private Path<V,E> getDelegate() {
		if (delegate==null) {
			for (Iterator<Path<V,E>> paths = helper.findLinks(graph, start, 1, -1, outgoing, filter, false); paths.hasNext();) {
				Path<V,E> path = paths.next();
				if (path.getEnd()==end) {
					delegate = path;
					break;
				}
			}
		}
		return delegate;
	}

	@Override
	public V getEnd() {
		return end;
	}

	@Override
	public V getStart() {
		return start;
	}

	@Override
	public Path<V, E> add(E e, V src, V target) {
		return getDelegate().add(e, src, target);
	}

	@Override
	public boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	@Override
	public int size() {
		return getDelegate().size();
	}

	@Override
	public Collection<V> getVertices() {
		return getDelegate().getVertices();
	}

	@Override
	public boolean contains(V v) {
		return getVertices().contains(v);
	}

}
