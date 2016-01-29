# Performance Data (Guery 1.2) #

## Data Set and Motif ##

GUERY has been used to find motifs representing structural antipatterns in graphs extracted from the byte code of Java programs. The largest graph in the data set was extracted from azureus-3.1.1.0, and had 6444 vertices (representing classes and interfaces) and 35392 edges (representing uses and inheritance relationships). We have used the following three motif definitions:

## Abstraction Without Decoupling ##

```
motif awd
select client, service, impl
where "!client.abstract" and "service.abstract" and "!impl.abstract"
connected by inherits(impl>service) and invokes(client>service)[1,1] and depends(client>impl)
where "inherits.type=='subtype'" and "invokes.type=='uses'" and "depends.type=='uses'"
group by "client" and "service"
```

## Circular Dependencies Between Name Spaces (Packages) ##

```
motif cd
select in1,in2,out1,out2
where "in1.namespace==in2.namespace" and "in1.namespace!=out1.namespace" and "in1.namespace!=out2.namespace"
connected by out(in1>out1)[1,1] and in(out2>in2)[1,1] and path(out1>out2)[0,*]
group by "in1.namespace"
```

## Subtype Knowledge ##

```
motif stk
select type,super
connected by inherits(type>super)[1,*] and uses(super>type)[1,*]
where "inherits.type=='extends' || inherits.type=='implements'" and "uses.type=='uses'"
group by "super"
```

## Number of Instances and Classes ##

> AWD instances: 9415
> AWD classes: 83657
> CD instances: 754674
> CD classes: 335
> STK instances: 1763
> STK classes: 290

# Google Mode Performance #

The next table show the time (in ms) it takes to compute the first 10 instances/classes. The solver makes results available immediately when they are found, and the user does not have to wait for the entire set to be computed. This was a major problem with some commercial software analysis tools we have used.

The excellent performance to compute initial result sets is mainly due to the observer based query interface that produces a query result stream. This is also the reason that memory requirements are very modest - instances can be dereferenced once they are consumed by the application.

Using a solver configured to use two threads, we have used PowerMac with a Intel Core 2 2.8 GHz Duo processor, the Java HotSpot(TM) 64-Bit Server VM (build 14.3-b01-101, mixed mode) for this experiment.

|**motif**|**first 10 instances**| **all instances**|**first 10 classes**|**all classes**|
|:--------|:---------------------|:-----------------|:-------------------|:--------------|
|AWD      |166ms                 | 314,188ms        | 780ms              |34,376ms       |
|CD	      | 569ms	               | 513,805ms	       | 1,472ms	           |40,833ms       |
|STK	     |89ms	                    | 8,688ms	         | 135ms	               | 1,950ms       |

# Parallelisation #

In the following experiment, we executed queries with a different number of threads on a computer with two Intel Xeon E65440@2.83GHz quad core processors, using a  Java SE 1.6.0\_16-b-1 Linux 64-bit VM. The charts below clearly show that the solver takes advantage of the multiple cores.

![http://www-ist.massey.ac.nz/jbdietrich/query/performance-multithreading-awd.png](http://www-ist.massey.ac.nz/jbdietrich/query/performance-multithreading-awd.png)
![http://www-ist.massey.ac.nz/jbdietrich/query/performance-multithreading-cd.png](http://www-ist.massey.ac.nz/jbdietrich/query/performance-multithreading-cd.png)
![http://www-ist.massey.ac.nz/jbdietrich/query/performance-multithreading-stk.png](http://www-ist.massey.ac.nz/jbdietrich/query/performance-multithreading-stk.png)