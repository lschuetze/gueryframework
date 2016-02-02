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

import edu.uci.ics.jung.graph.DirectedGraph;
import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import nz.ac.massey.cs.gql4jung.io.JarReader;
import nz.ac.massey.cs.guery.GQL;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.PathFinder;
import nz.ac.massey.cs.guery.impl.BreadthFirstPathFinder;
import nz.ac.massey.cs.guery.impl.GQLImpl;
import nz.ac.massey.cs.guery.impl.ccc.CCCPathFinder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Test cases based on the dependency graph extracted from log4j-1.2.15.jar.
 *
 * @author jens dietrich
 */
@RunWith(Parameterized.class)
public class TestLog4J extends AbstractTest {


    private static DirectedGraph<TypeNode, TypeRef> graph = null;

    public TestLog4J(GQL<TypeNode, TypeRef> engine, PathFinder<TypeNode, TypeRef> pathFinder) {
        super(engine, pathFinder);
    }

    @BeforeClass
    public static void readGraph() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource(TESTDATADIR + "log4j-1.2.15.jar");
        File file = new File(url.toURI());
        JarReader reader = new JarReader(file);
        graph = reader.readGraph();
        System.out.println("Graph read from " + file.getAbsolutePath());
        System.out.println("Vertices in " + file.getName() + ": " + graph.getVertexCount());
        System.out.println("Edges in " + file.getName() + ": " + graph.getEdgeCount());

    }


    @Parameters
    public static Collection<Object[]> getConfig() {
        return Arrays.asList(new Object[][]{
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new BreadthFirstPathFinder<TypeNode, TypeRef>(false)},
                new Object[]{new GQLImpl<TypeNode, TypeRef>(), new CCCPathFinder<TypeNode, TypeRef>()}
        });
    }


    // motif: cd
    // data: log4j-1.2.15.jar
    public static final int EXPECTED_RESULT_COUNT_cd = 4482;
    public static final Collection<Map<String, String>> EXPECTED_RESULTS_cd = getExpectedResults_cd();

    private static Collection<Map<String, String>> getExpectedResults_cd() {
        Collection<Map<String, String>> results = new HashSet<Map<String, String>>();
        Map<String, String> result = null;

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.spi.DefaultRepositorySelector");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Hierarchy");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.RendererSupport");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PatternLayout");
        result.put("inside2", "org.apache.log4j.PropertyConfigurator");
        result.put("outside1", "org.apache.log4j.helpers.PatternParser");
        result.put("outside2", "org.apache.log4j.helpers.OptionConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.RootCategory");
        result.put("inside2", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.DefaultCategoryFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.DailyRollingFileAppender");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AsyncAppender$Dispatcher");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.DefaultCategoryFactory");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside2", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Category");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.NDC");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.HierarchyEventListener");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("inside2", "org.apache.log4j.helpers.OptionConverter");
        result.put("outside1", "org.apache.log4j.spi.Configurator");
        result.put("outside2", "org.apache.log4j.config.PropertySetter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.RollingFileAppender");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.NDC");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.HierarchyEventListener");
        result.put("inside2", "org.apache.log4j.spi.RendererSupport");
        result.put("outside1", "org.apache.log4j.Appender");
        result.put("outside2", "org.apache.log4j.or.RendererMap");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("inside2", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside1", "org.apache.log4j.NDC");
        result.put("outside2", "org.apache.log4j.Layout");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PropertyConfigurator");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LoggerRepository");
        result.put("inside2", "org.apache.log4j.spi.RootLogger");
        result.put("outside1", "org.apache.log4j.Category");
        result.put("outside2", "org.apache.log4j.LogManager");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.RollingFileAppender");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.helpers.CountingQuietWriter");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LocationInfo");
        result.put("inside2", "org.apache.log4j.spi.RendererSupport");
        result.put("outside1", "org.apache.log4j.Layout");
        result.put("outside2", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AsyncAppender");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.helpers.OptionConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.PatternParser$LocationPatternConverter");
        result.put("inside2", "org.apache.log4j.helpers.OptionConverter");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AppenderSkeleton");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.helpers.OnlyOnceErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PatternLayout");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.CountingQuietWriter");
        result.put("inside2", "org.apache.log4j.helpers.AppenderAttachableImpl");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.WriterAppender");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LoggerRepository");
        result.put("inside2", "org.apache.log4j.spi.RendererSupport");
        result.put("outside1", "org.apache.log4j.Logger");
        result.put("outside2", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("inside2", "org.apache.log4j.helpers.LogLog");
        result.put("outside1", "org.apache.log4j.spi.Configurator");
        result.put("outside2", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.FileAppender");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.helpers.QuietWriter");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("inside2", "org.apache.log4j.spi.DefaultRepositorySelector");
        result.put("outside1", "org.apache.log4j.or.RendererMap");
        result.put("outside2", "org.apache.log4j.LogManager");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AppenderSkeleton");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.HierarchyEventListener");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Appender");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PropertyConfigurator");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.Configurator");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Category");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.config.PropertySetter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("inside2", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Logger");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.RootLogger");
        result.put("inside2", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AppenderSkeleton");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.Filter");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.TTCCLayout");
        result.put("inside2", "org.apache.log4j.Priority");
        result.put("outside1", "org.apache.log4j.helpers.DateLayout");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("inside2", "org.apache.log4j.helpers.AppenderAttachableImpl");
        result.put("outside1", "org.apache.log4j.spi.Configurator");
        result.put("outside2", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.RootLogger");
        result.put("inside2", "org.apache.log4j.spi.OptionHandler");
        result.put("outside1", "org.apache.log4j.Logger");
        result.put("outside2", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Category");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.lf5.AppenderFinalizer");
        result.put("inside2", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("outside1", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        result.put("outside2", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.FileAppender");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AppenderSkeleton");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.helpers.OnlyOnceErrorHandler");
        result.put("outside2", "org.apache.log4j.helpers.OnlyOnceErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Priority");
        result.put("outside1", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.WriterAppender");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.LoggerFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.PatternParser$LocationPatternConverter");
        result.put("inside2", "org.apache.log4j.helpers.Loader");
        result.put("outside1", "org.apache.log4j.spi.LocationInfo");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Hierarchy");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.or.RendererMap");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.RollingFileAppender");
        result.put("inside2", "org.apache.log4j.MDC");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.PatternParser$LocationPatternConverter");
        result.put("inside2", "org.apache.log4j.helpers.LogLog");
        result.put("outside1", "org.apache.log4j.spi.LocationInfo");
        result.put("outside2", "org.apache.log4j.LogManager");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.PatternParser$DatePatternConverter");
        result.put("inside2", "org.apache.log4j.helpers.LogLog");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.config.PropertySetter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.lf5.StartLogFactor5");
        result.put("inside2", "org.apache.log4j.lf5.LogLevel");
        result.put("outside1", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        result.put("outside2", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$10");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.DefaultRepositorySelector");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.SyslogQuietWriter");
        result.put("inside2", "org.apache.log4j.helpers.LogLog");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.NDC");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.DefaultRepositorySelector");
        result.put("outside2", "org.apache.log4j.spi.AppenderAttachable");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.lf5.AppenderFinalizer");
        result.put("inside2", "org.apache.log4j.lf5.LogLevel");
        result.put("outside1", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        result.put("outside2", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$11");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AsyncAppender$DiscardSummary");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.helpers.OptionConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Appender");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("inside2", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside1", "org.apache.log4j.Appender");
        result.put("outside2", "org.apache.log4j.LogManager");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PropertyWatchdog");
        result.put("inside2", "org.apache.log4j.Priority");
        result.put("outside1", "org.apache.log4j.helpers.FileWatchdog");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Dispatcher");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.helpers.BoundedFIFO");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("inside2", "org.apache.log4j.helpers.Loader");
        result.put("outside1", "org.apache.log4j.spi.Configurator");
        result.put("outside2", "org.apache.log4j.or.RendererMap");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.HTMLLayout");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.spi.LocationInfo");
        result.put("outside2", "org.apache.log4j.spi.HierarchyEventListener");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.NOPLoggerRepository");
        result.put("inside2", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside1", "org.apache.log4j.Appender");
        result.put("outside2", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Hierarchy");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.HierarchyEventListener");
        result.put("outside2", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.helpers.Loader");
        result.put("outside2", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.DailyRollingFileAppender");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PropertyConfigurator");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PatternLayout");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.helpers.PatternConverter");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AppenderSkeleton");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.config.PropertySetter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("outside2", "org.apache.log4j.helpers.OptionConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.PatternParser$ClassNamePatternConverter");
        result.put("inside2", "org.apache.log4j.helpers.OptionConverter");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.WriterAppender");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AppenderSkeleton");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LoggerRepository");
        result.put("inside2", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside1", "org.apache.log4j.Category");
        result.put("outside2", "org.apache.log4j.Layout");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AsyncAppender");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.LocationInfo");
        result.put("outside2", "org.apache.log4j.spi.HierarchyEventListener");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.NOPLoggerRepository");
        result.put("inside2", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside1", "org.apache.log4j.Appender");
        result.put("outside2", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.HTMLLayout");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.LocationInfo");
        result.put("outside2", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PropertyConfigurator");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside2", "org.apache.log4j.helpers.AppenderAttachableImpl");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.OptionConverter");
        result.put("inside2", "org.apache.log4j.helpers.LogLog");
        result.put("outside1", "org.apache.log4j.PropertyConfigurator");
        result.put("outside2", "org.apache.log4j.spi.LoggingEvent");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.helpers.PatternParser$BasicPatternConverter");
        result.put("inside2", "org.apache.log4j.helpers.OptionConverter");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.LogManager");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PropertyWatchdog");
        result.put("inside2", "org.apache.log4j.Layout");
        result.put("outside1", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside2", "org.apache.log4j.spi.LocationInfo");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.AsyncAppender");
        result.put("inside2", "org.apache.log4j.Priority");
        result.put("outside1", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Category");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LoggerFactory");
        result.put("inside2", "org.apache.log4j.spi.RendererSupport");
        result.put("outside1", "org.apache.log4j.Logger");
        result.put("outside2", "org.apache.log4j.or.RendererMap");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.spi.RootLogger");
        result.put("outside2", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.PropertyConfigurator");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.helpers.OptionConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.RootCategory");
        result.put("inside2", "org.apache.log4j.spi.DefaultRepositorySelector");
        result.put("outside1", "org.apache.log4j.Category");
        result.put("outside2", "org.apache.log4j.LogManager");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.NDC");
        result.put("inside2", "org.apache.log4j.Level");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.config.PropertySetter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.RendererSupport");
        result.put("inside2", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside1", "org.apache.log4j.or.RendererMap");
        result.put("outside2", "org.apache.log4j.Logger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.LogManager");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.spi.AppenderAttachable");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.LocationInfo");
        result.put("inside2", "org.apache.log4j.spi.LoggerRepository");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.RootLogger");
        result.put("inside2", "org.apache.log4j.spi.LoggerFactory");
        result.put("outside1", "org.apache.log4j.helpers.LogLog");
        result.put("outside2", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Appender");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.spi.NOPLogger");
        result.put("inside2", "org.apache.log4j.spi.LoggingEvent");
        result.put("outside1", "org.apache.log4j.Category");
        result.put("outside2", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.Hierarchy");
        result.put("inside2", "org.apache.log4j.Logger");
        result.put("outside1", "org.apache.log4j.or.RendererMap");
        result.put("outside2", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("inside1", "org.apache.log4j.DailyRollingFileAppender");
        result.put("inside2", "org.apache.log4j.Appender");
        result.put("outside1", "org.apache.log4j.spi.ErrorHandler");
        result.put("outside2", "org.apache.log4j.spi.ErrorHandler");
        results.add(result);


        return results;
    }

    // motif: stk
    // data: log4j-1.2.15.jar
    public static final int EXPECTED_RESULT_COUNT_stk = 18;
    public static final Collection<Map<String, String>> EXPECTED_RESULTS_stk = getExpectedResults_stk();

    private static Collection<Map<String, String>> getExpectedResults_stk() {
        Collection<Map<String, String>> results = new HashSet<Map<String, String>>();
        Map<String, String> result = null;

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.PropertyWatchdog");
        result.put("supertype", "org.apache.log4j.helpers.FileWatchdog");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.NOPLogger");
        result.put("supertype", "org.apache.log4j.Logger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.NOPLogger");
        result.put("supertype", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.RootLogger");
        result.put("supertype", "org.apache.log4j.spi.AppenderAttachable");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.RootLogger");
        result.put("supertype", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.Category");
        result.put("supertype", "org.apache.log4j.spi.AppenderAttachable");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.PropertyConfigurator");
        result.put("supertype", "org.apache.log4j.spi.Configurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.DefaultRepositorySelector");
        result.put("supertype", "org.apache.log4j.spi.RepositorySelector");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.Hierarchy");
        result.put("supertype", "org.apache.log4j.spi.RendererSupport");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.DefaultCategoryFactory");
        result.put("supertype", "org.apache.log4j.spi.LoggerFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.RootLogger");
        result.put("supertype", "org.apache.log4j.Logger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.Logger");
        result.put("supertype", "org.apache.log4j.spi.AppenderAttachable");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.helpers.AppenderAttachableImpl");
        result.put("supertype", "org.apache.log4j.spi.AppenderAttachable");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.Hierarchy");
        result.put("supertype", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.Logger");
        result.put("supertype", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.NOPLoggerRepository");
        result.put("supertype", "org.apache.log4j.spi.LoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.Level");
        result.put("supertype", "org.apache.log4j.Priority");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("type", "org.apache.log4j.spi.NOPLogger");
        result.put("supertype", "org.apache.log4j.spi.AppenderAttachable");
        results.add(result);


        return results;
    }

    // motif: awd
    // data: log4j-1.2.15.jar
    public static final int EXPECTED_RESULT_COUNT_awd = 104;
    public static final Collection<Map<String, String>> EXPECTED_RESULTS_awd = getExpectedResults_awd();

    private static Collection<Map<String, String>> getExpectedResults_awd() {
        Collection<Map<String, String>> results = new HashSet<Map<String, String>>();
        Map<String, String> result = null;

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        result.put("service", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("service_impl", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$3");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerFactory");
        result.put("service_impl", "org.apache.log4j.DefaultCategoryFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.PatternParser");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$LocationPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$28");
        result.put("service", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("service_impl", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$3");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.AsyncAppender");
        result.put("service", "org.apache.log4j.Appender");
        result.put("service_impl", "org.apache.log4j.AsyncAppender");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.BasicConfigurator");
        result.put("service", "org.apache.log4j.Layout");
        result.put("service_impl", "org.apache.log4j.PatternLayout");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.DefaultLF5Configurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.AppenderAttachable");
        result.put("service_impl", "org.apache.log4j.Logger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.Logger");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.LogManager");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.Log4JLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$3");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.Log4JLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.XMLWatchdog");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$28");
        result.put("service", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("service_impl", "org.apache.log4j.lf5.PassingLogRecordFilter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.PatternParser");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$BasicPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        result.put("service", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("service_impl", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$4");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SocketServer");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.PatternParser");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$DatePatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.jmx.HierarchyDynamicMBean");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PatternLayout");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$LocationPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.xml.DOMConfigurator$ParseAction");
        result.put("service_impl", "org.apache.log4j.xml.DOMConfigurator$4");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.jmx.HierarchyDynamicMBean");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.DailyRollingFileAppender");
        result.put("service", "org.apache.log4j.AppenderSkeleton");
        result.put("service_impl", "org.apache.log4j.WriterAppender");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.LF5Appender");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.Log4JLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.PatternParser");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$MDCPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.OptionConverter");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PatternLayout");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$LiteralPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SMTPAppender");
        result.put("service", "org.apache.log4j.spi.OptionHandler");
        result.put("service_impl", "org.apache.log4j.net.SMTPAppender");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.PatternParser");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$LiteralPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.PatternParser");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$ClassNamePatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.OptionConverter");
        result.put("service", "org.apache.log4j.spi.Configurator");
        result.put("service_impl", "org.apache.log4j.PropertyConfigurator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.Logger");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SocketServer");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor");
        result.put("service", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("service_impl", "org.apache.log4j.lf5.PassingLogRecordFilter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PropertyWatchdog");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.spi.LoggingEvent");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.DefaultLF5Configurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$4");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.Log4JLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.util.LogMonitorAdapter");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.Log4JLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PropertyConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.Logger");
        result.put("service", "org.apache.log4j.spi.LoggerFactory");
        result.put("service_impl", "org.apache.log4j.DefaultCategoryFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.util.LogMonitorAdapter");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.util.AdapterLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SocketNode");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.DailyRollingFileAppender");
        result.put("service", "org.apache.log4j.spi.ErrorHandler");
        result.put("service_impl", "org.apache.log4j.helpers.OnlyOnceErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.LogManager");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.AppenderAttachable");
        result.put("service_impl", "org.apache.log4j.helpers.AppenderAttachableImpl");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$2");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.Log4JLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.spi.NOPLogger");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.spi.NOPLogger");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SimpleSocketServer");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PatternLayout");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$MDCPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.xml.DOMConfigurator$ParseAction");
        result.put("service_impl", "org.apache.log4j.xml.DOMConfigurator$2");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.Category");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.Hierarchy");
        result.put("service", "org.apache.log4j.spi.LoggerFactory");
        result.put("service_impl", "org.apache.log4j.DefaultCategoryFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PatternLayout");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$ClassNamePatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.OptionConverter");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SMTPAppender");
        result.put("service", "org.apache.log4j.spi.TriggeringEventEvaluator");
        result.put("service_impl", "org.apache.log4j.net.DefaultEvaluator");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.spi.LoggingEvent");
        result.put("service", "org.apache.log4j.spi.RendererSupport");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PropertyWatchdog");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PatternLayout");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$BasicPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PropertyConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerFactory");
        result.put("service_impl", "org.apache.log4j.DefaultCategoryFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SocketNode");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.helpers.FileWatchdog");
        result.put("service_impl", "org.apache.log4j.xml.XMLWatchdog");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.RollingFileAppender");
        result.put("service", "org.apache.log4j.AppenderSkeleton");
        result.put("service_impl", "org.apache.log4j.WriterAppender");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.helpers.PatternParser");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$CategoryPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.AppenderAttachable");
        result.put("service_impl", "org.apache.log4j.spi.NOPLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.AppenderAttachable");
        result.put("service_impl", "org.apache.log4j.Category");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.Category");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.varia.ReloadingPropertyConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.or.RendererMap");
        result.put("service", "org.apache.log4j.spi.RendererSupport");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.FileAppender");
        result.put("service", "org.apache.log4j.spi.ErrorHandler");
        result.put("service_impl", "org.apache.log4j.helpers.OnlyOnceErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.AsyncAppender$Dispatcher");
        result.put("service", "org.apache.log4j.AppenderSkeleton");
        result.put("service_impl", "org.apache.log4j.AsyncAppender");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.or.RendererMap");
        result.put("service", "org.apache.log4j.or.ObjectRenderer");
        result.put("service_impl", "org.apache.log4j.or.DefaultRenderer");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PropertyConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.net.SimpleSocketServer");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.xml.DOMConfigurator$ParseAction");
        result.put("service_impl", "org.apache.log4j.xml.DOMConfigurator$5");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.XMLWatchdog");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.RollingFileAppender");
        result.put("service", "org.apache.log4j.spi.ErrorHandler");
        result.put("service_impl", "org.apache.log4j.helpers.OnlyOnceErrorHandler");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.FilteredLogTableModel");
        result.put("service", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("service_impl", "org.apache.log4j.lf5.PassingLogRecordFilter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.nt.NTEventLogAppender");
        result.put("service", "org.apache.log4j.Layout");
        result.put("service_impl", "org.apache.log4j.TTCCLayout");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.spi.LoggingEvent");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.LogManager");
        result.put("service", "org.apache.log4j.spi.LoggerFactory");
        result.put("service_impl", "org.apache.log4j.DefaultCategoryFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.jdbc.JDBCAppender");
        result.put("service", "org.apache.log4j.Layout");
        result.put("service_impl", "org.apache.log4j.PatternLayout");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.spi.DefaultRepositorySelector");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.RendererSupport");
        result.put("service_impl", "org.apache.log4j.Hierarchy");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PatternLayout");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$DatePatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.spi.AppenderAttachable");
        result.put("service_impl", "org.apache.log4j.spi.RootLogger");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.spi.NOPLoggerRepository");
        result.put("service", "org.apache.log4j.spi.LoggerFactory");
        result.put("service_impl", "org.apache.log4j.DefaultCategoryFactory");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.util.LogFileParser");
        result.put("service", "org.apache.log4j.lf5.LogRecord");
        result.put("service_impl", "org.apache.log4j.lf5.Log4JLogRecord");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.Hierarchy");
        result.put("service", "org.apache.log4j.or.ObjectRenderer");
        result.put("service_impl", "org.apache.log4j.or.DefaultRenderer");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.helpers.FileWatchdog");
        result.put("service_impl", "org.apache.log4j.PropertyWatchdog");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.xml.DOMConfigurator");
        result.put("service", "org.apache.log4j.xml.DOMConfigurator$ParseAction");
        result.put("service_impl", "org.apache.log4j.xml.DOMConfigurator$3");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PropertyConfigurator");
        result.put("service", "org.apache.log4j.helpers.FileWatchdog");
        result.put("service_impl", "org.apache.log4j.PropertyWatchdog");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.PatternLayout");
        result.put("service", "org.apache.log4j.helpers.PatternConverter");
        result.put("service_impl", "org.apache.log4j.helpers.PatternParser$CategoryPatternConverter");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$28");
        result.put("service", "org.apache.log4j.lf5.LogRecordFilter");
        result.put("service_impl", "org.apache.log4j.lf5.viewer.LogBrokerMonitor$4");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.LogManager");
        result.put("service", "org.apache.log4j.spi.RepositorySelector");
        result.put("service_impl", "org.apache.log4j.spi.DefaultRepositorySelector");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.BasicConfigurator");
        result.put("service", "org.apache.log4j.Appender");
        result.put("service_impl", "org.apache.log4j.ConsoleAppender");
        results.add(result);

        result = new HashMap<String, String>();
        result.put("client", "org.apache.log4j.varia.ReloadingPropertyConfigurator");
        result.put("service", "org.apache.log4j.spi.LoggerRepository");
        result.put("service_impl", "org.apache.log4j.spi.NOPLoggerRepository");
        results.add(result);


        return results;
    }

    @Test
    public void testCD() throws Exception {
        List<MotifInstance<TypeNode, TypeRef>> results = query(graph, cd, engine, pathFinder);
        assertEquals(EXPECTED_RESULT_COUNT_cd, results.size());
        checkResults(results, EXPECTED_RESULTS_cd);
    }

    @Test
    public void testAWD() throws Exception {
        List<MotifInstance<TypeNode, TypeRef>> results = query(graph, awd, engine, pathFinder);
        assertEquals(EXPECTED_RESULT_COUNT_awd, results.size());
        checkResults(results, EXPECTED_RESULTS_awd);
    }

    @Test
    public void testSTK() throws Exception {
        List<MotifInstance<TypeNode, TypeRef>> results = query(graph, stk, engine, pathFinder);
        assertEquals(EXPECTED_RESULT_COUNT_stk, results.size());
        checkResults(results, EXPECTED_RESULTS_stk);
    }
}
