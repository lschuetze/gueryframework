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


import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Interface for engines processing queries.
 * @author jens dietrich
 */
public interface GQL<V extends Vertex<E>, E extends Edge<V>>{
	/**
	 * Query for instances of a motif in a graph.
	 * @param graph the graph
	 * @param motif the motif
	 * @param listener the listener
	 * @param mode the computation mode
	 */
	void query(DirectedGraph<V,E> graph,Motif<V,E> motif,ResultListener<V,E> listener,ComputationMode mode);
	/**
	 * Query for instances of a motif in a graph. Use a custom path finder. by default, a breadth first finder is used.
	 * @param graph the graph
	 * @param motif the motif
	 * @param listener the listener
	 * @param mode the computation mode
	 * @param finder a path finder
	 */
	void query(DirectedGraph<V,E> graph,Motif<V,E> motif,ResultListener<V,E> listener,ComputationMode mode,PathFinder<V, E> finder);
	/**
	 * Cancel the computation.
	 */
	void cancel() ;
	/**
	 * Reset the engine.
	 */
	void reset() ;
	
	
	
}
