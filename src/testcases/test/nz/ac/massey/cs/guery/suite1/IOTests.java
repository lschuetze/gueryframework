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

import static test.nz.ac.massey.cs.guery.suite1.TestUtils.*;

import java.util.Collection;
import java.util.List;
import nz.ac.massey.cs.guery.Constraint;
import nz.ac.massey.cs.guery.GroupByClause;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifReader;
import nz.ac.massey.cs.guery.PathConstraint;
import nz.ac.massey.cs.guery.io.xml.XMLMotifReader;
import nz.ac.massey.cs.guery.mvel.CompiledPropertyConstraint;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Tests for graph and motif reader
 * @author jens dietrich
 */
public class IOTests {
	
	private MotifReader reader = new XMLMotifReader();
	
	private void mustContainsVertex(DirectedGraph<ColouredVertex,ColouredEdge> graph,final String id,final String colour) throws Exception {
		Predicate<ColouredVertex> filter = new Predicate<ColouredVertex>() {
			@Override
			public boolean apply(ColouredVertex v) {
				return id.equals(v.getId()) && colour.equals(v.getColour());
			}		
		};
		assertTrue(Iterables.find(graph.getVertices(),filter)!=null);
	}
	private void mustContainsEdge(DirectedGraph<ColouredVertex,ColouredEdge> graph,final String id,final String colour,final String source,final String target) throws Exception {
		Predicate<ColouredEdge> filter = new Predicate<ColouredEdge>() {
			@Override
			public boolean apply(ColouredEdge v) {
				return id.equals(v.getId()) && colour.equals(v.getColour()) && source.equals(v.getStart().getId())  && target.equals(v.getEnd().getId());
			}		
		};
		assertTrue(Iterables.find(graph.getEdges(),filter)!=null);
	}
	@Test
	public void testGraphMLReader() throws Exception {
		DirectedGraph<ColouredVertex,ColouredEdge> graph = loadGraph("graph1.graphml");
		mustContainsVertex(graph,"v1","red");
		mustContainsVertex(graph,"v2","blue");
		mustContainsVertex(graph,"v3","black");
		mustContainsVertex(graph,"v4","red");
		mustContainsVertex(graph,"v5","green");
		mustContainsVertex(graph,"v6","green");
		mustContainsVertex(graph,"v7","red");
		
		mustContainsEdge(graph,"e1","red","v1","v2");
		mustContainsEdge(graph,"e2","green","v2","v3");
		mustContainsEdge(graph,"e3","green","v3","v4");
		mustContainsEdge(graph,"e4","green","v4","v5");
		mustContainsEdge(graph,"e5","green","v5","v1");
		mustContainsEdge(graph,"e6","green","v2","v6");
		mustContainsEdge(graph,"e7","green","v6","v1");
		mustContainsEdge(graph,"e8","black","v3","v7");
	}

}
