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

package nz.ac.massey.cs.guery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nz.ac.massey.cs.guery.util.NoPath;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
/**
 * Constraint to check the existence of paths between nodes.
 * @author jens dietrich
 */
public class PathConstraint<V,E> implements Constraint {
	
	private Predicate<E> filter = new Predicate<E>() {
		@Override
		public boolean apply(E e) {
			for (PropertyConstraint constraint:constraints) {
				if (!constraint.check(e)) return false;
			}
			return true;
		}
		
		public String toString() {
			boolean f = true;
			StringBuffer b = new StringBuffer()
				.append("PathConstraints[");
			for (PropertyConstraint c:constraints) {
				if (f) f=false;
				else b.append(" & ");
				b.append(c);
			}
			b.append("]");
			return b.toString();
		}
	};
	
	private int maxLength = -1; // this means unbound	
	private int minLength = 1;
	private String role = null;
	private String source = null;
	private String target = null;
	private boolean computeAll = false; // whether to compute only one instance or all
	private boolean negated = false; // used to express constraints that vertices are not connected


	private List<PropertyConstraint> constraints = new ArrayList<PropertyConstraint>();


	public PathConstraint() {
		super();
	}
		
	public Iterator<? extends Path<V,E>> getPossibleSources(final GraphAdapter<V,E> g,final V target,PathFinder<V, E> finder) {
		if (this.isNegated()) throw new IllegalStateException("negated path constraints can only be checked if source and target are known");
		return finder.findLinks(g,target,this.minLength,this.maxLength, false,filter,computeAll);
	}
	public Iterator<? extends Path<V,E>>  getPossibleTargets(final GraphAdapter<V,E> g, final V source,PathFinder<V, E> finder){
		if (this.isNegated()) throw new IllegalStateException("negated path constraints can only be checked if source and target are known");
		return finder.findLinks(g,source,this.minLength,this.maxLength, true, filter,computeAll);
	}

	public Iterator<? extends Path<V,E>> check(final GraphAdapter<V,E> g, final V source, final V target,PathFinder<V, E> finder){
		if (this.negated) {
			Iterator<Path<V,E>> iter = finder.findLinks(g,source,minLength,maxLength, true,filter,false);
			while (iter.hasNext()) {
				Path<V,E> path = iter.next();
				/** for debugging only
				for (E e:path.getEdges()) {
					System.out.print(e);
					System.out.print(",");
				}
				System.out.println();
				*/
				if (path.getEnd()==target) return Iterators.emptyIterator(); // represents "false"
			}
			return Iterators.singletonIterator(new NoPath<V,E>(source,target));
		}
		else {
			if (this.computeAll) {
				Iterator<Path<V,E>> allOutgoing = finder.findLinks(g,source,this.minLength,this.maxLength, true, filter,computeAll);
				Predicate<Path<V,E>> connectToTargetFilter = new Predicate<Path<V,E>> () {
					@Override
					public boolean apply(Path<V,E> p) {
						return p.getEnd()==target;
					}
				};
				return Iterators.filter(allOutgoing, connectToTargetFilter);
			}
			else {
				Iterator<Path<V,E>> iter = finder.findLinks(g,source,minLength,maxLength, true,filter,false);
				while (iter.hasNext()) {
					Path<V,E> path = iter.next();
					if (path.getEnd()==target) return Iterators.singletonIterator(path);
				}
				return Iterators.emptyIterator();
			}
		}
	}


	public String toString() {
		return new StringBuffer()
			.append("path constraint[")
			.append(this.getSource())
			.append("->")
			.append(this.getTarget())
			.append("]")
			.toString();
	}

	public int getMaxLength() {
		return maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public String getRole() {
		return role;
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void addConstraint(PropertyConstraint constraint) {
		this.constraints.add(constraint);
	}

	public boolean isComputeAll() {
		return computeAll;
	}

	public void setComputeAll(boolean computeAll) {
		this.computeAll = computeAll;
	}
	
	public List<PropertyConstraint> getConstraints() {
		return constraints;
	}

	public boolean isNegated() {
		return negated;
	}

	public void setNegated(boolean negated) {
		this.negated = negated;
	}

}

