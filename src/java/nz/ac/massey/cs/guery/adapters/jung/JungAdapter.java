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

package nz.ac.massey.cs.guery.adapters.jung;

import java.util.Iterator;

import nz.ac.massey.cs.guery.AbstractGraphAdapter;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Adapter for graphs represented using the JUNG API.
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public class JungAdapter<V,E> extends AbstractGraphAdapter<V,E> {
	DirectedGraph<V,E> g = null;

	public JungAdapter(DirectedGraph<V, E> g) {
		super();
		this.g = g;
	}

	@Override
	public Iterator<E> getInEdges(V vertex) {
		return g.getInEdges(vertex).iterator();
	}

	@Override
	public Iterator<E> getOutEdges(V vertex) {
		return g.getOutEdges(vertex).iterator();
	}

	@Override
	public V getStart(E edge) {
		return g.getSource(edge);
	}

	@Override
	public V getEnd(E edge) {
		return g.getDest(edge);
	}

	@Override
	public Iterator<E> getEdges() {
		return g.getEdges().iterator();
	}

	@Override
	public Iterator<V> getVertices() {
		return g.getVertices().iterator();
	}

	@Override
	public int getVertexCount() throws UnsupportedOperationException {
		return g.getVertexCount();
	}

	@Override
	public int getEdgeCount() throws UnsupportedOperationException {
		return g.getEdgeCount();
	}

	@Override
	public void closeIterator(Iterator iterator) {
		// nothing to do here - iterators operate on resources in memory only
	}
	
	public DirectedGraph<V, E> getGraph() {
		return g;
	}
}
