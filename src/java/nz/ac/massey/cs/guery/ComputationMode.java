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

package nz.ac.massey.cs.guery;

/**
 * Lists computation modes that can be used by the engine.
 * <ol>
 * <li>ALL_INSTANCES all instances will be computed
 * <li>CLASSES_NO_REDUCE only classes w.r.t. group by clauses will be computed. Classes do not have all, but might have some instances. The situation that there can be multiple instances 
 * of the same class can occur if the group by functions are not injective. Example: group by type.namespace (many types will share the same namespace)
 * <li>CLASSES_REDUCE only classes w.r.t. group by clauses will be computed, and each class has exactly one element. Requires an extra reduce step that needs extra resources (time and memory).  
 * </ol>
 * @author jens dietrich
 */

public enum ComputationMode {
	CLASSES_NOT_REDUCED,CLASSES_REDUCED,ALL_INSTANCES
}
