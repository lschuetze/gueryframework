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

package nz.ac.massey.cs.guery.impl;


import java.util.ArrayList;
import java.util.List;

/**
 * Empty path.
 *
 * @author jens dietrich
 */
public abstract class EmptyPath<V, E> extends AbstractPath<V, E> {
    private static List EMPTY_LIST_OF_EDGES = new ArrayList();
    private static List EMPTY_LIST_OF_VERTICES = new ArrayList();
    private V soleNode = null;

    public EmptyPath(V v) {
        super();
        this.soleNode = v;
    }

    public final List<E> getEdges() {
        return EMPTY_LIST_OF_EDGES;
    }

    public final V getEnd() {
        return soleNode;
    }

    public final V getStart() {
        return soleNode;
    }

    public final boolean isEmpty() {
        return true;
    }

    public final int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "Path[length=0,node=" + this.soleNode + "]";
    }

    /**
     * Get an ordered list of vertices.
     *
     * @return
     */
    public final List<V> getVertices() {
        return EMPTY_LIST_OF_VERTICES;
    }

    /**
     * Whether this path contains a given vertex.
     */
    public boolean contains(V v) {
        return soleNode == v;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((soleNode == null) ? 0 : soleNode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmptyPath other = (EmptyPath) obj;
        if (soleNode == null) {
            if (other.soleNode != null)
                return false;
        } else if (!soleNode.equals(other.soleNode))
            return false;
        return true;
    }
}
