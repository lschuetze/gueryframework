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

import java.util.Collection;
import com.google.common.base.Predicate;
import nz.ac.massey.cs.guery.GraphAdapter;

/**
 * Interface used to describe a service that can compute / fetch information about vertex reachability. 
 * In this package, this information is cached. The space needed is |V|^2 - for this reason, compression 
 * techniques will be used in implementing classes. 
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public interface ReachabilityAnalyser<V, E> {
	
	public GraphAdapter<V, E> getGraph();

	public void setGraph(GraphAdapter<V, E> graph,Predicate<E> edgeFilter) ;
	
	public Collection<V> getReachableVertices(V start, boolean reverse, boolean includeStart) ;

	boolean isReachable(V v1, V v2, boolean reverse);
}


