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

import static junit.framework.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import nz.ac.massey.cs.guery.ComputationMode;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.MotifReader;
import nz.ac.massey.cs.guery.PathFinder;
import nz.ac.massey.cs.guery.impl.BreadthFirstPathFinder;
import nz.ac.massey.cs.guery.impl.GQLImpl;
import nz.ac.massey.cs.guery.impl.MultiThreadedGQLImpl;
import nz.ac.massey.cs.guery.impl.ccc.CCCPathFinder;
import nz.ac.massey.cs.guery.io.dsl.DefaultMotifReader;
import static nz.ac.massey.cs.guery.suite1.TestUtils.*;

/**
 * Query tests
 * @author jens dietrich
 */
@RunWith(Parameterized.class)
public class QueryTestsDSL {
	
	private GQL<ColouredVertex,ColouredEdge> engine = null;
	private MotifReader<ColouredVertex,ColouredEdge> reader = new DefaultMotifReader<ColouredVertex,ColouredEdge>();
	private PathFinder<ColouredVertex,ColouredEdge> pathFinder = null;
	
	public QueryTestsDSL(GQL<ColouredVertex,ColouredEdge> engine,PathFinder<ColouredVertex,ColouredEdge> pathFinder) {
			super();
			this.engine = engine;
			this.pathFinder = pathFinder;
	}
	
	@Parameters
	 public static Collection engines() {
	  return Arrays.asList(
			  new Object[][] {
					  {new GQLImpl<ColouredVertex,ColouredEdge>(),new BreadthFirstPathFinder<ColouredVertex,ColouredEdge>(false)},
					  {new MultiThreadedGQLImpl<ColouredVertex,ColouredEdge>(1),new BreadthFirstPathFinder<ColouredVertex,ColouredEdge>(false)},
					  {new MultiThreadedGQLImpl<ColouredVertex,ColouredEdge>(2),new BreadthFirstPathFinder<ColouredVertex,ColouredEdge>(false)},
					  {new MultiThreadedGQLImpl<ColouredVertex,ColouredEdge>(4),new BreadthFirstPathFinder<ColouredVertex,ColouredEdge>(false)},
					  {new GQLImpl<ColouredVertex,ColouredEdge>(),new CCCPathFinder<ColouredVertex,ColouredEdge>()},
					  {new MultiThreadedGQLImpl<ColouredVertex,ColouredEdge>(1),new CCCPathFinder<ColouredVertex,ColouredEdge>()},
					  {new MultiThreadedGQLImpl<ColouredVertex,ColouredEdge>(2),new CCCPathFinder<ColouredVertex,ColouredEdge>()},
					  {new MultiThreadedGQLImpl<ColouredVertex,ColouredEdge>(4),new CCCPathFinder<ColouredVertex,ColouredEdge>()}
			  });
	}

	@Test
	public void test1() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query1.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		//assertEquals(4,results.size());
		print(results);
		assertEquals(1,countInstances(results,"v1","v4"));
		assertEquals(1,countInstances(results,"v1","v7"));
		assertEquals(1,countInstances(results,"v4","v1"));
		assertEquals(1,countInstances(results,"v4","v7"));
	}
	@Test
	public void test2() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query2.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		assertEquals(2,results.size());
		assertEquals(1,countInstances(results,"v1","v4"));
		assertEquals(1,countInstances(results,"v4","v1"));
	}
	@Test
	public void test3() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query3.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		assertEquals(1,results.size());
		assertEquals(1,countInstances(results,"v4","v1"));
	}
	@Test
	public void test4a() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query4.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		assertEquals(5,results.size());
		assertEquals(1,countInstances(results,"v1","v1"));
		assertEquals(1,countInstances(results,"v1","v4"));
		assertEquals(1,countInstances(results,"v4","v1"));
		assertEquals(1,countInstances(results,"v4","v4"));
		assertEquals(1,countInstances(results,"v7","v7"));
	}
	@Test
	public void test4b() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query4.guery",ComputationMode.CLASSES_REDUCED,pathFinder);
		assertEquals(3,results.size()); // now, results with the same start node are identified
	}
	
	@Test
	public void test5() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph2.graphml","query1.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		print(results);
		assertEquals(1,countInstances(results,"r1","r2"));
	}
	
	@Test
	public void test6() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph2.graphml","query5.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		print(results);
		// More instances: computeAll annotation on paths!!
		// "detours" are possible
		assertEquals(3,countInstances(results,"r1","r2"));
	}
	
	@Test
	public void test7() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query6.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		print(results);
		assertEquals(1,countInstances(results,"v1","v7"));
		assertEquals(0,countInstances(results,"v1","v4"));
		assertEquals(0,countInstances(results,"v4","v1"));
		assertEquals(1,countInstances(results,"v4","v7"));
	}
	
	
	@Test
	public void test8() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query7.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		print(results);
		assertEquals(1,countInstances(results,"v1","v7"));
		assertEquals(0,countInstances(results,"v1","v4"));
		assertEquals(1,countInstances(results,"v4","v1"));
		assertEquals(1,countInstances(results,"v4","v7"));
	}
	
	@Test
	public void test9() throws Exception {
		List<MotifInstance<ColouredVertex,ColouredEdge>> results = findInstances(reader,"graph1.graphml","query8.guery",ComputationMode.ALL_INSTANCES,pathFinder);
		print(results);
		assertEquals(1,countInstances(results,"v1","v7"));
		assertEquals(0,countInstances(results,"v1","v4"));
		assertEquals(0,countInstances(results,"v4","v1"));
		assertEquals(1,countInstances(results,"v4","v7"));
	}
	

}
