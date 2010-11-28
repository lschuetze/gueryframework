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

package test.nz.ac.massey.cs.graphaggregation;

import java.io.InputStreamReader;
import java.io.Reader;
import nz.ac.massey.cs.graphaggregation.AggregationListener;
import nz.ac.massey.cs.guery.ComputationMode;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.ResultListener;
import nz.ac.massey.cs.guery.impl.MultiThreadedGQLImpl;
import nz.ac.massey.cs.guery.io.xml.XMLMotifReader;
import edu.uci.ics.jung.graph.DirectedGraph;

public class Example {

	static DirectedGraph<TypeNode,TypeReference> loadGraph(String name) throws Exception {
        String src = "/test/nz/ac/massey/cs/graphaggregation/data/"+name;
        Reader reader = new InputStreamReader(AggregationTests.class.getResourceAsStream(src));
        GraphMLReader greader = new GraphMLReader(reader);
        DirectedGraph<TypeNode,TypeReference> g = greader.readGraph();
        greader.close();
        return g;
	}
	
	static Motif<PackageNode,PackageReference> loadQuery(String name) throws Exception {
		String src = "/test/nz/ac/massey/cs/graphaggregation/data/"+name;
        return new XMLMotifReader().read(Example.class.getResourceAsStream(src));
	}
	
	public static void main(String[] args) throws Exception {
		// load data and query
		DirectedGraph<TypeNode,TypeReference> graph = loadGraph("ant.jar.graphml");
		Motif<PackageNode,PackageReference> motif = loadQuery("cd.xml");
		
		// aggregate graph
		Type2PackageAggregation aggregation = new Type2PackageAggregation();
		aggregation.setSource(graph);
		aggregation.aggregate(new AggregationListener(){
			@Override
			public void activityStarted(String message) {}
			@Override
			public void done() {}
			@Override
			public void progressMade(int progress, int total) {}});
		DirectedGraph<PackageNode,PackageReference> aggGraph = aggregation.getTarget();
		
		// query
		ResultListener<PackageNode,PackageReference> listener = new ResultListener<PackageNode,PackageReference>() {
			private int count=0;
			@Override
			public void done() {}
			@Override
			public boolean found(MotifInstance<PackageNode,PackageReference> instance) {
				System.out.println("instance " + ++count + " found:");
				System.out.println(" start: " + instance.getVertex("package1"));
				System.out.println(" end:" + instance.getVertex("package2"));
				System.out.println(" incoming: ");
				printPath(instance.getPath("incoming"));
				System.out.println(" outgoing: " );
				printPath(instance.getPath("outgoing"));
				System.out.println();
				return true; // try to find more!
			}
			@Override
			public void progressMade(int progress, int total) {}
		};
		
		GQL<PackageNode,PackageReference> engine = new MultiThreadedGQLImpl<PackageNode,PackageReference>();
		engine.query(aggGraph,motif,listener,ComputationMode.CLASSES_REDUCED);

	}

	protected static void printPath(Path<PackageNode, PackageReference> path) {
		boolean first = true;
		for (PackageReference edge:path.getEdges()) {
			if (first) {
				System.out.print("   ");
				first = false;
			}
			else {
				System.out.print("   > ");
			}
			System.out.print(edge.getStart().getName());
			System.out.print(" -> ");
			System.out.print(edge.getEnd().getName());
			
			System.out.print(" [strength=");
			System.out.print(edge.getStrength());
			System.out.println("]");
		} 
	}
}
