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


package test.nz.ac.massey.cs.guery.alg.reachability.ccc;

import java.io.File;
import java.util.Collection;
import nz.ac.massey.cs.guery.impl.ccc.ChainDecompositionReachabilityAnalyser2;
import nz.ac.massey.cs.guery.impl.ccc.Direction;
import nz.ac.massey.cs.guery.impl.ccc.NullFilter;
import nz.ac.massey.cs.guery.impl.ccc.ReachabilityAnalyser;
import org.junit.Before;
import org.junit.Test;
import com.google.common.base.Function;
import static org.junit.Assert.*;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;


/**
 * Test cases for reachability using simple graphs.
 * @author jens dietrich
 */
public class TestSimpleGraphs {
	private static boolean GENERATE_DOT_FILES = true;
	private ReachabilityAnalyser<String,String> RA = null;
	
	@Before 
	public void setup() throws Exception {
		RA = new ChainDecompositionReachabilityAnalyser2<String,String>(Direction.BOTH);
	}

	@Test
	public void test1() throws Exception {
		
		DirectedGraph<String,String> g = new DirectedSparseGraph<String,String>();
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		g.addVertex("v4");
		g.addVertex("v5");
		g.addVertex("v6");
		
		g.addEdge("e12","v1","v2");
		g.addEdge("e23","v2","v3");
		g.addEdge("e24","v2","v4");
		g.addEdge("e45","v4","v5");
		g.addEdge("e46","v4","v6");
		g.addEdge("e61","v6","v1");
		
		save(g,"data/TestSimpleGraphs_1.dot");
		
		
		RA.setGraph(new nz.ac.massey.cs.guery.adapters.jung.JungAdapter(g),NullFilter.DEFAULT);
		
//		UnweightedShortestPath SPU = new UnweightedShortestPath<String,String>(g);
//		Map<String,Number> dm = SPU.getDistanceMap("v1");
//		for (Map.Entry<String,Number> e:dm.entrySet()) {
//			System.out.print(e.getKey());
//			System.out.print(" -> ");
//			System.out.println(e.getValue());
//			
//		}
		
		
		checkMap(false,"v1",RA.getReachableVertices("v1",false),"v2","v3","v4","v5","v6");
		checkMap(false,"v2",RA.getReachableVertices("v2",false),"v3","v4","v5","v6","v1");
		checkMap(false,"v3",RA.getReachableVertices("v3",false));
		checkMap(false,"v4",RA.getReachableVertices("v4",false),"v1","v2","v3","v5","v6");
		checkMap(false,"v5",RA.getReachableVertices("v5",false));
		checkMap(false,"v6",RA.getReachableVertices("v6",false),"v1","v2","v3","v4","v5");
		
		checkMap(true,"v1",RA.getReachableVertices("v1",true),"v2","v4","v6");
		checkMap(true,"v2",RA.getReachableVertices("v2",true),"v1","v4","v6");
		checkMap(true,"v3",RA.getReachableVertices("v3",true),"v1","v2","v4","v6");
	}
	
	@Test
	public void test2() throws Exception {
		
		DirectedGraph<String,String> g = new DirectedSparseGraph<String,String>();
		g.addVertex("v1");
		g.addVertex("v2");
		
		g.addEdge("e12","v1","v2");
		g.addEdge("e21","v2","v1");
		
		RA.setGraph(new nz.ac.massey.cs.guery.adapters.jung.JungAdapter(g),NullFilter.DEFAULT);
		
		save(g,"data/TestSimpleGraphs_2.dot");
		
		checkMap(false,"v1",RA.getReachableVertices("v1",false),"v2");
		checkMap(false,"v2",RA.getReachableVertices("v2",false),"v1");
		
	}
	
	@Test
	public void test3() throws Exception {
		
		DirectedGraph<String,String> g = new DirectedSparseGraph<String,String>();
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		
		g.addEdge("e12","v1","v2");
		g.addEdge("e23","v2","v3");
		g.addEdge("e31","v3","v1");

		RA.setGraph(new nz.ac.massey.cs.guery.adapters.jung.JungAdapter(g),NullFilter.DEFAULT);
		
		save(g,"data/TestSimpleGraphs_3.dot");
		
		checkMap(false,"v1",RA.getReachableVertices("v1",false),"v2","v3");
		checkMap(false,"v2",RA.getReachableVertices("v2",false),"v1","v3");
		checkMap(false,"v3",RA.getReachableVertices("v3",false),"v1","v2");
	}
	
	
	

	private void checkMap(boolean reverse, String v,Collection<String> found,String... expected ) throws Exception {
		System.out.println("Reachable from " + v + " : " + found + (reverse?" - (reverse!)":""));

		
		assertEquals(expected.length,found.size());
		for (String s:expected) {
			assertTrue("Expected element " + s + " not found in computed set of reachable nodes",found.contains(s));
		}
	}
	
	private void save (DirectedGraph<String,String> g,String name) throws Exception {
		if (GENERATE_DOT_FILES ) {
			File file = new File(name);
			Function<String,String> vertexLabels = new Function<String,String>() {@Override public String apply(String s) {return s;}};
			Function<String,String> edgeLabels = new Function<String,String>() {@Override public String apply(String s) {return s;}};
			// new Jung2DOT().convert(g, file, vertexLabels, edgeLabels);
		}
	}
}
