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

package test.nz.ac.massey.cs.guery.benchmark;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.guery.ComputationMode;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifReader;
import nz.ac.massey.cs.guery.impl.MultiThreadedGQLImpl;
import nz.ac.massey.cs.guery.impl.ccc.CCCPathFinder;
import nz.ac.massey.cs.guery.io.dsl.DefaultMotifReader;
import nz.ac.massey.cs.guery.util.ResultCollector;

/**
 * Runs a benchmark test for a multithreaded engine using the breadth first pathfinder without caching.
 * Analyses azureus for instances of cd.guery (defined in this package).
 * Results will be printed to the console.
 * @author jens dietrich
 */
public class RunBenchmarkTest4CCCPathFinder {
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String data = "Azureus3.0.3.4.jar.graphml";
		DirectedGraph<TypeNode,TypeRef> graph = loadGraph(data);
        Motif<TypeNode,TypeRef> motif =  loadMotif("cd.guery");
        
        System.out.println("query "+ motif + " on data " + data);
        
        System.out.println("Edges " + graph.getEdgeCount());
        System.out.println("Nodes " + graph.getVertexCount());
         
        ResultCollector<TypeNode,TypeRef> coll = new ResultCollector<TypeNode,TypeRef>();
		long t1 = System.currentTimeMillis();
		GQL<TypeNode,TypeRef> engine = new MultiThreadedGQLImpl<TypeNode,TypeRef>();
		engine.query(new nz.ac.massey.cs.guery.adapters.jung.JungAdapter<TypeNode,TypeRef>(graph),motif,coll,ComputationMode.ALL_INSTANCES, new CCCPathFinder<TypeNode,TypeRef>());
		long t2 = System.currentTimeMillis();
		System.out.println("query "+motif+" on data "+data+ " returned "+coll.getInstances().size()+" variants");
		System.out.println("query "+motif+" on data "+data+ " took "+(t2-t1)+" millis");


	}

	private static DirectedGraph<TypeNode,TypeRef> loadGraph(String name) throws Exception {
        Reader reader = new InputStreamReader(load(name));
        GraphMLReader greader = new GraphMLReader(reader);
        DirectedGraph<TypeNode,TypeRef> g = greader.readGraph();
        greader.close();
        return g;
	}
	
	private static Motif<TypeNode,TypeRef> loadMotif(String name) throws Exception {
		MotifReader<TypeNode,TypeRef> reader = new DefaultMotifReader<TypeNode,TypeRef>();
        return reader.read(load(name));
	}
	
	private static InputStream load(String resource) throws Exception {
		return RunBenchmarkTest4CCCPathFinder.class.getResourceAsStream("/test/nz/ac/massey/cs/guery/benchmark/"+resource);
	}
}
