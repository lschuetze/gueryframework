/*
 * Copyright 2011 Jens Dietrich Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/agpl.html Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package nz.ac.massey.cs.guery.impl.ccc;

import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;

import nz.ac.massey.cs.guery.GraphAdapter;
/**
 * Interface describing a service used to build chains.
 * These chains are used to compress the reachability matrix.
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public interface ChainBuilder<V,E> {
	public void buildChains(GraphAdapter<V,E> graph,List<List<V>> chains,Map<V,List<V>> chainsByVertex,Predicate<E> edgeFilter);
}
