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


package test.nz.ac.massey.cs.guery.suite1;

import java.io.Reader;

import org.jdom.Element;

import edu.uci.ics.jung.io.GraphIOException;

import nz.ac.massey.cs.guery.io.graphml.GraphMLReader;

public class TestGraphMLReader extends GraphMLReader<ColouredVertex,ColouredEdge> {

	public TestGraphMLReader(Reader reader) {
		super(reader);
	}

	@Override
	protected ColouredEdge createNewEdge(Element e) {
		return new ColouredEdge();
	}

	@Override
	protected ColouredVertex createNewVertex(Element e) {
		return new ColouredVertex();
	}

	@Override
	protected void readAttributes(ColouredEdge edge, Element e) throws GraphIOException {
		edge.setColour(e.getAttributeValue("colour"));
	}

	@Override
	protected void readAttributes(ColouredVertex v, Element e) throws GraphIOException {
		v.setColour(e.getAttributeValue("colour"));
	}

}
