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

package nz.ac.massey.cs.graphaggregation;

import java.util.Collection;

import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Vertex;

public interface GraphAggregation<V1 extends Vertex<E1>, E1 extends Edge<V1>, V2 extends Vertex<E2>, E2 extends Edge<V2>> {
	void setSource(DirectedGraph<V1,E1> source);
	DirectedGraph<V1,E1> getSource();
	DirectedGraph<V2,E2> getTarget();
	boolean isReady();
	void aggregate(AggregationListener listener);
	Collection<E1> getChildren(E2 edge);
	E2 getParent(E1 edge);
	Collection<V1> getChildren(V2 vertex);
	V2 getParent(V1 vertex);
}
