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

import java.util.Collection;
import java.util.List;

/**
 * A path is a sequence of edges connecting vertices.
 *
 * @author jens dietrich
 */
public interface Path<V, E> {


    public List<E> getEdges();

    public V getEnd();

    public V getStart();

    public Path<V, E> add(E e, V src, V target);

    public boolean isEmpty();

    public int size();

    // note that vertices are not in order - use getEdges() if you need them ordered!
    public Collection<V> getVertices();

    public boolean contains(V v);

}
