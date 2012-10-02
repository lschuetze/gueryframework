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

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * This is the no-cache policy.
 * @author jens dietrich
 */
public class NullCache implements ElementCache {

	@Override
	public Vertex getCachedVertex(Vertex vertex) {
		return vertex;
	}

	@Override
	public Edge getCachedEdge(Edge edge) {
		return edge;
	}
	@Override
	public boolean ensuresReferentialIntegrity () {
		return false;
	}

}
