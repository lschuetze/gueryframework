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


/**
 * Custom vertex class.
 * @author jens dietrich
 */
@SuppressWarnings("serial")
public class PackageNode extends nz.ac.massey.cs.guery.Vertex<PackageReference> {
	private String name = "";
	private String container = null;
	private int size = 0;

	public PackageNode(String id) {
		super(id);
	}
	public PackageNode() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String toString() {
		return new StringBuffer() 
			.append(this.getId())
			.append(':')
			.append(this.name)
			.toString();
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public void copyValuesTo(PackageNode v) {
		// super.copyValuesTo(v);
		v.setName(name);
		v.setContainer(container);
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
}
