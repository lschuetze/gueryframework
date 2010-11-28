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

package nz.ac.massey.cs.guery.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import nz.ac.massey.cs.guery.Constraint;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.GroupByClause;
import nz.ac.massey.cs.guery.Motif;
import nz.ac.massey.cs.guery.Vertex;

/**
 * Utility class to control backtracking. Supports back jumping based on 
 * aggregation.
 * @author jens dietrich
 */

final class BackJumpingController<V extends Vertex<E>,E extends Edge<V>> extends Controller<V,E> {

	private int jumpBackPosition = -1; // jump back for aggregation mode
	private boolean jumpBackMode = false; // jump back for aggregation mode
	private Collection<String> aggregationRoles = null;
	private List<GroupByClause<V>> groupByClauses = null;
	private Collection<Object> resultCores = null; // used to check aggregation
	
	public BackJumpingController(Motif<V,E> motif, List<Constraint> constraints) {
		super(motif, constraints);
		
		aggregationRoles = new HashSet<String>();
		groupByClauses = new ArrayList<GroupByClause<V>>();
		resultCores = new HashSet<Object>();
		for (GroupByClause<V> gb:motif.getGroupByClauses()) {
			aggregationRoles.add(gb.getRole());
			groupByClauses.add(gb);
		}
	}
	
	public void reset() {
		super.reset();
		this.jumpBackMode = false;
	}
	/**
	 * Goes one level up.
	 */
	public void backtrack() {
		super.backtrack();	
		if (this.jumpBackMode && this.getPosition()<=this.jumpBackPosition) {
			this.jumpBackMode=false;

		}
	}
	/**
	 * Add a new entry.
	 */
	public void bind(String k,V v) {
		super.bind(k,v);
		// compute initial jumpback position
		if (jumpBackPosition==-1&&isInAggregationMode()) {
			this.aggregationRoles.remove(k);
			if (this.aggregationRoles.size()==0) {
				setBackjumpPosition(this.position);
			}
		}
		// check if we have to back into jumpback mode
		if (isInAggregationMode()&&this.position==jumpBackPosition) {
			Object core = this.createResultCore();
			if (core!=null && this.resultCores.contains(core)) {
				this.jumpBackMode=true;
				if (LOG_BIND.isDebugEnabled()) {
					LOG_BIND.debug("going back into jumpback mode at position "+position);
				}
			}
		}
	}
	
	public boolean isInJumpBackMode() {
		return jumpBackMode;
	}

	private void setBackjumpPosition(int pos) {
		this.jumpBackPosition = pos;
		if (LOG_BACKJUMP.isDebugEnabled()) {
			LOG_BACKJUMP.debug("jump back position set to " + pos);
		}		
	}
	private Object createResultCore() {
		if (groupByClauses.size()==1) {
			GroupByClause<V> gb = groupByClauses.get(0);
			V v = this.lookupVertex(gb.getRole());
			if (v==null) return null; // not enough bindings to build core
			return gb.getGroup(v);
		}
		List<Object> core = new ArrayList<Object>(groupByClauses.size());
		for (GroupByClause<V> gb:groupByClauses) {
			V v = this.lookupVertex(gb.getRole());
			if (v==null) return null; // not enough bindings to build core
			Object group = gb.getGroup(v);
			core.add(group);
		}
		return core;
	}
	private boolean isInAggregationMode() {
		return aggregationRoles!=null;
	}
	public boolean isDone() {
		boolean done =  position==constraints.size();
		if (done && isInAggregationMode()) {
			// build new result core
			Object core = this.createResultCore();
			this.resultCores.add(core);
			this.jumpBackMode = true;
		}
		return done;
	}
}
