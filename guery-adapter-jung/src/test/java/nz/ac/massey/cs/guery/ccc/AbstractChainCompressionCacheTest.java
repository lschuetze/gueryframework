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


package test.nz.ac.massey.cs.guery.ccc;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import org.junit.Test;
import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import nz.ac.massey.cs.guery.impl.ccc.ReachabilityAnalyser;
import com.google.common.base.Function;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;


/**
 * Abstract class for test cases and benchmarks based on graphs extracted from jars (java byte code).
 * @author jens dietrich
 */
public abstract class AbstractChainCompressionCacheTest {
	
	protected static final boolean GENERATE_DOT_FILES = false;
	protected TypeNode source = null;
	protected TypeNode target = null;
	protected boolean reachable = false;

	protected static void save(DirectedGraph<TypeNode, TypeRef> g, String name) throws Exception {
		if (GENERATE_DOT_FILES ) {
			File file = new File(name);
			Function<TypeNode,String> vertexLabels = new Function<TypeNode,String>() {@Override public String apply(TypeNode s) {return s.getFullname().replace('.', '_').replace('$','_');}};
			Function<TypeRef,String> edgeLabels = new Function<TypeRef,String>() {@Override public String apply(TypeRef s) {return s.getType();}};
			// new Jung2DOT().convert(g, file, vertexLabels, edgeLabels);
		}
	}

	public static void profile(Class clazz,String method,Collection<Object[]> testData) throws Exception {
		Method m = AbstractChainCompressionCacheTest.class.getMethod(method,new Class[]{});
		Object[] noparam = new Object[]{};
		long before = System.currentTimeMillis();
		for (Object[] param:testData) {
			Constructor testBuilder = clazz.getConstructor(String.class,String.class,Boolean.TYPE);
			
			AbstractChainCompressionCacheTest test = (AbstractChainCompressionCacheTest) testBuilder.newInstance((String)param[0],(String)param[1],(Boolean)param[2]);
			for (int i=0;i<1000;i++) m.invoke(test, noparam);
		}
		long after = System.currentTimeMillis();
		
		System.out.println("Testing method " + method + " took " + (after-before) + "ms");
		
	}
	
	public abstract ReachabilityAnalyser<TypeNode, TypeRef> getRA();
	
	public abstract DirectedGraph<TypeNode, TypeRef> getGraph();

	@Test
	public void testGetReachableVerticesOUT() throws Exception {
		boolean r = getRA().getReachableVertices(source,false,false).contains(target);
		assertEquals(reachable,r);
	}

	@Test
	public void testGetReachableVerticesIN() throws Exception {
		boolean r = getRA().getReachableVertices(target,true,false).contains(source);
		assertEquals(reachable,r);
	}

	@Test
	public void testIsReachableOUT() throws Exception {
		boolean r = getRA().isReachable(source,target,false);
		assertEquals(reachable,r);
	}
	
	@Test
	public void testIsReachableIN() throws Exception {
		boolean r = getRA().isReachable(target,source,true);
		assertEquals(reachable,r);
	}
	
	
	//@Test for benchmarking 
	public void testUsingJungDijkstraOnline() throws Exception {
		boolean r = !(new DijkstraShortestPath(getGraph()).getPath(source,target).isEmpty());
		assertEquals(reachable,r);
	}

}
