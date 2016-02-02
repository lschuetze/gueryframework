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
import com.google.common.collect.Lists;
import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import nz.ac.massey.cs.guery.*;
import nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter;
import nz.ac.massey.cs.guery.impl.BreadthFirstPathFinder;
import nz.ac.massey.cs.guery.impl.GQLImpl;
import nz.ac.massey.cs.guery.impl.ccc.CCCPathFinder;
import nz.ac.massey.cs.guery.io.dsl.DefaultMotifReader;
import nz.ac.massey.cs.guery.util.ResultCollector;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Abstract test case.
 *
 * @author jens dietrich
 */

public class AbstractTest {

    protected GQL<TypeNode, TypeRef> engine = new GQLImpl<TypeNode, TypeRef>();
    protected PathFinder<TypeNode, TypeRef> pathFinder = new BreadthFirstPathFinder<TypeNode, TypeRef>(false);

    private static DirectedGraph<TypeNode, TypeRef> graph = null;
    protected static Motif<TypeNode, TypeRef> cd = null;
    protected static Motif<TypeNode, TypeRef> awd = null;
    protected static Motif<TypeNode, TypeRef> stk = null;
    protected static Motif<TypeNode, TypeRef>[] motifs = null;

    protected static final String TESTDATADIR = "testdata/";

    public AbstractTest(GQL<TypeNode, TypeRef> engine,
                        PathFinder<TypeNode, TypeRef> pathFinder) {
        super();
        this.engine = engine;
        this.pathFinder = pathFinder;
    }

    @BeforeClass
    public static void readMotifs() throws Exception {
        // read motifs
        cd = readMotif(TESTDATADIR + "cd.guery");
        awd = readMotif(TESTDATADIR + "awd.guery");
        stk = readMotif(TESTDATADIR + "stk.guery");
        motifs = new Motif[]{cd, awd, stk};

    }

    private static Motif<TypeNode, TypeRef> readMotif(String f) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource(f);
        InputStream in = new FileInputStream(new File(url.toURI()));
        MotifReader<TypeNode, TypeRef> reader = new DefaultMotifReader<>();
        Motif<TypeNode, TypeRef> motif = reader.read(in);
        System.out.println("Read motif from " + f);
        return motif;
    }

    @Parameters
    public static Collection<Object[]> getConfig() {
        return Arrays.asList(new Object[][]{
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new BreadthFirstPathFinder<TypeNode, TypeRef>(false)},
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new CCCPathFinder<TypeNode, TypeRef>()}
        });
    }


    protected void checkResults(List<MotifInstance<TypeNode, TypeRef>> results, Collection<Map<String, String>> expectedResultsCd) {

        Collection<Map<String, String>> resultsAsMaps = new HashSet<Map<String, String>>();
        resultsAsMaps.addAll(Lists.transform(results, new Function<MotifInstance<TypeNode, TypeRef>, Map<String, String>>() {

            @Override
            public Map<String, String> apply(MotifInstance<TypeNode, TypeRef> mi) {
                Map<String, String> map = new HashMap<String, String>();
                List<String> roles = mi.getMotif().getRoles();
                for (String role : roles) {
                    map.put(role, mi.getVertex(role).getFullname());
                }
                return map;
            }

        }));

        // check whether each expected result is computed (completeness)
        for (Map<String, String> expected : expectedResultsCd) {
            // for debugging
            boolean success = resultsAsMaps.contains(expected);
            if (!success) {
                System.out.println("The following instance was expected but not computed");
                for (Map.Entry<String, String> e : expected.entrySet()) {
                    System.out.print(e.getKey());
                    System.out.print(" -> ");
                    System.out.println(e.getValue());
                }
            }

            assertTrue(success);
        }

    }

    protected List<MotifInstance<TypeNode, TypeRef>> query(DirectedGraph<TypeNode, TypeRef> graph, Motif<TypeNode, TypeRef> motif, GQL<TypeNode, TypeRef> gql, PathFinder<TypeNode, TypeRef> pf) {
        ResultCollector<TypeNode, TypeRef> collector = new ResultCollector<TypeNode, TypeRef>();
        gql.query(new JungAdapter<TypeNode, TypeRef>(graph), motif, collector, ComputationMode.ALL_INSTANCES, pf);
        return collector.getInstances();
    }
}
