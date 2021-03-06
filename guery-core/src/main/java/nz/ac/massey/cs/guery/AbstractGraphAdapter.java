/*
 * Copyright 2015 Jens Dietrich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nz.ac.massey.cs.guery;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * An abstract graph adapter that describes access to a graph that is not necessarily completely loaded
 * into memory.
 *
 * @param <V>
 * @param <E>
 * @author jens dietrich
 */
public abstract class AbstractGraphAdapter<V, E> implements GraphAdapter<V, E> {

    @Override
    public Iterator<E> getEdges(Predicate<? super E> filter) {
        return Iterators.filter(getEdges(), filter);
    }

    @Override
    public Iterator<E> getInEdges(V vertex, Predicate<? super E> filter) {
        return Iterators.filter(getInEdges(vertex), filter);
    }

    @Override
    public Iterator<E> getOutEdges(V vertex, Predicate<? super E> filter) {
        return Iterators.filter(getOutEdges(vertex), filter);
    }

    @Override
    public Iterator<V> getVertices(Predicate<? super V> filter) {
        return Iterators.filter(getVertices(), filter);
    }

    @Override
    public Iterator<V> getVertices(Comparator<? super V> comparator) {
        if (comparator == null) return getVertices();
        TreeSet<V> sorted = new TreeSet<V>(comparator);
        Iterator<V> unsorted = getVertices();
        while (unsorted.hasNext()) {
            sorted.add(unsorted.next());
        }
        closeIterator(unsorted);
        return sorted.iterator();
    }


}
