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

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nz.ac.massey.cs.graphaggregation.AggregationListener;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;

import edu.uci.ics.jung.graph.DirectedGraph;


public class AggregationTests {
	
	private class LogProgress implements AggregationListener {
		@Override
		public void done() {
			//System.out.println("aggregation done");
		}
		@Override
		public void progressMade(int progress, int total) {
			/*
			System.out.print(" ");
			System.out.print(progress);
			System.out.print("/");
			System.out.println(total);
			*/
		}
		@Override
		public void activityStarted(String message) {
			//System.out.println(message);
		}
	};
	static DirectedGraph<TypeNode,TypeReference> loadGraph(String name) throws Exception {
        String src = "/test/nz/ac/massey/cs/graphaggregation/data/"+name;
        Reader reader = new InputStreamReader(AggregationTests.class.getResourceAsStream(src));
        GraphMLReader greader = new GraphMLReader(reader);
        DirectedGraph<TypeNode,TypeReference> g = greader.readGraph();
        greader.close();
        return g;
	}
	@Test
	public void testAggregationSize() throws Exception {
		DirectedGraph<TypeNode,TypeReference> graph = loadGraph("ant.jar.graphml");
		assertTrue(graph!=null);
		Set<String> nss = new HashSet<String>();
		for (TypeNode v:graph.getVertices()) {
			nss.add(v.getNamespace());
		}
		Type2PackageAggregation aggregation = new Type2PackageAggregation();
		aggregation.setSource(graph);
		aggregation.aggregate(new LogProgress());
		DirectedGraph<PackageNode,PackageReference> aggGraph = aggregation.getTarget();
		
		System.out.println("size of aggregated graph is:" +aggGraph.getVertexCount());
		System.out.println(" " +aggGraph.getVertexCount());
		System.out.println(" " +aggGraph.getEdgeCount());
		
		assertEquals(nss.size(),aggGraph.getVertexCount());
		
		// test getChildren for nodes
		PackageNode n1 = Iterators.find(aggGraph.getVertices().iterator(),new Predicate<PackageNode>(){
			@Override
			public boolean apply(PackageNode n) {
				return "org.apache.tools.ant".equals(n.getName());
			}});

		Collection<TypeNode> children = aggregation.getChildren(n1);
		Collection<String> childrenNames = Collections2.transform(children,new Function<TypeNode,String>(){
			@Override
			public String apply(TypeNode v) {
				return v.getFullname();
			}});
		
		assertTrue(childrenNames.contains("org.apache.tools.ant.AntClassLoader$ResourceEnumeration"));
		assertTrue(childrenNames.contains("org.apache.tools.ant.DirectoryScanner"));
		assertTrue(childrenNames.contains("org.apache.tools.ant.MagicNames"));
		
		// test getParent for nodes
		TypeNode n2 = Iterators.find(graph.getVertices().iterator(),new Predicate<TypeNode>(){
			@Override
			public boolean apply(TypeNode n) {
				return "org.apache.tools.ant".equals(n.getNamespace()) && "MagicNames".equals(n.getName());
			}});
		PackageNode n3 = aggregation.getParent(n2);
		assertEquals(n1,n3);
	}
	
	@Test
	public void testGetChildren() throws Exception {
		DirectedGraph<TypeNode,TypeReference> graph = loadGraph("ant.jar.graphml");
		assertTrue(graph!=null);
		Type2PackageAggregation aggregation = new Type2PackageAggregation();
		aggregation.setSource(graph);
		aggregation.aggregate(new LogProgress());
		DirectedGraph<PackageNode,PackageReference> aggGraph = aggregation.getTarget();

		
		// test getChildren for nodes
		PackageNode n1 = Iterators.find(aggGraph.getVertices().iterator(),new Predicate<PackageNode>(){
			@Override
			public boolean apply(PackageNode n) {
				return "org.apache.tools.ant".equals(n.getName());
			}});

		Collection<TypeNode> children = aggregation.getChildren(n1);
		Collection<String> childrenNames = Collections2.transform(children,new Function<TypeNode,String>(){
			@Override
			public String apply(TypeNode v) {
				return v.getFullname();
			}});
		
		assertTrue(childrenNames.contains("org.apache.tools.ant.AntClassLoader$ResourceEnumeration"));
		assertTrue(childrenNames.contains("org.apache.tools.ant.DirectoryScanner"));
		assertTrue(childrenNames.contains("org.apache.tools.ant.MagicNames"));

	}
	
	@Test
	public void testGetParent() throws Exception {
		DirectedGraph<TypeNode,TypeReference> graph = loadGraph("ant.jar.graphml");
		assertTrue(graph!=null);
		Type2PackageAggregation aggregation = new Type2PackageAggregation();
		aggregation.setSource(graph);
		aggregation.aggregate(new LogProgress());
		DirectedGraph<PackageNode,PackageReference> aggGraph = aggregation.getTarget();

		
		// test getChildren for nodes
		PackageNode n1 = Iterators.find(aggGraph.getVertices().iterator(),new Predicate<PackageNode>(){
			@Override
			public boolean apply(PackageNode n) {
				return "org.apache.tools.ant".equals(n.getName());
			}});
		
		// test getParent for nodes
		TypeNode n2 = Iterators.find(graph.getVertices().iterator(),new Predicate<TypeNode>(){
			@Override
			public boolean apply(TypeNode n) {
				return "org.apache.tools.ant".equals(n.getNamespace()) && "MagicNames".equals(n.getName());
			}});
		PackageNode n3 = aggregation.getParent(n2);
		assertEquals(n1,n3);
	}
	
	@Test
	public void testGetChildren2() throws Exception {
		DirectedGraph<TypeNode,TypeReference> graph = loadGraph("ant.jar.graphml");
		assertTrue(graph!=null);
		Type2PackageAggregation aggregation = new Type2PackageAggregation();
		aggregation.setSource(graph);
		aggregation.aggregate(new LogProgress());
		DirectedGraph<PackageNode,PackageReference> aggGraph = aggregation.getTarget();

		
		// test getChildren for nodes
		for (PackageReference pr:aggGraph.getEdges()){
			testEdgeChildren(aggregation,pr);
			System.out.println(pr+" - strength="+pr.getStrength());
		}


	}
	private void testEdgeChildren(Type2PackageAggregation aggregation,PackageReference pr) {
		Collection<TypeReference> children = aggregation.getChildren(pr);
		assertEquals(pr.getStrength(),children.size());
		
		Set<String> startNodes = new HashSet<String>();
		Set<String> endNodes = new HashSet<String>();
		
		for (TypeReference child:children) {
			startNodes.add(child.getStart().getNamespace());
			endNodes.add(child.getEnd().getNamespace());
		}
		
		// all children should connect the same two namespaces
		assertEquals(1,startNodes.size());
		assertEquals(1,endNodes.size());
	}

}
