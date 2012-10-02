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

import java.util.concurrent.ConcurrentMap;

import nz.ac.massey.cs.guery.adapters.blueprints.WrappingCache.GEdge;
import nz.ac.massey.cs.guery.adapters.blueprints.WrappingCache.GVertex;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
/**
 * Cache implementation that always consults the cache. 
 * @author jens dietrich
 */
public class AlwaysCheckCache implements ElementCache {
	
	protected ConcurrentMap<Object,Vertex> vertexCache =  new MapMaker()
		.concurrencyLevel(16)
		.softValues()
	    .makeMap();

	protected ConcurrentMap<Object,Edge> edgeCache =  new MapMaker()
	    .concurrencyLevel(16)
	    .softValues()
	    .makeMap();

	@Override
	public Vertex getCachedVertex(Vertex vertex) {
		Vertex cached = this.vertexCache.putIfAbsent(vertex.getId(),vertex);
		return cached==null?vertex:cached;
	}

	@Override
	public Edge getCachedEdge(Edge edge) {
		Edge cached = this.edgeCache.putIfAbsent(edge.getId(),edge);
		return cached==null?edge:cached;
	}

	@Override
	public boolean ensuresReferentialIntegrity() {
		return true;
	}
	

}
