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

import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.guery.*;
import nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter;
import nz.ac.massey.cs.guery.impl.MultiThreadedGQLImpl;
import nz.ac.massey.cs.guery.util.ResultCollector;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Utils used in different test suites.
 *
 * @author jens dietrich
 */
public class TestUtils {
    public static DirectedGraph<ColouredVertex, ColouredEdge> loadGraph(String name) throws Exception {
        String src = "/testdata/" + name;
        Reader reader = new InputStreamReader(TestUtils.class.getResourceAsStream(src));
        TestGraphMLReader greader = new TestGraphMLReader(reader);
        DirectedGraph<ColouredVertex, ColouredEdge> g = greader.readGraph();
        greader.close();
        return g;
    }

    public static Motif<ColouredVertex, ColouredEdge> loadQuery(MotifReader reader, String name) throws Exception {
        String src = "/testdata/" + name;
        return reader.read(TestUtils.class.getResourceAsStream(src));
    }

    public static int countInstances(List<MotifInstance<ColouredVertex, ColouredEdge>> instances, String startVertex, String endVertex) {
        int count = 0;
        for (MotifInstance<ColouredVertex, ColouredEdge> instance : instances) {
            if (instance.getVertex("start").getId().equals(startVertex) && instance.getVertex("end").getId().equals(endVertex)) {
                count = count + 1;
            }
        }
        return count;
    }

    public static List<MotifInstance<ColouredVertex, ColouredEdge>> findInstances(MotifReader<ColouredVertex, ColouredEdge> reader, String graphSrc, String querySrc, ComputationMode mode, PathFinder<ColouredVertex, ColouredEdge> pathFinder) throws Exception {
        DirectedGraph<ColouredVertex, ColouredEdge> graph = loadGraph(graphSrc);
        Motif<ColouredVertex, ColouredEdge> motif = loadQuery(reader, querySrc);
        ResultCollector<ColouredVertex, ColouredEdge> coll = new ResultCollector<ColouredVertex, ColouredEdge>();
        long t1 = System.currentTimeMillis();
        GQL<ColouredVertex, ColouredEdge> engine = new MultiThreadedGQLImpl<ColouredVertex, ColouredEdge>();
        engine.query(new JungAdapter<ColouredVertex, ColouredEdge>(graph), motif, coll, mode, pathFinder);
        long t2 = System.currentTimeMillis();
        //System.out.println("query "+motif+" on data "+graph+ " returned "+coll.getInstances().size()+" variants");
        //System.out.println("query "+motif+" on data "+graph+ " took "+(t2-t1)+" millis");
        return coll.getInstances();
    }

    public static void print(List<MotifInstance<ColouredVertex, ColouredEdge>> results) {
        int counter = 0;
        for (MotifInstance<ColouredVertex, ColouredEdge> result : results) {
            print(result, ++counter, results.size());
        }
    }

    public static void print(MotifInstance<ColouredVertex, ColouredEdge> result, int counter, int total) {
        System.out.println("Result " + counter + "/" + total);
        Motif<ColouredVertex, ColouredEdge> m = result.getMotif();
        for (String role : m.getRoles()) {
            System.out.print(role);
            System.out.print(" -> ");
            System.out.println(result.getVertex(role));
        }
        for (String role : m.getPathRoles()) {
            System.out.print(role);
            System.out.print(" -> ");
            print(result.getPath(role));
            System.out.println();
        }
        System.out.println();
    }

    public static void print(Path<ColouredVertex, ColouredEdge> path) {
        boolean f = true;
        for (ColouredEdge e : path.getEdges()) {
            if (f) {
                f = false;
                System.out.print(e.getStart());
            }
            System.out.print(',');
            System.out.print(e.getEnd());
        }
    }
}
