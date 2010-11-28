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

package test.nz.ac.massey.cs.graphaggregation;

import java.util.Collection;

import nz.ac.massey.cs.graphaggregation.AbstractGraphAggregation;

public class Type2PackageAggregation extends AbstractGraphAggregation<TypeNode, TypeReference, PackageNode,PackageReference> {
	
	private int nodeCounter = 0;
	private int edgeCounter = 0;

	@Override
	protected PackageNode buildVertex(Object id,Collection<TypeNode> verticesInGroup) {
		PackageNode node = new PackageNode();
		nodeCounter=nodeCounter+1;
		node.setId("vertex-"+nodeCounter);
		node.setName((String)id);
		node.setSize(verticesInGroup.size());
		return node;
	}

	@Override
	protected Object getGroupIdentifier(TypeNode vertex) {
		return vertex.getNamespace();
	}

	@Override
	protected PackageReference buildEdge(Collection<TypeReference> edgesInGroup, PackageNode source,PackageNode target) {
		PackageReference ref = new PackageReference();
		edgeCounter=edgeCounter+1;
		ref.setId("edge-"+edgeCounter);
		ref.setStrength(edgesInGroup.size());
		
		return ref;
	}

	protected void reset() {
		this.nodeCounter=0;
		this.edgeCounter=0;
		super.reset();
	}
}
