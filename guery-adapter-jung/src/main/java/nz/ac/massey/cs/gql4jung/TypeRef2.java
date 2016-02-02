/**
 * Copyright 2010 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package nz.ac.massey.cs.gql4jung;

import nz.ac.massey.cs.guery.adapters.jungalt.Edge;

// Referenced classes of package nz.ac.massey.cs.gql4jung:
// TypeNode
public class TypeRef2 extends Edge {
    public TypeRef2(String id, TypeNode2 end, TypeNode2 start) {
        super(id, end, start);
        type = null;
    }

    public TypeRef2() {
        type = null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void copyValuesTo(TypeRef2 e) {
        e.setType(type);
    }

    private String type;
}