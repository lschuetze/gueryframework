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

import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.Vertex;
/**
 * Left recursive empty path.
 * @author jens dietrich
 */
final class LREmptyPath<V extends Vertex<E>, E extends Edge<V>>  extends EmptyPath<V,E> {

	public LREmptyPath(V v) {
		super(v);
	}

	@Override
	public Path<V,E> add(E e) {
		return new LRPath<V,E>(this,e);
	}
}
