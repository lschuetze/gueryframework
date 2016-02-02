/*
 * Copyright 2015 Jens Dietrich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package nz.ac.massey.cs.guery.benchmark;

import java.io.Reader;

import nz.ac.massey.cs.gql4jung.TypeNode;
import nz.ac.massey.cs.gql4jung.TypeRef;
import org.jdom2.Element;
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
