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

package nz.ac.massey.cs.guery.softwareantipatterns;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edu.uci.ics.jung.algorithms.filters.EdgePredicateFilter;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import nz.ac.massey.cs.gql4jung.io.JarReader;
import nz.ac.massey.cs.guery.*;
import nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter;
import nz.ac.massey.cs.guery.impl.BreadthFirstPathFinder;
import nz.ac.massey.cs.guery.impl.GQLImpl;
import nz.ac.massey.cs.guery.impl.MultiThreadedGQLImpl;
import nz.ac.massey.cs.guery.impl.ccc.CCCPathFinder;
import nz.ac.massey.cs.guery.util.ResultCollector;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.assertTrue;

/**
 * Script to investigate the result consistency across different GQL/PathFinder implementations.
 *
 * @author jens dietrich
 */

@RunWith(Parameterized.class)
public class TestGQLEngineAndPathFinderConsistency extends AbstractTest {


    private static DirectedGraph<TypeNode, TypeRef> log4j = null;
    private static DirectedGraph<TypeNode, TypeRef> azureus = null;
    private static Map<String, DirectedGraph<TypeNode, TypeRef>> graphs = new HashMap<String, DirectedGraph<TypeNode, TypeRef>>();

    private GQL<TypeNode, TypeRef> gql1 = null, gql2 = null;
    private PathFinder<TypeNode, TypeRef> pf1 = null, pf2 = null;

    public TestGQLEngineAndPathFinderConsistency(GQL<TypeNode, TypeRef> gql1, GQL<TypeNode, TypeRef> gql2, PathFinder<TypeNode, TypeRef> pf1, PathFinder<TypeNode, TypeRef> pf2) {
        super(null, null);
        this.gql1 = gql1;
        this.gql2 = gql2;
        this.pf1 = pf1;
        this.pf2 = pf2;
    }

    @Parameters
    public static Collection<Object[]> getConfig() {
        return Arrays.asList(new Object[][]{
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new MultiThreadedGQLImpl<TypeNode, TypeRef>(2), new BreadthFirstPathFinder<TypeNode, TypeRef>(false), new BreadthFirstPathFinder<TypeNode, TypeRef>(false)},
                new Object[]{new MultiThreadedGQLImpl<TypeNode, TypeRef>(2), new MultiThreadedGQLImpl<TypeNode, TypeRef>(8), new BreadthFirstPathFinder<TypeNode, TypeRef>(false), new BreadthFirstPathFinder<TypeNode, TypeRef>(false)},
                new Object[]{new MultiThreadedGQLImpl<TypeNode, TypeRef>(1), new MultiThreadedGQLImpl<TypeNode, TypeRef>(2), new BreadthFirstPathFinder<TypeNode, TypeRef>(false), new BreadthFirstPathFinder<TypeNode, TypeRef>(false)},
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new MultiThreadedGQLImpl<TypeNode, TypeRef>(2), new BreadthFirstPathFinder<TypeNode, TypeRef>(false), new BreadthFirstPathFinder<TypeNode, TypeRef>(true)},
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new GQLImpl<TypeNode, TypeRef>(), new BreadthFirstPathFinder<TypeNode, TypeRef>(false), new CCCPathFinder<TypeNode, TypeRef>()},
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new MultiThreadedGQLImpl<TypeNode, TypeRef>(2), new CCCPathFinder<TypeNode, TypeRef>(), new CCCPathFinder<TypeNode, TypeRef>()}
        });

    }

    private final Predicate<TypeRef> extendsFilter = new Predicate<TypeRef>() {
        @Override
        public boolean apply(TypeRef t) {
            return t.getType().equals("extends") || t.getType().equals("implements");
        }
    };
    private final Predicate<TypeRef> usesFilter = new Predicate<TypeRef>() {
        @Override
        public boolean apply(TypeRef t) {
            return t.getType().equals("uses");
        }
    };

    @BeforeClass
    public static void readGraphs() throws Exception {
        log4j = readGraph("log4j-1.2.15.jar");
        graphs.put("log4j-1.2.15.jar", log4j);

        //azureus = readGraph("azureus-3.1.1.0.jar"); // disable for performance reasons - run overnight
        //graphs.put("azureus-3.1.1.0.jar",log4j);
    }

    private static DirectedGraph<TypeNode, TypeRef> readGraph(String name) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource(AbstractTest.TESTDATADIR + name);
        File file = new File(url.toURI());
        JarReader reader = new JarReader(file);
        DirectedGraph<TypeNode, TypeRef> graph = reader.readGraph();
        System.out.println("Graph read from " + file.getAbsolutePath());
        System.out.println("Vertices in " + file.getName() + ": " + graph.getVertexCount());
        System.out.println("Edges in " + file.getName() + ": " + graph.getEdgeCount());
        return graph;

    }

    @Test
    public void testConsistencySingleVsMultithreadedGPLWithBFPathFinder1() throws Exception {
        testConsistency(gql1, pf1, gql2, pf2);
    }

    private void testConsistency(GQL<TypeNode, TypeRef> gql1, PathFinder<TypeNode, TypeRef> pf1, GQL<TypeNode, TypeRef> gql2, PathFinder<TypeNode, TypeRef> pf2) throws Exception {
        for (String graphName : graphs.keySet()) {
            DirectedGraph<TypeNode, TypeRef> graph = graphs.get(graphName);
            System.out.println("Querying graph " + graphName);
            for (Motif<TypeNode, TypeRef> motif : motifs) {
                System.out.println("Querying with " + motif.getName());
                testConsistency(gql1, pf1, gql2, pf2, motif, graph);
                System.out.println();
            }
        }
    }

    private void testConsistency(GQL<TypeNode, TypeRef> gql1, PathFinder<TypeNode, TypeRef> pf1, GQL<TypeNode, TypeRef> gql2, PathFinder<TypeNode, TypeRef> pf2, Motif<TypeNode, TypeRef> motif, DirectedGraph<TypeNode, TypeRef> graph) throws Exception {

//		GQL<TypeNode,TypeRef> gql = new GQLImpl<TypeNode,TypeRef>();
//		PathFinder<TypeNode, TypeRef> pf1 = new BreadthFirstPathFinder<TypeNode, TypeRef>(false);
//		PathFinder<TypeNode, TypeRef> pf2 = new CCCPathFinder<TypeNode, TypeRef>();

        ResultCollector<TypeNode, TypeRef> collector1 = new ResultCollector<TypeNode, TypeRef>();
        ResultCollector<TypeNode, TypeRef> collector2 = new ResultCollector<TypeNode, TypeRef>();

        gql1.query(new JungAdapter<TypeNode, TypeRef>(graph), motif, collector1, ComputationMode.ALL_INSTANCES, pf1);
        gql2.query(new JungAdapter<TypeNode, TypeRef>(graph), motif, collector2, ComputationMode.ALL_INSTANCES, pf2);

        Function<MotifInstance<TypeNode, TypeRef>, Map<String, String>> mi2map = new Function<MotifInstance<TypeNode, TypeRef>, Map<String, String>>() {
            @Override
            public Map<String, String> apply(MotifInstance<TypeNode, TypeRef> mi) {
                Map<String, String> map = new HashMap<String, String>();
                List<String> roles = mi.getMotif().getRoles();
                for (String role : roles) {
                    map.put(role, mi.getVertex(role).getFullname());
                }
                return map;
            }
        };

        // bf path finder
        Set<Map<String, String>> resultsAsMaps1 = new HashSet<Map<String, String>>();
        resultsAsMaps1.addAll(Lists.transform(collector1.getInstances(), mi2map));
        System.out.println("Computed " + resultsAsMaps1.size() + " instances with " + gql1 + " / " + pf1);

        // ccc path finder
        Set<Map<String, String>> resultsAsMaps2 = new HashSet<Map<String, String>>();
        resultsAsMaps2.addAll(Lists.transform(collector2.getInstances(), mi2map));
        System.out.println("Computed " + resultsAsMaps2.size() + " instances with " + gql2 + " / " + pf2);

        Set<Map<String, String>> intersection = Sets.intersection(resultsAsMaps1, resultsAsMaps2);
        System.out.println("Intersection of both instance sets has " + intersection.size() + " elements");

        Set<Map<String, String>> diff12 = Sets.difference(resultsAsMaps1, resultsAsMaps2);
        System.out.println("Difference between 1st and 2nd result set: " + diff12.size() + " elements");
        for (Map<String, String> instance : diff12) {
            print(instance);
        }

        Set<Map<String, String>> diff21 = Sets.difference(resultsAsMaps2, resultsAsMaps1);
        System.out.println("Difference between 2nd and 1st result set: " + diff21.size() + " elements");
        for (Map<String, String> instance : diff12) {
            print(instance);
        }


    }

    private void print(Map<String, String> instance) {
        for (Entry<String, String> e : instance.entrySet()) {
            System.out.print(e.getKey());
            System.out.print(" -> ");
            System.out.print(e.getValue());
            System.out.println();
        }
    }


    private void testReachability(String v1, String v2, PathFinder<TypeNode, TypeRef> pf, Predicate<TypeRef> filter, DirectedGraph<TypeNode, TypeRef> graph) throws Exception {
        TypeNode t1 = findNode(v1, graph);
        TypeNode t2 = findNode(v2, graph);

        Iterator<Path<TypeNode, TypeRef>> paths = pf.findLinks(new JungAdapter<TypeNode, TypeRef>(graph), t1, 1, -1, true, filter, false);
        while (paths.hasNext()) {
            Path<TypeNode, TypeRef> path = paths.next();
            if (path.getEnd() == t2) {
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }

    private void testReachabilityDijkstra(String v1, String v2, final Predicate<TypeRef> filter, DirectedGraph<TypeNode, TypeRef> graph) throws Exception {

        TypeNode t1 = findNode(v1, graph);
        TypeNode t2 = findNode(v2, graph);

        org.apache.commons.collections15.Predicate<TypeRef> gf = new org.apache.commons.collections15.Predicate<TypeRef>() {
            @Override
            public boolean evaluate(TypeRef ref) {
                return filter.apply(ref);
            }
        };

        EdgePredicateFilter<TypeNode, TypeRef> graphFilter = new EdgePredicateFilter<TypeNode, TypeRef>(gf);
        DirectedGraph<TypeNode, TypeRef> filteredGraph = (DirectedGraph<TypeNode, TypeRef>) graphFilter.transform(graph);

        DijkstraShortestPath<TypeNode, TypeRef> SHPATH = new DijkstraShortestPath<TypeNode, TypeRef>(filteredGraph);
        List<TypeRef> p = SHPATH.getPath(t1, t2);
        System.out.println("Paths computed by Djikstra: " + p);

        assertTrue(p != null && p.size() > 0);

    }

    private TypeNode findNode(String v, DirectedGraph<TypeNode, TypeRef> graph) throws Exception {
        for (TypeNode t : graph.getVertices()) {
            if (t.getFullname().equals(v)) return t;
        }
        throw new Exception("Cannot find vertex for " + v);
    }


}
