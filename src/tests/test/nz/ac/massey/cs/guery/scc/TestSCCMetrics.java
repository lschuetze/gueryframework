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

package test.nz.ac.massey.cs.guery.scc;

import static org.junit.Assert.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nz.ac.massey.cs.guery.adapters.jung.JungAdapter;
import nz.ac.massey.cs.guery.scc.SCCMetrics;
import nz.ac.massey.cs.guery.scc.TarjansAlgorithm;
import nz.ac.massey.cs.guery.util.NullFilter;

import org.junit.Test;
import test.nz.ac.massey.cs.guery.suite1.ColouredEdge;
import test.nz.ac.massey.cs.guery.suite1.ColouredVertex;
import test.nz.ac.massey.cs.guery.suite1.QueryTestsXML;
import test.nz.ac.massey.cs.guery.suite1.TestGraphMLReader;
import edu.uci.ics.jung.graph.DirectedGraph;


/**
 * Tests for SCC related metrics. 
 * @author jens dietrich
 */

public class TestSCCMetrics {
	
	public static double DELTA = 0.001;
	
	public static DirectedGraph<ColouredVertex,ColouredEdge> loadGraph(String name) throws Exception {
        String src = "/test/nz/ac/massey/cs/guery/scc/data/"+name;
        Reader reader = new InputStreamReader(QueryTestsXML.class.getResourceAsStream(src));
        TestGraphMLReader greader = new TestGraphMLReader(reader);
        DirectedGraph<ColouredVertex,ColouredEdge> g = greader.readGraph();
        greader.close();
        return g;
	}
	
	public static Set<ColouredVertex> findLargestSCC(DirectedGraph<ColouredVertex,ColouredEdge> g) {
		
		TarjansAlgorithm<ColouredVertex,ColouredEdge> finder = new TarjansAlgorithm<ColouredVertex,ColouredEdge>();
		finder.buildComponentGraph(new JungAdapter<ColouredVertex,ColouredEdge>(g), NullFilter.DEFAULT);
		Map<ColouredVertex,Set<ColouredVertex>> sccs = finder.getComponentMembership();
		Set<ColouredVertex> top = new HashSet<ColouredVertex>();
		for (Set<ColouredVertex> scc:sccs.values()) {
			if (scc.size()>top.size()) top=scc;
		}
		return top;
		
	}
	
	@Test
	public void testTanglednessInCircle () throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> g = loadGraph("circle.graphml");
		Set<ColouredVertex> scc = findLargestSCC(g);
		
		assertEquals("computed scc size incorrect",4,scc.size());	
		assertEquals("computed tangledness is incorrect",0,SCCMetrics.tangledness(scc, new JungAdapter<ColouredVertex,ColouredEdge>(g)),DELTA);
	} 
	
	@Test
	public void testDensityInCircle () throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> g = loadGraph("circle.graphml");
		Set<ColouredVertex> scc = findLargestSCC(g);
		
		assertEquals("computed scc size incorrect",4,scc.size());	
		assertEquals("computed density is incorrect",0,SCCMetrics.density(scc, new JungAdapter<ColouredVertex,ColouredEdge>(g)),DELTA);
	} 
	
	@Test
	public void testTanglednessInClique () throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> g = loadGraph("clique.graphml");
		Set<ColouredVertex> scc = findLargestSCC(g);
		
		assertEquals("computed scc size incorrect",4,scc.size());
		assertEquals("computed tangledness is incorrect",1,SCCMetrics.tangledness(scc, new JungAdapter<ColouredVertex,ColouredEdge>(g)),DELTA);
	} 
	
	@Test
	public void testDensityInClique () throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> g = loadGraph("clique.graphml");
		Set<ColouredVertex> scc = findLargestSCC(g);
		
		assertEquals("computed scc size incorrect",4,scc.size());
		assertEquals("computed tangledness is incorrect",1,SCCMetrics.density(scc, new JungAdapter<ColouredVertex,ColouredEdge>(g)),DELTA);
	} 
	
	
	@Test
	public void testDensityInMixed () throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> g = loadGraph("mixed.graphml");
		Set<ColouredVertex> scc = findLargestSCC(g);
		
		assertEquals("computed scc size incorrect",4,scc.size());
		assertEquals("computed tangledness is incorrect",0.375,SCCMetrics.density(scc, new JungAdapter<ColouredVertex,ColouredEdge>(g)),DELTA);
	} 
	
	@Test
	public void testTanglednessInMixed () throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> g = loadGraph("mixed.graphml");
		Set<ColouredVertex> scc = findLargestSCC(g);
		
		assertEquals("computed scc size incorrect",4,scc.size());
		assertEquals("computed tangledness is incorrect",0.5714,SCCMetrics.tangledness(scc, new JungAdapter<ColouredVertex,ColouredEdge>(g)),DELTA);
	} 
}
