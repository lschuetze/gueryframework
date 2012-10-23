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

package test.nz.ac.massey.cs.guery.softwareantipatterns;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import org.junit.BeforeClass;
import org.junit.Test;
import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import nz.ac.massey.cs.gql4jung.io.JarReader;
import nz.ac.massey.cs.guery.ComputationMode;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.MotifReader;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.ResultListener;
import nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter;
import nz.ac.massey.cs.guery.impl.GQLImpl;
import nz.ac.massey.cs.guery.io.dsl.DefaultMotifReader;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Test cases based on the dependency graph extracted from log4j-1.2.15.jar.
 * @author jens dietrich
 */

public class TestAdhocDependencyQueryLog4J  {

	
	private static DirectedGraph<TypeNode, TypeRef> graph = null;
	
	public TestAdhocDependencyQueryLog4J() {
		super();
	}
	
	@BeforeClass
	public static void readGraph() throws Exception {
		File file = new File(AbstractTest.TESTDATADIR + "log4j-1.2.15.jar");
		JarReader reader = new JarReader(file);
		graph = reader.readGraph();
		System.out.println("Graph read from " + file.getAbsolutePath());
		System.out.println("Vertices in " + file.getName() + ": " + graph.getVertexCount() );
		System.out.println("Edges in " + file.getName() + ": " + graph.getEdgeCount() );
		
	}
	


	

	@Test
	public void test() throws Exception {
		String query = 
				"motif parameterized_dep\n"+
				"select source,target\n"+
				"where \"source.namespace=='org.apache.log4j.jdbc'\" and \"target.namespace=='org.apache.log4j'\"\n"+
				"connected by dependency(source>target)\n"+
				"where \"dependency.end.namespace!='org.apache.log4j.jdbc'\" and \"dependency.start.namespace!='org.apache.log4j'\"\n";
		

		
		MotifReader<TypeNode, TypeRef> reader = new DefaultMotifReader<TypeNode, TypeRef>();
        InputStream in = new ByteArrayInputStream(query.getBytes());
        Motif<TypeNode, TypeRef> motif = reader.read(in);
        
        
        class Counter implements ResultListener<TypeNode, TypeRef> {
        	
        	int count = 0;

			@Override
			public boolean found(MotifInstance<TypeNode, TypeRef> instance) {
				// log 
				System.out.print(instance.getVertex("source").getFullname());
				Path<TypeNode, TypeRef> path = instance.getPath("dependency");
				for (TypeRef e:path.getEdges()) {
					System.out.print(" > ");
					System.out.print(e.getEnd().getFullname());
				}
				System.out.println();
				
				this.count = this.count+1;
				System.out.println(this.count);
				return true;
			}

			@Override
			public void progressMade(int progress, int total) {}

			@Override
			public void done() {}
        	
        }
        
        Counter counter = new Counter();
        GQL<TypeNode, TypeRef> engine = new GQLImpl<TypeNode, TypeRef>();
        engine.query(new JungAdapter<TypeNode, TypeRef>(graph), motif, counter, ComputationMode.ALL_INSTANCES);
        
        assertTrue(counter.count>1);
        
		
	}

}
