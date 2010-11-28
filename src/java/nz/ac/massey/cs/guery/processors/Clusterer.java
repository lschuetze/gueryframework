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

package nz.ac.massey.cs.guery.processors;

import java.util.Set;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.Processor;
import nz.ac.massey.cs.guery.Vertex;
import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Implementation class for processing clusters in graph.
 * @author jens dietrich
 */
public abstract class Clusterer<V extends Vertex<E>,E extends Edge<V>> implements Processor<V,E> {
	
	public Clusterer() {
		super();
	}
	
	@Override
	public void process(DirectedGraph<V, E> g){
		EdgeBetweennessClusterer<V,E> clusterer = new EdgeBetweennessClusterer<V,E>(0);
		Set<Set<V>> clusters = clusterer.transform(g);  
		int counter = 1;
		for (Set<V> cluster:clusters) {
			String label = this.createClusterLabel(counter);
			counter=counter+1;
			for (V v:cluster) {
				annotateWithClusterLabel(v,label);
			}
		}
	}

	protected abstract void annotateWithClusterLabel(V vertex,String clusterLabel);

	protected String createClusterLabel(int counter) {
		return "cluster-"+counter;
	}

}
