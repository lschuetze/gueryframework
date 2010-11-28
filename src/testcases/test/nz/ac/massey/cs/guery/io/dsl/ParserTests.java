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

package test.nz.ac.massey.cs.guery.io.dsl;

import static org.junit.Assert.*;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import nz.ac.massey.cs.guery.*;
import nz.ac.massey.cs.guery.io.dsl.DefaultMotifReader;

import org.junit.Test;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import nz.ac.massey.cs.guery.MotifReaderException;


public class ParserTests {
	
	private static Collection filterByType(Collection coll,final Class type) {
		return Collections2.filter(coll,new Predicate(){
			@Override
			public boolean apply(Object arg) {
				return type.isAssignableFrom(arg.getClass());
			}});
	}
	
	private Motif load(String name) throws Exception {
		InputStream in = ParserTests.class.getResourceAsStream("/test/nz/ac/massey/cs/guery/io/dsl/data/"+name);
		MotifReader r = new DefaultMotifReader();
		Motif motif = r.read(in);	
		in.close();
		return motif;
	}
	// 1S1 - query1 , should succeed, variant 1
	@Test
	public void test1S1() throws Exception {
		test1S("query1S1.guery","query_1");
	}
	@Test
	public void test1S2() throws Exception {
		test1S("query1S2.guery",null);
	}
	@Test
	public void test1S3() throws Exception {
		test1S("query1S3.guery",null);
	}
	@Test(expected=MotifReaderException.class)
	public void test1F1() throws Exception {
		load("query1F1.guery");
	}
	
	@Test(expected=MotifReaderException.class)
	public void test1F2() throws Exception {
		load("query1F2.guery");
	}
	
		
	// reusable test for variations of query 1 - success
	private void test1S(String guery,String name) throws Exception {
		Motif motif = load(guery);
		 
		
		String qname = motif.getName();
		assertEquals(name,qname);
		
		assertEquals(1,motif.getGraphProcessors().size());
		assertEquals(TestGraphProcessor.class.getName(),motif.getGraphProcessors().iterator().next().getClass().getName());
		
		assertEquals(2,motif.getRoles().size());
		assertEquals("start",motif.getRoles().get(0));
		assertEquals("end",motif.getRoles().get(1));
		
		assertEquals(2,motif.getPathRoles().size());
		assertEquals("connection2",motif.getPathRoles().get(0));
		assertEquals("connection3",motif.getPathRoles().get(1));
		
		assertEquals(1,motif.getNegatedPathRoles().size());
		assertEquals("connection1",motif.getNegatedPathRoles().get(0));
		
		Iterator<PathConstraint> pathConstraints = filterByType(motif.getConstraints(),PathConstraint.class).iterator();
		// connection(start>end)[3,4] 
		PathConstraint pc1 =  pathConstraints.next();
		assertEquals("connection1",pc1.getRole());
		assertEquals("start",pc1.getSource());
		assertEquals("end",pc1.getTarget());
		assertEquals(3,pc1.getMinLength());
		assertEquals(4,pc1.getMaxLength());
		assertFalse(pc1.isComputeAll());
		assertEquals(1,pc1.getConstraints().size());
		PropertyConstraint constraint1 = (PropertyConstraint) pc1.getConstraints().get(0);
		assertEquals("connection1.colour!='blue'",constraint1.getExpression());
		
		
		// connection2(end>start)
		PathConstraint pc2 =  pathConstraints.next();
		assertEquals("connection2",pc2.getRole());
		assertEquals("end",pc2.getSource());
		assertEquals("start",pc2.getTarget());
		assertEquals(1,pc2.getMinLength());
		assertEquals(-1,pc2.getMaxLength());
		assertTrue(pc2.isComputeAll());	
		assertEquals(1,pc2.getConstraints().size());
		PropertyConstraint constraint2 = (PropertyConstraint) pc2.getConstraints().get(0);
		assertEquals("connection2.colour=='red'",constraint2.getExpression());
		
		// connection3(end>start)[0,*]
		PathConstraint pc3 =  pathConstraints.next();
		assertEquals("connection3",pc3.getRole());
		assertEquals("end",pc3.getSource());
		assertEquals("start",pc3.getTarget());
		assertEquals(0,pc3.getMinLength());
		assertEquals(-1,pc3.getMaxLength());
		assertFalse(pc3.isComputeAll());
		assertEquals(0,pc3.getConstraints().size());
		
		assertFalse(pathConstraints.hasNext());
		
		// test remaining property constraints (those not attached to path constraints)
		Iterator<PropertyConstraint> propertyConstraints = filterByType(motif.getConstraints(),PropertyConstraint.class).iterator();
		assertEquals("start.colour=='red'",propertyConstraints.next().getExpression());
		assertEquals("end.colour=='red'",propertyConstraints.next().getExpression());
		assertFalse(propertyConstraints.hasNext());
		
		// test group by clauses
		Iterator<GroupByClause> groupByClauses = motif.getGroupByClauses().iterator();
		assertEquals("start",groupByClauses.next().getExpression());
		assertEquals("end.colour",groupByClauses.next().getExpression());
		assertFalse(groupByClauses.hasNext()); 
	}
}
