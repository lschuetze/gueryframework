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


package test.nz.ac.massey.cs.guery.io.xml;

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
import test.nz.ac.massey.cs.guery.suite1.ColouredEdge;
import test.nz.ac.massey.cs.guery.suite1.ColouredVertex;
import static org.junit.Assert.*;

/**
 * Tests for graph and motif reader
 * @author jens dietrich
 */
public class ParserTests {
	
	private MotifReader<ColouredVertex,ColouredEdge> reader = new XMLMotifReader<ColouredVertex,ColouredEdge>();
	
	@Test
	public void testMotifReader1() throws Exception {
		Motif<ColouredVertex,ColouredEdge> motif = loadQuery(reader,"query1.xml");
		
		List<String> nodeRoles = motif.getRoles();
		assertEquals(2,nodeRoles.size());
		assertEquals("start",nodeRoles.get(0));
		assertEquals("end",nodeRoles.get(1));
		
		List<String> pathRoles = motif.getPathRoles();
		assertEquals(1,pathRoles.size());
		assertEquals("connection",pathRoles.get(0));
		
		List<Constraint> constraints = motif.getConstraints();
		assertEquals(4,constraints.size());
		
		assertTrue(constraints.get(0) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c1 = (CompiledPropertyConstraint)constraints.get(0) ;
		assertEquals("start.colour==\"red\"",c1.getExpression());
		assertEquals(1,c1.getRoles().size());
		assertEquals("start",c1.getFirstRole());
		
		assertTrue(constraints.get(1) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c2 = (CompiledPropertyConstraint)constraints.get(1) ;
		assertEquals("end.colour==\"red\"",c2.getExpression());
		assertEquals(1,c2.getRoles().size());
		assertEquals("end",c2.getFirstRole());
		
		assertTrue(constraints.get(2) instanceof PathConstraint);
		PathConstraint<ColouredVertex,ColouredEdge> c3 = (PathConstraint)constraints.get(2);
		assertEquals("connection",c3.getRole());
		assertEquals("start",c3.getSource());
		assertEquals("end",c3.getTarget());
		assertEquals(-1,c3.getMaxLength());
		assertEquals(1,c3.getMinLength());
		
		assertTrue(constraints.get(3) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c4 = (CompiledPropertyConstraint)constraints.get(3) ;
		assertEquals("start!=end",c4.getExpression());
		assertEquals(2,c4.getRoles().size());
		assertEquals("start",c4.getRoles().get(0));
		assertEquals("end",c4.getRoles().get(1));
		
		Collection<GroupByClause<ColouredVertex>> aggregationClauses = motif.getGroupByClauses();
		assertEquals(1,aggregationClauses.size());
		GroupByClause<ColouredVertex> groupBy = aggregationClauses.iterator().next();
		assertEquals("start",groupBy.getExpression());
		assertEquals("start",groupBy.getRole());
	}
	
	@Test
	public void testMotifReader2() throws Exception {
		Motif<ColouredVertex,ColouredEdge> motif = loadQuery(reader,"query2.xml");
		
		List<String> nodeRoles = motif.getRoles();
		assertEquals(2,nodeRoles.size());
		assertEquals("start",nodeRoles.get(0));
		assertEquals("end",nodeRoles.get(1));
		
		List<String> pathRoles = motif.getPathRoles();
		assertEquals(1,pathRoles.size());
		assertEquals("connection",pathRoles.get(0));
		
		List<Constraint> constraints = motif.getConstraints();
		assertEquals(4,constraints.size());
		
		assertTrue(constraints.get(0) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c1 = (CompiledPropertyConstraint)constraints.get(0) ;
		assertEquals("start.colour==\"red\"",c1.getExpression());
		assertEquals(1,c1.getRoles().size());
		assertEquals("start",c1.getFirstRole());
		
		assertTrue(constraints.get(1) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c2 = (CompiledPropertyConstraint)constraints.get(1) ;
		assertEquals("end.colour==\"red\"",c2.getExpression());
		assertEquals(1,c2.getRoles().size());
		assertEquals("end",c2.getFirstRole());
		
		assertTrue(constraints.get(2) instanceof PathConstraint);
		PathConstraint c3 = (PathConstraint)constraints.get(2);
		assertEquals("connection",c3.getRole());
		assertEquals("start",c3.getSource());
		assertEquals("end",c3.getTarget());
		assertEquals(-1,c3.getMaxLength());
		assertEquals(1,c3.getMinLength());
		
		// additional tests for condition
		assertEquals(1,c3.getConstraints().size());
		CompiledPropertyConstraint c3p = (CompiledPropertyConstraint)c3.getConstraints().get(0);
		assertEquals("connection.colour!=\"black\"",c3p.getExpression());
		
		assertTrue(constraints.get(3) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c4 = (CompiledPropertyConstraint)constraints.get(3) ;
		assertEquals("start!=end",c4.getExpression());
		assertEquals(2,c4.getRoles().size());
		assertEquals("start",c4.getRoles().get(0));
		assertEquals("end",c4.getRoles().get(1));
		
		Collection<GroupByClause<ColouredVertex>> aggregationClauses = motif.getGroupByClauses();
		assertEquals(1,aggregationClauses.size());
		GroupByClause<ColouredVertex> groupBy = aggregationClauses.iterator().next();
		assertEquals("start",groupBy.getExpression());
		assertEquals("start",groupBy.getRole());
	}
	
	@Test
	public void testMotifReader3() throws Exception {
		Motif<ColouredVertex,ColouredEdge> motif = loadQuery(reader,"query3.xml");
		
		List<String> nodeRoles = motif.getRoles();
		assertEquals(2,nodeRoles.size());
		assertEquals("start",nodeRoles.get(0));
		assertEquals("end",nodeRoles.get(1));
		
		List<String> pathRoles = motif.getPathRoles();
		assertEquals(1,pathRoles.size());
		assertEquals("connection",pathRoles.get(0));
		
		List<Constraint> constraints = motif.getConstraints();
		assertEquals(4,constraints.size());
		
		assertTrue(constraints.get(0) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c1 = (CompiledPropertyConstraint)constraints.get(0) ;
		assertEquals("start.colour==\"red\"",c1.getExpression());
		assertEquals(1,c1.getRoles().size());
		assertEquals("start",c1.getFirstRole());
		
		assertTrue(constraints.get(1) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c2 = (CompiledPropertyConstraint)constraints.get(1) ;
		assertEquals("end.colour==\"red\"",c2.getExpression());
		assertEquals(1,c2.getRoles().size());
		assertEquals("end",c2.getFirstRole());
		
		assertTrue(constraints.get(2) instanceof PathConstraint);
		PathConstraint<ColouredVertex,ColouredEdge> c3 = (PathConstraint)constraints.get(2);
		assertEquals("connection",c3.getRole());
		assertEquals("start",c3.getSource());
		assertEquals("end",c3.getTarget());
		assertEquals(2,c3.getMaxLength());
		assertEquals(1,c3.getMinLength());
		
		// additional tests for condition
		assertEquals(1,c3.getConstraints().size());
		CompiledPropertyConstraint c3p = (CompiledPropertyConstraint)c3.getConstraints().get(0);
		assertEquals("connection.colour!=\"black\"",c3p.getExpression());
		
		assertTrue(constraints.get(3) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c4 = (CompiledPropertyConstraint)constraints.get(3) ;
		assertEquals("start!=end",c4.getExpression());
		assertEquals(2,c4.getRoles().size());
		assertEquals("start",c4.getRoles().get(0));
		assertEquals("end",c4.getRoles().get(1));
		
		Collection<GroupByClause<ColouredVertex>> aggregationClauses = motif.getGroupByClauses();
		assertEquals(1,aggregationClauses.size());
		GroupByClause<ColouredVertex> groupBy = aggregationClauses.iterator().next();
		assertEquals("start",groupBy.getExpression());
		assertEquals("start",groupBy.getRole());
	}
	
	@Test
	public void testMotifReader4() throws Exception {
		Motif<ColouredVertex,ColouredEdge> motif = loadQuery(reader,"query4.xml");
		
		List<String> nodeRoles = motif.getRoles();
		assertEquals(2,nodeRoles.size());
		assertEquals("start",nodeRoles.get(0));
		assertEquals("end",nodeRoles.get(1));
		
		List<String> pathRoles = motif.getPathRoles();
		assertEquals(1,pathRoles.size());
		assertEquals("connection",pathRoles.get(0));
		
		List<Constraint> constraints = motif.getConstraints();
		assertEquals(3,constraints.size());
		
		assertTrue(constraints.get(0) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c1 = (CompiledPropertyConstraint)constraints.get(0) ;
		assertEquals("start.colour==\"red\"",c1.getExpression());
		assertEquals(1,c1.getRoles().size());
		assertEquals("start",c1.getFirstRole());
		
		assertTrue(constraints.get(1) instanceof CompiledPropertyConstraint);
		CompiledPropertyConstraint c2 = (CompiledPropertyConstraint)constraints.get(1) ;
		assertEquals("end.colour==\"red\"",c2.getExpression());
		assertEquals(1,c2.getRoles().size());
		assertEquals("end",c2.getFirstRole());
		
		assertTrue(constraints.get(2) instanceof PathConstraint);
		PathConstraint<ColouredVertex,ColouredEdge> c3 = (PathConstraint)constraints.get(2);
		assertEquals("connection",c3.getRole());
		assertEquals("start",c3.getSource());
		assertEquals("end",c3.getTarget());
		assertEquals(-1,c3.getMaxLength());
		assertEquals(0,c3.getMinLength());
		
		// additional tests for condition
		assertEquals(1,c3.getConstraints().size());
		CompiledPropertyConstraint c3p = (CompiledPropertyConstraint)c3.getConstraints().get(0);
		assertEquals("connection.colour!=\"black\"",c3p.getExpression());
		
		Collection<GroupByClause<ColouredVertex>> aggregationClauses = motif.getGroupByClauses();
		assertEquals(1,aggregationClauses.size());
		GroupByClause<ColouredVertex> groupBy = aggregationClauses.iterator().next();
		assertEquals("start",groupBy.getExpression());
		assertEquals("start",groupBy.getRole());

	}

}
