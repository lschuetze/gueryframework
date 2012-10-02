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


package nz.ac.massey.cs.guery.adapters.blueprints;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * The purpose of the cache is not performance but to preserve referential integrity. 
 * I.e., when obtaining a reference to a vertex (or edge) through different means (index, navigation),
 * we always want one object to represent the vertex or node. A direct consequence of this is that 
 * algorithms can reply on == (instead of the slower equality). 
 * Caching should not interfere with GC, it is therefore recommended to use weak or soft maps.
 * @see <a href="https://groups.google.com/forum/?fromgroups=#!topic/neo4j/PIACdug4yJo">referential integrity issue in neo4j</a>
 * @author jens dietrich
 */
public interface ElementCache {
	/**
	 * contract:
	 * IF v1.equals(v2) is true THEN getCachedVertex(v1)==getCachedVertex(v2) is true 
	 */
	Vertex getCachedVertex (Vertex vertex) ;
	
	/**
	 * contract:
	 * IF e1.equals(e2) is true THEN getCachedEdge(e1)==getCachedEdge(e2) is true 
	 */
	Edge getCachedEdge (Edge edge) ;

	/**
	 * Whether this implementation can ensure the contracts for the getCached* methods.
	 * @return
	 */
	boolean ensuresReferentialIntegrity ();
	
	
	
}
