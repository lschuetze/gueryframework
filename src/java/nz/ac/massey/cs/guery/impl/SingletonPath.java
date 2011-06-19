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

package nz.ac.massey.cs.guery.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.Path;
/**
 * Path that consists of only one edge.
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public class SingletonPath<V, E> implements Path<V, E> {
	
	private E edge = null;
	private List<E> edges = null;
	private GraphAdapter<V,E> graph = null;
	private Collection<V> vertices = null;
	
	public SingletonPath(E edge, GraphAdapter<V, E> graph) {
		super();
		this.edge = edge;
		this.graph = graph;
	}

	@Override
	public List<E> getEdges() {
		if (edges==null) {
			edges = new ArrayList<E>(1);
			edges.add(edge);
		}
		return edges;
	}

	@Override
	public V getEnd() {
		return graph.getEnd(edge);
	}

	@Override
	public V getStart() {
		return graph.getStart(edge);
	}

	@Override
	public Path<V, E> add(E e, V src, V target) {
		throw new UnsupportedOperationException("Cannot extend singleton path");
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Collection<V> getVertices() {
		if (vertices==null) {
			Collection<V> vertices = new HashSet<V>(2);
			vertices.add(getStart());
			vertices.add(getEnd());
		}
		return vertices;
	}

	@Override
	public boolean contains(V v) {
		return this.getStart()==v || this.getEnd()==v;
	}

}
