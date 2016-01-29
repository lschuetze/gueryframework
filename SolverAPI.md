# Solver API (Guery 1.2) #

## Loading Motifs ##

Motifs are loaded as follows from guery scripts:

```
1    InputStream in = ... ;
2    MotifReader<V,E> reader = new DefaultMotifReader<V,E>();
3    Motif<V,E> motif = reader.read(in);   
```

Motifs can also be loaded from XML. The library contains the class nz.ac.massey.cs.guery.io.xml.XMLMotifReader for this purpose, the XML schema used to define the query syntax is in the repository.

## Finding Motifs ##

GUERY uses an observer-based API. To execute a query, the following information must be passed to the solver:
  1. The graph, an instance of the JUNG `DirectedGraph<V,E>` interface. Note that the parameter types must subclass the GUERY Vertex and Edge classes, respectively. This is to enable fast graph traversal.
  1. The motifs, usually obtained by importing a GUERY script.
  1. The observer. This is a class providing the callback functions. These methods are invoked by the solver whenever a new result is found. It is also used to report progress.
  1. A computation mode indicating whether to compute all instances (`ComputationMode.ALL_INSTANCES`) or only classes modulo the aggregation functions used in the motif definition (`ComputationMode.CLASSES_NOT_REDUCED` and `ComputationMode.CLASSES_REDUCED`). If `ComputationMode.CLASSES_REDUCED` is used, the solver can guarantee that there is only one instance in each class. Using `ComputationMode.CLASSES_REDUCED` is slightly slower than `ComputationMode.CLASSES_NOT_REDUCED`.

```
1     DirectedGraph<V,E> graph = … 
2     Motif<V,E> motif = ... 
3     ResultListener<V,E> listener = new ResultListener<V,E>(){ 
4         public void done() {} 
5         public boolean found(MotifInstance<V,E> instance) { 
6             // do something 
7             return true; 
8         } 
9         public void progressMade(int progress, int total) {} 
10    }; 
11    GQL<V,E> engine = new MultiThreadedGQLImpl<V,E>();
12    engine.query(graph,motif,listener,ComputationMode.ALL_INSTANCES);
```

## Analysing Motif Instances ##

Motif instances are basically maps mapping the vertex and path roles defined in the motif to vertices and paths. The following listing contains code that prints out all bindings of the vertex roles defined in the motif.

```
1    MotifInstance<V,E> instance = ...;
2    Motif<V,E> motif = instance.getMotif();
3    for (String vertexRole:m.getRoles()) {
4    V vertex = instance.getVertex(vertexRole);
5        System.out.println(vertexRole + " -> "+ vertex);
6    }
```


The next listing prints out all bindings for the path roles. Note that Motif has two related methods: getPathRoles() returns all path roles defined in the motif, while getNegatedPathRoles() defines all negated path roles (not connected by constraints).

```
1    for (String edgeRole:m.getPathRoles()) {
2        Path<V,E> path = instance.getPath(edgeRole);
3        java.util.List<E> edges = path.getEdges();
4        System.out.println(edgeRole + " -> " + edges);
5    }
```