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

package test.nz.ac.massey.cs.guery.benchmark;

import nz.ac.massey.cs.guery.adapters.jungalt.Edge;



/**
 * Custom edge class.
 * @author jens dietrich
 */

public class TypeRef extends Edge<TypeNode>{
	
	private String type = null;
	
	public TypeRef(String id, TypeNode end, TypeNode start) {
		super(id,end,start);
	}
	public TypeRef() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void copyValuesTo(TypeRef e) {
		e.setType(type);
	}
}
