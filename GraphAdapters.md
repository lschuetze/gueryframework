# Graph Adapters #

## The Graph Adapter API ##

In Guery `1.*`, graphs were represented in memory using the JUNG library type `edu.uci.ics.jung.graph.DirectedGraph`, with special classes to represent edge and vertex types. In order to process large graphs, this changes in Guery `2.0`. There is now an interface `nz.ac.massey.cs.guery.GraphAdapter<V, E>`, where V and E can be arbitrary types representing vertices and edges, respectively.

The graph adapter API looks as follows:

```
public interface GraphAdapter<V,E> {
	Iterator<E> getInEdges(V vertex);
	Iterator<E> getInEdges(V vertex,Predicate<? super E> filter);
	Iterator<E> getOutEdges(V vertex);
	Iterator<E> getOutEdges(V vertex,Predicate<? super E> filter);
	V getStart(E edge);
	V getEnd(E edge);
	Iterator<E> getEdges();
	Iterator<E> getEdges(Predicate<? super E> filter);
	Iterator<V> getVertices();
	Iterator<V> getVertices(Comparator<? super V> comparator);
	Iterator<V> getVertices(Predicate<? super V> filter);
	int getVertexCount() throws UnsupportedOperationException;
	int getEdgeCount()  throws UnsupportedOperationException;
	void closeIterator(Iterator<?> iterator);
}
```

Graph adapters have methods to query graphs for vertices and edges, and to navigate through the graph (from edges to start/end nodes, and from vertices to incoming/outgoing edges). The APIs return iterators to support lazy computation. It is therefore not necessary to compute the entire set (of edges and vertices) immediately when these methods are invoked.  The `closeIterator()` method is used for lifecycle management: whenever the computation is finished or abandoned, it is called. This facilitates the implementation of graph adapters backed by data base or network sources - the adapter can close the respective connections.
The `get*Count()` methods are optional as the can be potentially very expensive to compute.

In Guery 2.0, all references to `edu.uci.ics.jung.graph.DirectedGraph` have been replaces with references to `nz.ac.massey.cs.guery.GraphAdapter<V, E>`.

## Built-In Adapters ##

Guery 2 has the following two built-in adapters:

  1. `nz.ac.massey.cs.guery.adapters.jung.JungAdapter` is an adapter for the JUNG type `edu.uci.ics.jung.graph.DirectedGraph` . Arbitrary vertex and edge types can be used.
  1. `nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter` is also based on `edu.uci.ics.jung.graph.DirectedGraph`. However, the vertex and edge types must be derived from the `Vertex` and `Edge` classes defined in the same package `nz.ac.massey.cs.guery.adapters.jungalt`. For graph traversal, the methods in these classes are used (instead of querying the Jung graph). This results in better performance.

## Performance ##

Guery contains a little benchmark example. The graph used is the dependency graph between classes derived from the byte code of the Azureus3.0.3.4.jar application library. This graph contains 29231 edges and 5378 vertices. The following motif is used:

```
motif cd
select in1,in2,out1,out2
where "in1.namespace==in2.namespace" and "in1.namespace!=out1.namespace" and "in1.namespace!=out2.namespace"
connected by out(in1>out1)[1,1] and in(out2>in2)[1,1] and path(out1>out2)[0,*]
group by "in1.namespace"
```

This motif represents circular dependencies between name spaces (aka packages).

We compared the performance for finding all instances between Guery 1.2 and Guery 2.0 using different adapters.
The multithreaded solver was used, the computer used was a `MacBook Pro with Intel Core 2 Duo 2.8 GHz` processor, running `Java HotSpot(TM) 64-Bit Server VM (build 17.1-b03-307, mixed mode) 1.6`.

|Query Version|Adapter|Mode|Motif Instances found|Time (ms)|
|:------------|:------|:---|:--------------------|:--------|
|Guery1.2     |n/a    |ALL |611566               |373170   |
|Guery 2.0    |`nz.ac.massey.cs.guery.adapters.jungalt.JungAdapter`|ALL |611566               |379942   |
|Guery 2.0    |`nz.ac.massey.cs.guery.adapters.jung.JungAdapter`|ALL |611566               |440607   |