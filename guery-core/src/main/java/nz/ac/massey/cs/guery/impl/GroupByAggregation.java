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


import nz.ac.massey.cs.guery.GroupByClause;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.MotifInstanceAggregation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Motif instance aggregation based on a collection of group by clauses
 * consisting of vertex role names and optional attributes.
 *
 * @author jens dietrich
 */
public final class GroupByAggregation<V, E> implements MotifInstanceAggregation<V, E> {

    @Override
    public Object getGroupIdentifier(MotifInstance<V, E> instance) {
        final Motif<V, E> motif = instance.getMotif();
        Collection<GroupByClause<V>> clauses = motif.getGroupByClauses();

        if (clauses.size() == 1) {
            return getValue(clauses.iterator().next(), instance);
        } else {
            List<Object> identifier = new ArrayList<Object>(clauses.size());
            for (GroupByClause<V> clause : clauses) {
                identifier.add(getValue(clause, instance));
            }
            return identifier;
        }


    }

    private Object getValue(GroupByClause<V> clause, MotifInstance<V, E> instance) {
        String role = clause.getRole();
        V v = instance.getVertex(role);
        return clause.getGroup(v);
    }

}
