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

package nz.ac.massey.cs.guery.adapters.jungalt;

import java.io.Serializable;

/**
 * Custom edge class.
 * @author jens dietrich
 */

@SuppressWarnings({"unchecked" })
public class Edge<V extends Vertex> extends GraphElement implements Serializable {

	private static final long serialVersionUID = 8084858521563114358L;
	private V start = null;
	private V end = null;
	
	public Edge(String id, V start, V end) {
		super(id);
		this.setEnd(end);
		this.setStart(start);
	}
	public Edge() {
		super();
	}
	
	public V getStart() {
		return start;
	}
	public V getEnd() {
		return end;
	}
	public void setStart(V start) {
		if (this.start!=null) {
			boolean success = start.removeOutEdge(this);
			assert success;
		}
		this.start = start;
		start.addOutEdge(this);
	}
	public void setEnd(V end) {
		if (this.end!=null) {
			boolean success = start.removeInEdge(this);
			assert success;
		}
		this.end = end;
		end.addInEdge(this);
	}
	
	public String toString() {
		return new StringBuffer() 			
			.append(this.getId())
			.append(':')
			.append('[')
			.append(this.start)
			.append(" -> ")
			.append(this.end)
			.append(']')
			.toString();
	}
	public void copyValuesTo(Edge<V> e) {
		// nothing todo here
	}
}
