/*
 * Copyright 2010 Jens Dietrich Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/agpl.html Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package test.nz.ac.massey.cs.guery.benchmark;

import java.io.Reader;
import org.jdom.Element;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * Reads graphs from xml.
 * @author jens dietrich
 */
public class GraphMLReader extends nz.ac.massey.cs.guery.adapters.jungalt.io.graphml.GraphMLReader<TypeNode,TypeRef> {

	public GraphMLReader(Reader reader) {
		super(reader);
	}

	@Override
	protected TypeRef createNewEdge(Element e) {
		return new TypeRef();
	}

	@Override
	protected TypeNode createNewVertex(Element e) {
		return new TypeNode();
	}

	@Override
	protected void readAttributes(TypeRef edge, Element e) throws GraphIOException {
		edge.setType(e.getAttributeValue("type"));
		
	}

	@Override
	protected void readAttributes(TypeNode v, Element e) throws GraphIOException {
		v.setContainer(e.getAttributeValue("container"));
		v.setNamespace(e.getAttributeValue("namespace"));
		v.setName(e.getAttributeValue("name"));
		v.setType(e.getAttributeValue("type"));
		v.setAbstract("true".equals(e.getAttributeValue("isAbstract")));
	}

}
