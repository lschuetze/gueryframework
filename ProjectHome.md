**Latest news:**
  1. License changed to Apache License 2.0 !
  1. The [Massey Architecture Explorer](http://xplrarc.massey.ac.nz/) is an HTML5 based frontend for graph-based software analysis that uses GUERY.
  1. We have created the [graph-query-benchmark project](http://code.google.com/p/graph-query-benchmarks/) to compare guery with similar tools. Some results can be found [here](http://goo.gl/bjmwV).
  1. New in v 1.4.0 (beta in the trunk): an adapter for [tinkerpops blueprints](https://github.com/tinkerpop/blueprints).

GUERY is a Java library that can be used to query graphs for instances of motifs (aka patterns). Guery supports motifs that are complex in the following sense: motifs describe sets of vertices connected by paths. Both the vertices and
constraints have to satisfy constraints w.r.t. (vertex and edge) labels and the length of the paths. This definition of motif is significantly more expressive than the concept of motif often used in [bioinformatics](http://www.sciencemag.org/content/298/5594/824.full) where the vertices in the motif are only connected by edges.

Guery has the following features:

  * Motif definitions describe patterns in terms of vertex roles and paths connecting them. I.e., unlike languages like SPARQL GUERY supports the transitive closure of relationships.
  * Motif definitions can contain aggregation ("group by") clauses. When the solver is configured to find classes of motif instances modulo the group by clauses, back jumping can be used (instead of back tracking).
  * GUERY has a motif finder ("solver") that uses multithreading. This means that GUERY can take [full advantage](http://code.google.com/p/gueryframework/wiki/PerformanceData) of multicore processors.
  * GUERY uses an [observer](http://c2.com/cgi/wiki?ObserverPattern) based API. I.e., the solver produces a result stream, similar to the way [SAX XML parsers](http://www.saxproject.org/) work. Once an observer is notified, GUERY will dereference the result. This means that GUERY can evaluate queries on large graph with low constant heap space (unless the observers collect and aggregate the results in memory of course - but this is the responsibility of the application using query!). We found that memory usage is a big problem for many other graph query tools (neo4j/cypher, crocopat, jena)!
  * By default, GUERY works on graphs represented in memory using the [JUNG API](http://jung.sourceforge.net/). GUERY2 also supports working with distributed graphs that do not have to be completely loaded into memory.
  * The solver uses a path finder to traverse the graph. There are two path finder implementations available: a simple online breath first path finder, and a path finder that caches reachable nodes using [chain compression](http://portal.acm.org/citation.cfm?id=99944) applied to the [condensation](http://en.wikipedia.org/wiki/Strongly_connected_component) of the graph computed using  [Tarjan's algorithm](http://www.cs.ucsb.edu/~gilbert/cs240aSpr2011/slides/TarjanDFS.pdf).

The following two screen shots show two motif instances representing antipatterns detected by analysing the dependency graph obtained from the byte code of the OpenJDK JRE1.6.0\_05-b13. This graph has 16877 vertices and 170140 edges. The first motif instance represents that the root class of the class hierarchy `java.lang.Object` depends on classes in the AWT (user interface) package. The second motif instance represents a circular dependency between libraries (jars) in the JRE.
For details, see [this presentation](https://docs.google.com/present/edit?id=0AabefECuU_XJZGd6enA2Z25fMTU4ZHJzdDd3ZHg&hl=en#), or [J Dietrich, C McCartin: Scalable Motif Detection and Aggregation. Proceedings ADC 2012](http://crpit.com/confpapers/CRPITV124Dietrich.pdf).

![http://www-ist.massey.ac.nz/jbdietrich/query/jdk-antipattern1.png](http://www-ist.massey.ac.nz/jbdietrich/query/jdk-antipattern1.png)
![http://www-ist.massey.ac.nz/jbdietrich/query/jdk-antipattern2.png](http://www-ist.massey.ac.nz/jbdietrich/query/jdk-antipattern2.png)