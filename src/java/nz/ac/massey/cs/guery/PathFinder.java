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

import java.util.*;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.PathFinder;
import nz.ac.massey.cs.guery.Vertex;
import com.google.common.base.Predicate;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Interface for utility to find paths in graphs.
 * @author jens dietrich
 */
public interface PathFinder<V extends Vertex<E>,E extends Edge<V>>  {
	
	Iterator<Path<V,E>> findLinks(DirectedGraph<V,E> g,V start, int minLength, int maxLength, boolean outgoing, Predicate<E> filter,boolean computeAll) ;
}
