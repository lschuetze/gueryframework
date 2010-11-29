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

package nz.ac.massey.cs.guery.impl;

import java.util.ArrayList;
import java.util.List;
import nz.ac.massey.cs.guery.Path;

/**
 * Left recursive path implementation.
 * @author jens dietrich
 */
public class LRPath<V,E>  implements Path<V,E>{

	private Path<V,E> body = null;
	private E tail = null;
	private V end = null;
	private int size=0;
		
	public LRPath(Path<V,E> body,E e,V end) {
		super();
		this.body=body;
		this.tail=e;
		this.end = end;
		size = 1+body.size();
	}

	public List<E> getEdges() {		
		List<E> bedges = this.body==null?null:this.body.getEdges();
		List<E> edges = new ArrayList<E>(bedges==null?1:bedges.size()+1);
		if (this.body!=null) edges.addAll(bedges);
		if (this.tail!=null) edges.add(this.tail);
		return edges;
	}

	public V getEnd() {
		return end;
	}

	public V getStart() {
		return body.getStart();
	}

	public LRPath<V,E> add(E e,V start,V end) {	
		return new LRPath<V,E>(this,e,end);
	}

	
	public boolean isEmpty() {
		return tail==null&&(body==null || body.isEmpty());
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
		l.add(end);
		l.addAll(this.body.getVertices());
		return l;
	}
	
	/**
	 * Whether this path contains a given vertex.
	 */
	public boolean contains(V v) {
		if (end==v) return true;
		return body.contains(v);
	}
}
