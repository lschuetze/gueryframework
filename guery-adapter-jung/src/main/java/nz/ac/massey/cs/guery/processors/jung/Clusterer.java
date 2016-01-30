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

package nz.ac.massey.cs.guery.processors.jung;

import java.util.Set;
import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.Processor;
import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Implementation class for processing clusters in graph.
 * Note that this clustered will only work for graph adapters based on JUNG graphs.
 * @author jens dietrich
 */
public abstract class Clusterer<V,E> implements Processor<V,E> {
	
	public Clusterer() {
		super();
	}
	
	@Override
	public void process(GraphAdapter<V, E> g){
		DirectedGraph<V,E> graph = null;
		if (g instanceof nz.ac.massey.cs.guery.adapters.jung.JungAdapter) {
			graph = ((nz.ac.massey.cs.guery.adapters.jung.JungAdapter)g).getGraph();
		}
		else if (g instanceof nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter) {
			graph = ((nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter)g).getGraph();
		}
		
		if (graph==null) throw new IllegalArgumentException("The clusterer " + this.getClass().getName() + " can only be used with Jung graph adapters");
		
		EdgeBetweennessClusterer<V,E> clusterer = new EdgeBetweennessClusterer<V,E>(0);
		Set<Set<V>> clusters = clusterer.transform(graph);  
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
