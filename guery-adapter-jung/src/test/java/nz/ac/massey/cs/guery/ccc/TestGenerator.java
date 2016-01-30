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

import java.io.File;
import java.util.*;
import javax.swing.JFileChooser;
import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import nz.ac.massey.cs.gql4jung.io.JarReader;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Utility to create junit feeder methods.
 * @author jens dietrich
 */
public class TestGenerator {
	public static void main(String[] args) throws Exception {
		//String src = "data/log4j-1.2.15.jar";
		DirectedGraph<TypeNode, TypeRef> g = null;
		File file = null;
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("testdata"));
		fc.setDialogTitle("Select jar file for test data generation");
		int returnVal = fc.showOpenDialog(null);

		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            JarReader reader = new JarReader(file);
    		g = reader.readGraph();
		}
		else {
			System.err.println("No jar file selected, JVM will exit");
			System.exit(0);
		}	
		
		System.out.println("Vertices in " + file.getName() + ": " + g.getVertexCount() );
		System.out.println("Edges in " + file.getName() + ": " + g.getEdgeCount() );
		
		List<TypeNode> nodes = new ArrayList<TypeNode>();
		nodes.addAll(g.getVertices()); // fix order
		
		int count = 0;
		
		System.out.println("\nGenerated JUnit feeder method follows (copy and paste this into parameterized JUnit test case):\n\n");
		
		System.out.println("@Parameters" );
		System.out.println("public static Collection<Object[]> getTestData() {" );
		System.out.println("\treturn Arrays.asList(new Object[][] {" );
		int MAX = 100;
		Set<String> controlSet = new HashSet<String>();
		
		// positive test cases
		System.out.println("\t\t// positive test case (reachable)");
		while (count<MAX) {
			TypeNode v1 = nodes.get(new Random().nextInt(nodes.size()));
			TypeNode v2 = nodes.get(new Random().nextInt(nodes.size()));			
			if (v1!=v2) {
				String s = v1.getFullname() + "->" + v2.getFullname();
				DijkstraShortestPath SP = new DijkstraShortestPath(g);
				List<TypeRef> path = SP.getPath(v1,v2);
				boolean isReachable = !path.isEmpty();
				if (isReachable && controlSet.add(s)) {
					count = count+1;
					printRecord(v1,v2,path,isReachable,count,2*MAX);
				}
			}
		}
		
		System.out.println("\t\t// negative test case (not reachable)");
		while (count<2*MAX) {
			TypeNode v1 = nodes.get(new Random().nextInt(nodes.size()));
			TypeNode v2 = nodes.get(new Random().nextInt(nodes.size()));			
			if (v1!=v2) {
				String s = v1.getFullname() + "->" + v2.getFullname();
				DijkstraShortestPath SP = new DijkstraShortestPath(g);
				List<TypeRef> path = SP.getPath(v1,v2);
				boolean isReachable = !path.isEmpty();
				if (!isReachable && controlSet.add(s)) {
					count = count+1;
					printRecord(v1,v2,path,isReachable,count,2*MAX);
				}
			}
		}
		
		System.out.println("\t});" );
		System.out.println("}" );
		
	}
	
	private static void printRecord(TypeNode v1,TypeNode v2,List<TypeRef> path,boolean isReachable,int count,int MAX) {
		System.out.print("\t\t{\"");
		System.out.print(v1.getFullname());
		System.out.print("\",\"");
		System.out.print(v2.getFullname());
		System.out.print("\",");
		System.out.print(!path.isEmpty());
		System.out.print("}");
		if (count<MAX) System.out.print(",");
		if (isReachable) {
			System.out.print(" // ");
			System.out.print(path);
		}
		System.out.println();		
	}
}
