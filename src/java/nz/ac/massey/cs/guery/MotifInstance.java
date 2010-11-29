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


package nz.ac.massey.cs.guery;

import java.util.Set;


public interface MotifInstance<V,E> {
	/**
	 * Get the instantiated motif.
	 * @return
	 */
	Motif<V,E> getMotif();
	/**
	 * Get a vertex for a given role name (id attribute in query).
	 * The role name is the id of the node in the query.
	 * @param roleName
	 * @return
	 */
	V getVertex(String roleName);
	/**
	 * Get the path for a given path role name.
	 * @param roleName
	 * @return
	 */
	Path<V,E> getPath(String roleName); 
	/**
	 * Get all vertices (instantiating roles and part of paths)
	 * @return a set of vertices
	 */
	Set<V> getVertices();
}
