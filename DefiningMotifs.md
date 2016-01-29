# Defining Motifs #

Motifs can be defined using a domain specific language. This is an example query defining a motif in a simple graph where vertices and edges are labelled with colours:

```
1    // connect a red node to a different red node
2    motif query1
3    select start,end
4    where "start.colour=='red'" and "end.colour=='red'"
5    connected by connection(start>end)
6    where "start!=end"
7    group by "start"
```

This represents a motif consisting of two (3) different (6) red (4) nodes connected by a path (5). Motif instances are grouped together if their start node is the same (7). Motif definitions can contain comments (lines starting with //, (1)) and have names (2).

Motif definitions can contain constraints (4,6) that refer to vertex roles (like start and end) and path roles (like connection), respectively. Often, the constraints refer to vertex and edge labels (like colour in (4)). When the motif is instantiated, maps are computed that map vertex roles to vertices and path roles to sequences of edges. Note that the constraints as well as the group by clauses are string literarals. The reason is that they are MVEL2 expressions. They are parsed by MVEL2 and compiled into executable Java byte code. The main reason to use MVEL2 here is its expressiveness and scalability.

The next listing adds two more features: the link is now constrained by a cardinality constraint ((5), the default value for cardinality constraints is `[1,*]` - where `*` means unbound), and there is a constraint defined on the edge labels (6). Note that connection refers to a path, not an edge. The respective constraint must be satisfied for all edges within a path instantiating connection.

```
1    // connect a red node to a different red node, avoid black edges and look for connections that are at least 3 edges long
2    motif query2
3    select start,end
4    where "start.colour=='red'" and "end.colour=='red'"
5    connected by connection(start>end)[3,*]
6    where "start!=end" and "connection.colour!='black'"
7    group by "start"
```

By default, the solver will find all possible vertex bindings (vertex role to vertex mappings), but for each mapping only one path binding (path role to sequence of edge mapping). This restriction is often necessary for scalability reasons. In a graph with |V| vertices and |E| edges and a motif with m vertex roles and n edges roles, there could be e|V|m(|E|!)n instances if all path role bindings are considered. However, if all paths bindings are required, the find all flag can be used as shown in the next listing:

```
1    // connect a red node to a different red node, compute all paths
2    motif query3
3    select start,end
4    where "start.colour=='red'" and "end.colour=='red'"
5    connected by connection(start>end) find all
6    where "start!=end"
7    group by "start"
```


Motif definitions can also express that vertices should not be connected by a path of a certain type, as shown in the next example:

```
1    // connect a red node to a different red node, without back reference from end to start
2    motif query4
3    select start,end
4    where "start.colour=='red'" and "end.colour=='red'"
5    connected by connection(start>end)
6    not connected by missing(end>start)
7    where "start!=end"
8    group by "start"
```

Finally, the prepare with clause can be used to define a preprocessor. This is the fully qualified name of a class implementing the Processor interface shown below. This class will be instantiated and executed by the solver before the query is executed. This is useful for tasks like computing clusters, and adding cluster information to vertices.

```
1    prepare with com.example.GraphProcessor
```

```
1    package nz.ac.massey.cs.guery;
2    import edu.uci.ics.jung.graph.DirectedGraph;
3    public interface Processor<V,E> {
4        void process(DirectedGraph<V,E> g);
5    }
```