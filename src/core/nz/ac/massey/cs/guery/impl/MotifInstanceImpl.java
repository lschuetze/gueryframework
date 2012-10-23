/**
 * Copyright 2010 Jens Dietrich 
 * @license
 */

package nz.ac.massey.cs.guery.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.Path;

/**
 * Motif instance implementation.
 * @author jens dietrich
 */

public class MotifInstanceImpl<V,E> extends Logging implements MotifInstance<V,E> {
	
	private Motif<V,E> motif = null;
	private Map<String,V> vertexBindings = new HashMap<String, V>();
	private Map<String,Path<V,E>> pathBindings = new HashMap<String,Path<V,E>>();
	private GraphAdapter<V,E> graph = null;
	private Set<V> vertices = null;
	
	MotifInstanceImpl(GraphAdapter<V,E> graph,Motif<V,E> motif,Controller<V,E> bindings) {
		this.graph = graph;
		this.motif = motif;
		this.vertexBindings.putAll(bindings.getRoleBindingsAsMap());
		this.pathBindings.putAll(bindings.getPathBindingsAsMap());
		if (LOG_INST.isDebugEnabled()) {
			LOG_INST.debug("result created: " + this);
		}
	}
	
	public Path<V,E> getPath(String roleName) {
		return pathBindings.get(roleName);
	}
		
	@Override
	public Motif<V,E> getMotif() {
		return motif;
	}
	
	@Override
	public V getVertex(String role) {
		return vertexBindings.get(role);
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("aMotifInstance(");
		boolean f = true;
		for (Map.Entry<String,V> e:this.vertexBindings.entrySet()) {
			if (f) f=false;
			else b.append(',');
			b.append(e.getKey());
			b.append("->");
			b.append(e.getValue());
		}
		b.append(")");
		return b.toString();
	}
	
	/**
	 * Get all vertices (instantiating roles and part of paths)
	 * @return a set of vertices
	 */
	public Set<V> getVertices() {
		if (vertices ==null) {
			vertices = new HashSet<V>();
			for (V v:this.vertexBindings.values()) {
				vertices.add(v);
			}
			for (Path<V,E> p:this.pathBindings.values()) {
				for (E e:p.getEdges()) {
					vertices.add(graph.getStart(e));
					vertices.add(graph.getEnd(e));
				}
			}
		}
		return vertices;
	}
}
