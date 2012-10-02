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

import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.MapMaker;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.id.IdEdge;
import com.tinkerpop.blueprints.util.wrappers.id.IdVertex;
/**
 * Cache implementation that creates vertex and edge wrappers. 
 * @author jens dietrich
 */
public class WrappingCache implements ElementCache {
	
	protected ConcurrentMap<Vertex,Vertex> vertexCache =  new MapMaker()
		.concurrencyLevel(16)
		.softValues()
	    .makeComputingMap(new Function<Vertex,Vertex>(){
	        public Vertex apply(Vertex v) {
	            return new GVertex(v);
	        }
	    });

	protected ConcurrentMap<Edge,Edge> edgeCache =  new MapMaker()
	    .concurrencyLevel(16)
	    .softValues()
	    .makeComputingMap(new Function<Edge,Edge>(){
	        public Edge apply(Edge v) {
	        	return new GEdge(v);
	        }
	    });

	// make these classes public to facilitate testing
	public class GVertex extends IdVertex {
		protected GVertex(Vertex baseVertex) {
			super(baseVertex);
		}
		@Override
	    public Iterable<Edge> getEdges(final Direction direction, final String... labels) {
	        return new Iterable<Edge>() {
				@Override
				public Iterator<Edge> iterator() {
					Iterator<Edge> itty = getBaseVertex().getEdges(direction,labels).iterator();
					return Iterators.transform(itty,new Function<Edge,Edge>() {
						@Override
						public Edge apply(Edge e) {
							return  getCachedEdge(e);
						}
					});
				}};
	    }
	    @Override
	    public Iterable<Vertex> getVertices(final Direction direction, final String... labels) {
	        throw new UnsupportedOperationException("this interface is not used by guery");
	    }
		@Override
		public String toString() {
			return "GVertex [id=" + getId() + ", baseElement="
					+ baseElement + "]";
		}
		@Override
		public Object getId() {
	        return this.baseElement.getId();
	    }
	        
	}
	
	public class GEdge extends IdEdge {
		protected GEdge(Edge base) {
			super(base);
		}
		@Override
	    public Vertex getVertex(final Direction direction) throws IllegalArgumentException {
			return getCachedVertex(getBaseEdge().getVertex(direction));
	    }
		@Override
		public String toString() {
			return "GEdge [id=" + getId() + ", baseElement=" + baseElement
					+ "]";
		}
		@Override
		public Object getId() {
	        return this.baseElement.getId();
	    }

	}
	

	@Override
	public boolean ensuresReferentialIntegrity () {
		return true;
	}

	@Override
	public Vertex getCachedVertex(Vertex vertex) {
		if (vertex instanceof GVertex) {
			return vertex;
		}
		else {
			return vertexCache.get(vertex);	
		}
	}

	@Override
	public Edge getCachedEdge(Edge edge) {
		if (edge instanceof GEdge) {
			return edge;
		}
		else {
			return edgeCache.get(edge);	
		}
	}

}
