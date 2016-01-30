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


/**
 * Group by clause, based on an expression.
 *
 * @author jens dietrich
 */
@SuppressWarnings("unchecked")
public interface GroupByClause<V> {
    /**
     * Get the role.
     *
     * @return
     */
    public String getRole();

    /**
     * Compute the group for a given vertex.
     *
     * @param o
     * @return
     */
    public Object getGroup(V v);

    public String getExpression();
}
