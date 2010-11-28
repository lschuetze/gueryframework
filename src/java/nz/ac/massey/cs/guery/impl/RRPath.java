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
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.Vertex;

/**
 * Right recursive path implementation.
 * @author jens dietrich
 */
final class RRPath<V extends Vertex<E>, E extends Edge<V>>  implements Path<V,E> {

	private Path<V,E> body = null;
	private E head = null;
	private int size=0;
		
	public RRPath(Path<V,E> body,E e) {
		super();
		this.body=body;
		this.head=e;
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
		if (body!=null) return body.getEnd();
		else if (head!=null) return head.getEnd();
		else throw new IllegalStateException();
	}

	public V getStart() {
		if (head!=null) return head.getStart();
		else if (body!=null) return body.getStart();
		else throw new IllegalStateException();
	}

	public RRPath<V,E> add(E e) {
		return new RRPath<V,E>(this,e);
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
		List<E> edges = this.getEdges();
		List<V> l = new ArrayList<V>(edges.size()+1);
		l.add(this.getStart());
		for (E e:edges) {
			l.add((V)e.getEnd());
		}
		return l;
	}
	
	/**
	 * Whether this path contains a given vertex.
	 */
	public boolean contains(V v) {
		if (head!=null) {
			if (head.getStart()==v) return true;
			if (body==null && head.getEnd()==v) return true; // if body !=null this node will be checked!
		} 
		if (body!=null) {
			return body.contains(v);
		}
		return false;
	}
}
