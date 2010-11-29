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

package nz.ac.massey.cs.guery;

import java.util.Comparator;
import java.util.Iterator;

import com.google.common.base.Predicate;

/**
 * Interface for graph adapters describing access to graphs that may not be in memory.
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public interface GraphAdapter<V,E> {
	Iterator<E> getInEdges(V vertex);
	Iterator<E> getInEdges(V vertex,Predicate<? super E> filter);
	Iterator<E> getOutEdges(V vertex);
	Iterator<E> getOutEdges(V vertex,Predicate<? super E> filter);
	V getStart(E edge);
	V getEnd(E edge);
	Iterator<E> getEdges();
	Iterator<E> getEdges(Predicate<? super E> filter);
	Iterator<V> getVertices();
	Iterator<V> getVertices(Comparator<? super V> comparator);
	Iterator<V> getVertices(Predicate<? super V> filter);
	int getVertexCount() throws UnsupportedOperationException;
	int getEdgeCount()  throws UnsupportedOperationException;
	void closeIterator(Iterator<?> iterator);
}
