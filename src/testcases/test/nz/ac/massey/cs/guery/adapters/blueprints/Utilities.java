package test.nz.ac.massey.cs.guery.adapters.blueprints;


import java.io.*;
import java.util.List;
import nz.ac.massey.cs.guery.ComputationMode;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.MotifReader;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.impl.GQLImpl;
import nz.ac.massey.cs.guery.io.dsl.DefaultMotifReader;
import nz.ac.massey.cs.guery.util.ResultCollector;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class Utilities {
	
	public static boolean PRINT_PATH_ROLES = true;
	
    public static Motif<Vertex,Edge> loadMotif(String def) throws Exception {
		MotifReader<Vertex,Edge> reader = new DefaultMotifReader<Vertex,Edge>();
		InputStream in = new StringBufferInputStream(def);
        return reader.read(in);
	}
    
    public static Motif<Vertex,Edge> loadMotif2(String def) throws Exception {
		MotifReader<Vertex,Edge> reader = new DefaultMotifReader<Vertex,Edge>();
		InputStream in = new StringBufferInputStream(def);
        return reader.read(in);
	}

	
	public static void printResult(MotifInstance<Vertex,Edge> result,int counter,int size, String... roles) throws Exception {
		PrintStream out = System.out;
		Motif<Vertex,Edge> motif = result.getMotif();
		
		out.println("result " + counter + "/" + size);
		for (String role:motif.getRoles())  {
			Vertex node = result.getVertex(role);
			out.print(role);
			out.print(" -> ");
			out.println(node);
		}
		if (PRINT_PATH_ROLES) {
			for (String role:motif.getPathRoles())  {
				Path<Vertex,Edge> path = result.getPath(role);
				out.print(role);
				out.print(" -> ");
				out.print(path.getStart());
				for (Edge edge:path.getEdges()) {
					out.print(" >[");
					out.print(edge);
					out.print("] ");
					out.println(edge.getVertex(Direction.OUT));
				}
			}
		}
			
		out.println(".");
	}
	
	public static void printResults(List<MotifInstance<Vertex,Edge>> results,String... roles) throws Exception {
		int counter = 0;
		int size = results.size();
		for (MotifInstance<Vertex,Edge> result:results) {
			printResult(result,++counter,size,roles);
		}
	}
	
	public static List<MotifInstance<Vertex,Edge>> query(GraphAdapter<Vertex,Edge> graph,String def) throws Exception {
		
		// load query
		Motif<Vertex,Edge> query = loadMotif(def);
		GQL<Vertex,Edge> engine = new GQLImpl<Vertex,Edge>();
		
		ResultCollector<Vertex,Edge> collector = new ResultCollector<Vertex,Edge>();
		
		long before = System.currentTimeMillis();
		engine.query(graph, query, collector, ComputationMode.ALL_INSTANCES);
		
		List<MotifInstance<Vertex,Edge>> resultList = collector.getInstances();
		
		long after = System.currentTimeMillis();
		System.out.println("evaluated query, this took " + (after - before) + "ms, returned " + resultList.size() + " results");
		return resultList;

	}


}
