/*
 * Copyright 2012 Jens Dietrich Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/agpl.html Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */


package nz.ac.massey.cs.guery.adapters.blueprints;

import java.util.Comparator;
import java.util.Iterator;
import nz.ac.massey.cs.guery.GraphAdapter;
import com.google.common.base.Predicate;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.tinkerpop.blueprints.*;

/**
 * An adapter for blueprint dbs. 
 * @author jens dietrich
 */
public class BluePrintAdapter implements GraphAdapter<Vertex,Edge> {
	
	private Graph g = null;
	private ElementCache cache = new DefaultCache();
	
	public BluePrintAdapter(Graph g, ElementCache cache) {
		super();
		this.g = g;
		this.cache = cache;
	}

	public BluePrintAdapter(Graph g) {
		super();
		this.g = g;
	}
	
	Function<Vertex,Vertex> vertexCaching = new Function<Vertex,Vertex> () {
		public Vertex apply(Vertex vertex) {
			return cache.getCachedVertex(vertex);
		}
	};
	Function<Edge,Edge> edgeCaching = new Function<Edge,Edge> () {
		public Edge apply(Edge edge) {
			return cache.getCachedEdge(edge);
		}
	};

	@Override
	public void closeIterator(Iterator<?> iterator) {
		// nothing to to here, subclasses could override this
	}

	/**
	 * Warning - this operation requires a full enumeration of all edges and should be avoided.
	 */
	@Override
	public int getEdgeCount() throws UnsupportedOperationException {
		return count(this.getEdges());
	}

	@Override
	public Iterator<Edge> getEdges() {
		return Iterators.transform(g.getEdges().iterator(),this.edgeCaching);
	}

	@Override
	public Iterator<Edge> getEdges(Predicate<? super Edge> filter) {
		return Iterators.transform(g.getEdges().iterator(),this.edgeCaching);
	}

	/*
	 *  Note that the direction mapping used by the blueprint neo4j adapter com.tinkerpop.blueprints.impls.neo4j.Neo4jEdge is not inituitive and 
	 *  contradicts the documentation in com.tinkerpop.blueprints.Core !! 
	 *  This might be a blueprints bug. 
	 */
	@Override
	public Vertex getEnd(Edge edge) {
		return edge.getVertex(Direction.IN);
	}
	
	@Override
	public Iterator<Edge> getInEdges(Vertex vertex) {
		return vertex.getEdges(Direction.IN).iterator();
	}

	@Override
	public Iterator<Edge> getInEdges(Vertex vertex, Predicate<? super Edge> filter) {
		return Iterators.filter(this.getInEdges(vertex),filter);
	}

	@Override
	public Iterator<Edge> getOutEdges(Vertex vertex) {
		return vertex.getEdges(Direction.OUT).iterator();
	}

	@Override
	public Iterator<Edge> getOutEdges(Vertex vertex, Predicate<? super Edge> filter) {
		return Iterators.filter(this.getOutEdges(vertex),filter);
	}
	/*
	 *  Note that the direction mapping used by the blueprint neo4j adapter com.tinkerpop.blueprints.impls.neo4j.Neo4jEdge is not inituitive and 
	 *  contradicts the documentation in com.tinkerpop.blueprints.Core !! 
	 *  This might be a blueprints bug. 
	 */
	@Override
	public Vertex getStart(Edge edge) {
		return edge.getVertex(Direction.OUT);
	}

	@Override
	public int getVertexCount() throws UnsupportedOperationException {
		return count(this.getVertices());
	}

	@Override
	public Iterator<Vertex> getVertices() {
		return Iterators.transform(g.getVertices().iterator(),this.vertexCaching);
	}
	
	// expensive - TODO check whether this should better throw an UnsupportedOperationException
	@Override
	public Iterator<Vertex> getVertices(Comparator<? super Vertex> comp) {
		return getVertices();
	}
	
	@Override
	public Iterator<Vertex> getVertices(Predicate<? super Vertex> filter) {
		return null;
	}
	
	private int count(Iterator<?> iter) {
		int c=0;
		while (iter.hasNext()) {
			iter.next();
			c=c+1;
		}
		return c;
	}

	

}
