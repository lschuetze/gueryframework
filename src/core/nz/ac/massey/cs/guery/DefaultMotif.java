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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Simple motif implementation.
 * @author jens dietrich
 *
 */

public class DefaultMotif<V,E> implements Motif<V,E> {
	
	private List<String> roles = new ArrayList<String>();
	private List<String> pathRoles = new ArrayList<String>();
	private List<String> negatedPathRoles = new ArrayList<String>();
	private String name = null;
	private Collection<GroupByClause<V>> groupByClauses = new ArrayList<GroupByClause<V>>();
	private Collection<Processor<V,E>> graphProcessors = new ArrayList<Processor<V,E>>();
	private List<Constraint> constraints = new ArrayList<Constraint>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<Constraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	@Override
	public Collection<GroupByClause<V>> getGroupByClauses() {	
		return groupByClauses;
	}
	public void setGroupByClauses(Collection<GroupByClause<V>> groupBy){
		this.groupByClauses = groupBy;
	}
	@Override
	public Collection<Processor<V,E>> getGraphProcessors() {
		return graphProcessors;
	}
	public void setGraphProcessor(Collection<Processor<V,E>> processors){
		this.graphProcessors = processors; 
	}
	public List<String> getPathRoles() {
		return pathRoles;
	}
	public void setPathRoles(List<String> pathRoles) {
		this.pathRoles = pathRoles;
	}
	public List<String> getNegatedPathRoles() {
		return negatedPathRoles;
	}
	public void setNegatedPathRoles(List<String> negatedPathRoles) {
		this.negatedPathRoles = negatedPathRoles;
	}

}
