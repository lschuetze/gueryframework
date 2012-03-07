/*
 * Copyright 2012 Jens Dietrich Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/agpl.html Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package nz.ac.massey.cs.guery.impl;

import java.util.List;
import nz.ac.massey.cs.guery.Path;

/**
 * Utility to implement equals and hashCode for all paths.
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public abstract class AbstractPath<V,E> implements Path<V,E> {
	
	@Override
	public boolean equals (Object obj) {
		if (obj==null) return false;
		if (obj instanceof Path) {
			Path<V,E> p = (Path<V,E>)obj;
			if (p.size()==this.size()) {
				// try first to compare first and last nodes as getEdges() is expensive
				if (p.size()>0) {
					if (!this.getStart().equals(p.getStart())) return false;
					if (!this.getEnd().equals(p.getEnd())) return false;
				} 
				// compare all edges in path
				List<E> edges1 = this.getEdges();
				List<E> edges2 = p.getEdges();
				for (int i=0;i<edges1.size();i++) {
					if (!edges1.get(i).equals(edges2.get(i))) return false;
				}
				return true;
			}
			
		}
		
		return false;
	}
	@Override
	public int hashCode() {
		if (this.size()==0) return this.getStart().hashCode();
		else {
			final int prime = 31;
			int result = 1;
			result = prime * result + this.getStart().hashCode();
			result = prime * result + this.getEnd().hashCode();
			return result;
		}
	}
	

}
