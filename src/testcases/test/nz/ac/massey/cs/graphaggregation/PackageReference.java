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

import nz.ac.massey.cs.guery.Edge;

/**
 * Custom edge class.
 * @author jens dietrich
 */

@SuppressWarnings("serial")
public class PackageReference extends Edge<PackageNode>{
	
	private int strength = 0;
	
	public PackageReference(String id, PackageNode end, PackageNode start) {
		super(id,end,start);
	}
	public PackageReference() {
		super();
	}

	public String toString() {
		return new StringBuffer() 			
			.append(this.getId())
			.append(':')
			.append('[')
			.append(this.getStart())
			.append("->")
			.append(this.getEnd())
			.append(']')
			.toString();
	}
	public void copyValuesTo(PackageReference e) {
		super.copyValuesTo(e);
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getStrength() {
		return strength;
	}
}
