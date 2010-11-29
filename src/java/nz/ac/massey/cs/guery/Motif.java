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

import java.util.Collection;
import java.util.List;


/**
 * This is the type for queries. Queries are usually built from query language expressions (query DSL, XML, ..).
 * @author jens dietrich
 */
public interface Motif<V,E> {
	/**
	 * Roles are the vertex variables in the query.
	 * @return
	 * @throws JAXBException 
	 */
	List<String> getRoles();
	List<String> getPathRoles();
	List<String> getNegatedPathRoles();
	List<Constraint> getConstraints();
	Collection<GroupByClause<V>> getGroupByClauses(); 	
	Collection<Processor<V,E>> getGraphProcessors();
	String getName();
}
