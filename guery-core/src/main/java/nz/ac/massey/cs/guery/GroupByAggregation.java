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


package nz.ac.massey.cs.guery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Motif instance aggregation based on a collection of group by clauses 
 * consisting of vertex role names and optional attributes.
 * @author jens dietrich
 */
public class GroupByAggregation<V,E> implements MotifInstanceAggregation<V,E> {

	@Override
	public Object getGroupIdentifier (MotifInstance<V,E> instance) {
		final Motif<V,E> motif = instance.getMotif();
		Collection<GroupByClause<V>> clauses = motif.getGroupByClauses();
		List<Object> identifier = new ArrayList<Object>();
		for (GroupByClause<V> c:clauses) {
			String role = c.getRole();
			V v = instance.getVertex(role);
			Object value = c.getGroup(v);
			if (clauses.size()==1) return value;
			else identifier.add(value);
		}

		return identifier;
	}

}
