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

package nz.ac.massey.cs.guery.suite1;

import static nz.ac.massey.cs.guery.suite1.TestUtils.loadGraph;
import static nz.ac.massey.cs.guery.suite1.TestUtils.loadQuery;
import nz.ac.massey.cs.guery.ComputationMode;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.ResultListener;
import nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter;
import nz.ac.massey.cs.guery.impl.MultiThreadedGQLImpl;
import nz.ac.massey.cs.guery.io.xml.XMLMotifReader;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Example showing how to run a simple query.
 * @author jens dietrich
 *
 */
public class Example {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph1.graphml");
		Motif<ColouredVertex,ColouredEdge> motif = loadQuery(new XMLMotifReader(),"query1.xml");
		ResultListener<ColouredVertex,ColouredEdge> listener = new ResultListener<ColouredVertex,ColouredEdge>() {
			@Override
			public void done() {}
			@Override
			public boolean found(MotifInstance<ColouredVertex,ColouredEdge> instance) {
				System.out.println("instance found:");
				System.out.println(" start: " + instance.getVertex("start"));
				System.out.println(" end:" + instance.getVertex("end"));
				System.out.println(" connection: " + instance.getPath("connection"));
				return true; // try to find more!
			}
			@Override
			public void progressMade(int progress, int total) {}
		};
		GQL<ColouredVertex,ColouredEdge> engine = new MultiThreadedGQLImpl<ColouredVertex,ColouredEdge>();
		engine.query(new JungAdapter<ColouredVertex,ColouredEdge>(graph), motif, listener, ComputationMode.ALL_INSTANCES);
	}

}
