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
package nz.ac.massey.cs.guery.impl;

import java.util.List;
import edu.uci.ics.jung.graph.Graph;
import nz.ac.massey.cs.guery.Constraint;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.Vertex;

/**
 * Scheduler for constraints. Scheduling constraints means listing them in an
 * optimizes fashion. 
 * @author jens dietrich
 * 
 */

public interface ConstraintScheduler<V extends Vertex<E>,E extends Edge<V>> {
    /**
     * Get the constraints for the motif. 
     * @param g the graph
     * @param pattern a motif
     * @return a list of constraints
     */
    public List<Constraint> getConstraints(Graph<V,E> g,Motif<V,E> motif);
    
    /**
     * Get the name of the initial role where analysis starts.
     * @param graph
     * @param motif
     * @return
     */
    public String getInitialRole(Graph<V,E> graph,Motif<V,E> motif);

}