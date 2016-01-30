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
import nz.ac.massey.cs.guery.Path;

/**
 * Right recursive path implementation.
 * @author jens dietrich
 */
public class RRPath<V,E> extends AbstractPath<V,E> {

	private Path<V,E> body = null;
	private E head = null;
	private V start = null;
	private int size=0;
		
	public RRPath(Path<V,E> body,E e,V start) {
		super();
		this.body=body;
		this.head=e;
		this.start=start;
		size = 1+body.size();
	}

	public List<E> getEdges() {		
		List<E> bedges = this.body==null?null:this.body.getEdges();
		List<E> edges = new ArrayList<E>(bedges==null?1:bedges.size()+1);
		if (this.head!=null) edges.add(this.head);
		if (this.body!=null) edges.addAll(bedges);
		return edges;
	}

	public V getEnd() {
		return body.getEnd();
	}

	public V getStart() {
		return start;
	}

	public RRPath<V,E> add(E e,V start,V end) {
		return new RRPath<V,E>(this,e,start);
	}
	
	public boolean isEmpty() {
		return head==null&&(body==null || body.isEmpty());
	}
	
	public int size() {
		return size;
			
	}

	@Override
	public String toString() {
		return new StringBuffer()
			.append("Path[length=")
			.append(size())
			.append(",")
			.append(getStart())
			.append(" -> ")
			.append(getEnd())
			.append("]")
			.toString();
	}
	/**
	 * Get an ordered list of vertices.
	 * @return
	 */
	public List<V> getVertices() {
		List<V> l = new ArrayList<V>(this.size+1);
		l.add(start);
		l.addAll(this.body.getVertices());
		return l;
	}
	
	/**
	 * Whether this path contains a given vertex.
	 */
	public boolean contains(V v) {
		if (start==v) return true;
		return body.contains(v);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + size;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		RRPath other = (RRPath) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (size != other.size)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
}
