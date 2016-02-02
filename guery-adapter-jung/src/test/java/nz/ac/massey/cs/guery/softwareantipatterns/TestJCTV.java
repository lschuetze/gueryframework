package nz.ac.massey.cs.guery.softwareantipatterns;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import nz.ac.massey.cs.gql4jung.io.JarReader;
import nz.ac.massey.cs.guery.ComputationMode;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.MotifReader;
import nz.ac.massey.cs.guery.PathFinder;
import nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter;
import nz.ac.massey.cs.guery.impl.GQLImpl;
import nz.ac.massey.cs.guery.io.dsl.DefaultMotifReader;
import nz.ac.massey.cs.guery.util.ResultCollector;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import edu.uci.ics.jung.graph.DirectedGraph;

@Ignore
public class TestJCTV  {
	
	protected static final String TESTDATADIR = "testdata/";
	private static DirectedGraph<TypeNode, TypeRef> graph = null;
	private static Motif<TypeNode, TypeRef> deginh = null;
	
	public TestJCTV() {
		super();
	}
	
	@BeforeClass
	public static void readGraph() throws Exception {
		File file = new File(TESTDATADIR + "jctv1.3.jar");
		JarReader reader = new JarReader(file);
		graph = reader.readGraph();
		System.out.println("Graph read from " + file.getAbsolutePath());
		System.out.println("Vertices in " + file.getName() + ": " + graph.getVertexCount() );
		System.out.println("Edges in " + file.getName() + ": " + graph.getEdgeCount() );
		
	}
	@BeforeClass
	public static void readMotif() throws Exception {
		File file = new File(TESTDATADIR + "deginh.guery");
        InputStream in = new FileInputStream(file);
        MotifReader<TypeNode,TypeRef> reader = new DefaultMotifReader<TypeNode,TypeRef>();
        deginh = reader.read(in);
 		in.close();
 		System.out.println("Read motif from " + file.getAbsolutePath());
 		
	}
	

	@Test 
	public void test1() {
		ResultCollector<TypeNode,TypeRef> collector = new ResultCollector<TypeNode,TypeRef>();
		GQL<TypeNode, TypeRef> gql = new GQLImpl<TypeNode, TypeRef>();
		gql.query(new JungAdapter<TypeNode,TypeRef>(graph), deginh, collector, ComputationMode.ALL_INSTANCES);
		List<MotifInstance<TypeNode, TypeRef>> results = collector.getInstances();
		System.out.println(results.size());
		
		// select instance
		for (MotifInstance<TypeNode, TypeRef> mi:results) {
			TypeNode t = mi.getVertex("type");
			TypeNode st = mi.getVertex("supertype");
			
			
		}
		
	}
}
