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

package nz.ac.massey.cs.guery.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import nz.ac.massey.cs.guery.Path;

/**
 * Represents the non-existence of a path connecting two edges.
 * This is used in negated path constraints.
 * @author jens dietrich
 */
public class NoPath<V,E> implements Path<V, E> {
	private final static List EMPTY_LIST = Collections.unmodifiableList(new ArrayList());
	private V end = null;
	private V start = null;
	
	public NoPath(V end, V start) {
		super();
		this.end = end;
		this.start = start;
	}

	@Override
	public Path<V, E> add(E e,V src,V target) {
		return null;
	}

	@Override
	public boolean contains(V v) {
		return false;
	}

	@Override
	public List<E> getEdges() {
		return EMPTY_LIST;
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
	public Collection<V> getVertices() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public int size() {
		return 0;
	}

}
