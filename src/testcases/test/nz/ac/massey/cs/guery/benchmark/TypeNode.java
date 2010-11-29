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

import nz.ac.massey.cs.guery.adapters.jungalt.Vertex;


/**
 * Custom vertex class.
 * @author jens dietrich
 */
public class TypeNode extends Vertex<TypeRef> {

	// properties
	// when making this a general purpose query language, we need to remove this
	private String namespace = "";
	private String name = "";
	private boolean isAbstract = false;
	private String type = null;
	private String container = null;
	private String cluster = null;
	private String fullName = null;
	
	public TypeNode(String id) {
		super(id);
	}
	public TypeNode() {
		super();
	}
	
	public boolean isAbstract() {
		return isAbstract;
	}
	public String getType() {
		return type;
	}
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.fullName = null;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
		this.fullName = null;
	}
	public String getFullname() {
		if (this.fullName==null) {
			if (this.namespace==null||this.namespace.length()==0) {
				this.fullName = name;
			}
			else {
				this.fullName = namespace+'.'+name;
			}
		}
		return fullName;
	}
	
	public String toString() {
		return new StringBuffer() 
			.append(this.getId())
			.append(':')
			.append(this.namespace)
			.append('.')
			.append(this.name)
			.toString();
	}
	
	public String getContainer() {
		return container;
	}
	public String getCluster() {
		return cluster;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	// checks for inner class relationships
	public boolean isPartOf(TypeNode v) {
		if (!this.namespace.equals(v.namespace)) return false;
		else return (this.name.startsWith(v.name+'$'));
	}
	public void copyValuesTo(TypeNode v) {
		v.setName(name);
		v.setNamespace(namespace);
		v.setContainer(container);
		v.setType(type);
		v.setCluster(cluster);
		v.setAbstract(isAbstract);
	}
	

}
