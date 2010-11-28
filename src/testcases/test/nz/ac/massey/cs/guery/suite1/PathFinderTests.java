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

package test.nz.ac.massey.cs.guery.suite1;

import static org.junit.Assert.*;
import static test.nz.ac.massey.cs.guery.suite1.TestUtils.loadGraph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.PathFinder;
import nz.ac.massey.cs.guery.impl.BreadthFirstPathFinder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.google.common.base.Predicate;
import edu.uci.ics.jung.graph.DirectedGraph;

@RunWith(Parameterized.class)
public class PathFinderTests {

	private PathFinder finder = null;
	
	public PathFinderTests(PathFinder finder) {
		super();
		this.finder = finder;
	}

	@Parameters
	 public static Collection pathFilders() {
	  return Arrays.asList(
			  new PathFinder[][] {
					  {new BreadthFirstPathFinder<ColouredVertex,ColouredEdge>(true)},
					  {new BreadthFirstPathFinder<ColouredVertex,ColouredEdge>(false)}
			  });
	}
	
	@Test 
	public void test1() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph1.graphml");
		ColouredVertex start = getVertex(graph,"v1");
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge> () {
			@Override
			public boolean apply(ColouredEdge e) {return true;}	
		};
		Iterator<Path<ColouredVertex,ColouredEdge>> iter = finder.findLinks(graph, start, 1, 100, true, filter,true);
		List<String> expected = collectAndPrintOutgoing ("test1()",iter,start);
		assertTrue(expected.contains("v1,v2"));
		assertTrue(expected.contains("v1,v2,v6"));
		assertTrue(expected.contains("v1,v2,v3"));
		assertTrue(expected.contains("v1,v2,v3,v7"));
		assertTrue(expected.contains("v1,v2,v3,v4"));
		assertTrue(expected.contains("v1,v2,v3,v4,v5"));
		
		assertEquals(6,expected.size());
	}
	
	@Test 
	public void test2() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph1.graphml");
		ColouredVertex start = getVertex(graph,"v1");
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge> () {
			@Override
			public boolean apply(ColouredEdge e) {return !"black".equals(e.getColour());}	
		};
		Iterator<Path<ColouredVertex,ColouredEdge>> iter = finder.findLinks(graph, start, 1, 100, true, filter,true);
		List<String> expected = collectAndPrintOutgoing ("test2()",iter,start);
		assertTrue(expected.contains("v1,v2"));
		assertTrue(expected.contains("v1,v2,v6"));
		assertTrue(expected.contains("v1,v2,v3"));
		assertTrue(expected.contains("v1,v2,v3,v4"));
		assertTrue(expected.contains("v1,v2,v3,v4,v5"));
		
		assertEquals(5,expected.size());
	}

	
	@Test 
	public void test3() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph1.graphml");
		ColouredVertex start = getVertex(graph,"v1");
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge> () {
			@Override
			public boolean apply(ColouredEdge e) {return true;}	
		};
		Iterator<Path<ColouredVertex,ColouredEdge>> iter = finder.findLinks(graph, start, 1, 3, true, filter,true);
		List<String> expected = collectAndPrintOutgoing ("test3()",iter,start);
		assertTrue(expected.contains("v1,v2"));
		assertTrue(expected.contains("v1,v2,v6"));
		assertTrue(expected.contains("v1,v2,v3"));
		assertTrue(expected.contains("v1,v2,v3,v7"));
		assertTrue(expected.contains("v1,v2,v3,v4"));
		
		assertEquals(5,expected.size());
	}

	
	@Test 
	public void test4() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph1.graphml");
		ColouredVertex start = getVertex(graph,"v1");
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge> () {
			@Override
			public boolean apply(ColouredEdge e) {return true;}	
		};
		Iterator<Path<ColouredVertex,ColouredEdge>> iter = finder.findLinks(graph, start, 0, 3, true, filter,true);
		List<String> expected = collectAndPrintOutgoing ("test4()",iter,start);
		assertTrue(expected.contains("v1"));
		assertTrue(expected.contains("v1,v2"));
		assertTrue(expected.contains("v1,v2,v6"));
		assertTrue(expected.contains("v1,v2,v3"));
		assertTrue(expected.contains("v1,v2,v3,v7"));
		assertTrue(expected.contains("v1,v2,v3,v4"));
		
		assertEquals(6,expected.size());
	}

	
	@Test 
	public void test5() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph1.graphml");
		ColouredVertex start = getVertex(graph,"v1");
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge> () {
			@Override
			public boolean apply(ColouredEdge e) {return true;}	
		};
		Iterator<Path<ColouredVertex,ColouredEdge>> iter = finder.findLinks(graph, start, 1, 100, false, filter,true);
		List<String> expected = collectAndPrintIncoming ("test5()",iter,start);
		assertTrue(expected.contains("v6,v1"));
		assertTrue(expected.contains("v2,v6,v1"));
		assertTrue(expected.contains("v5,v1"));
		assertTrue(expected.contains("v4,v5,v1"));
		assertTrue(expected.contains("v3,v4,v5,v1"));
		assertTrue(expected.contains("v2,v3,v4,v5,v1"));
		
		assertEquals(6,expected.size());
	}
	
	@Test 
	public void test6() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph2.graphml");
		ColouredVertex start = getVertex(graph,"r1");
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge> () {
			@Override
			public boolean apply(ColouredEdge e) {return true;}	
		};
		Iterator<Path<ColouredVertex,ColouredEdge>> iter = finder.findLinks(graph, start, 1, 100, true, filter,true);
		List<String> expected = collectAndPrintOutgoing ("test6()",iter,start);
		assertTrue(expected.contains("r1,b1"));
		assertTrue(expected.contains("r1,b1,b2"));
		assertTrue(expected.contains("r1,b1,b2,r2"));
		assertTrue(expected.contains("r1,b3"));
		assertTrue(expected.contains("r1,b3,b4"));
		assertTrue(expected.contains("r1,b3,b5"));
		assertTrue(expected.contains("r1,b3,b4,r2"));
		assertTrue(expected.contains("r1,b3,b5,r2"));
		
		assertEquals(8,expected.size());
	}
	
	@Test 
	public void test7() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph3.graphml");
		ColouredVertex start = getVertex(graph,"r1");
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge> () {
			@Override
			public boolean apply(ColouredEdge e) {return true;}	
		};
		Iterator<Path<ColouredVertex,ColouredEdge>> iter = finder.findLinks(graph, start, 1, 100, true, filter,false);
		List<String> expected = collectAndPrintOutgoing ("test7()",iter,start);
		assertTrue(expected.contains("r1,b1"));
		assertTrue(expected.contains("r1,b1,b2"));
		assertTrue(expected.contains("r1,b3"));
		// two possible path to r2 - only one must be found!
		assertTrue(expected.contains("r1,b3,b4") || expected.contains("r1,b3,b5"));
		assertTrue(expected.contains("r1,b3,b4,r2") || expected.contains("r1,b3,b5,r2"));
		
		assertEquals(6,expected.size());
	}

	private List<String> collectAndPrintOutgoing (String methodName,Iterator<Path<ColouredVertex,ColouredEdge>> iter,ColouredVertex start) {
		List<String> list = new ArrayList<String>();
		System.out.println(methodName);
		while (iter.hasNext()) {
			StringBuffer nodes = new StringBuffer();
			System.out.print(start);
			nodes.append(start.getId());
			Path<ColouredVertex,ColouredEdge> p = iter.next();
			for (ColouredEdge e:p.getEdges()) {
				System.out.print(" > ");
				ColouredVertex v = e.getEnd();
				System.out.print(v);
				nodes.append(",");
				nodes.append(v.getId());
			}
			System.out.println();
			list.add(nodes.toString());
		}	
		System.out.println();
		return list;
	}
	private List<String> collectAndPrintIncoming (String methodName,Iterator<Path<ColouredVertex,ColouredEdge>> iter,ColouredVertex start) {
		List<String> list = new ArrayList<String>();
		System.out.println(methodName);
		while (iter.hasNext()) {
			StringBuffer nodes = new StringBuffer();
			Path<ColouredVertex,ColouredEdge> p = iter.next();
			for (ColouredEdge e:p.getEdges()) {
				ColouredVertex v = e.getStart();
				System.out.print(v);
				System.out.print(" > ");
				nodes.append(v.getId());
				nodes.append(",");
			}
			System.out.print(start);
			nodes.append(start.getId());
			System.out.println();
			list.add(nodes.toString());
		}	
		System.out.println();
		return list;
	}
		
		

	private ColouredVertex getVertex(DirectedGraph<ColouredVertex, ColouredEdge> graph, String name) throws Exception {
		for (ColouredVertex v:graph.getVertices()) {
			if (name.equals(v.getId())) return v;
		}
		throw new Exception("Vertex not found: " + name);
	}
	
	
}
