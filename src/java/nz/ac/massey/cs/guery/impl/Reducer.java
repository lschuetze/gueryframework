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

import java.util.HashSet;
import java.util.Set;
import nz.ac.massey.cs.guery.Edge;
import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.ResultListener;
import nz.ac.massey.cs.guery.Vertex;

/**
 * Utility used to make sure that each class w.r.t. group by clauses computed has only one element.
 * Used in the computation mode is ConputationMode.CLASSES_REDUCED.
 * @author jens dietrich
 */
final class Reducer<V extends Vertex<E>,E extends Edge<V>> implements ResultListener<V, E> {
	private Set<Object> instanceIdentifiers = new HashSet<Object>();
	private GroupByAggregation<V,E> groupBy = new GroupByAggregation<V,E>();
	private ResultListener<V, E> delegate = null;
	
	public Reducer(ResultListener<V, E> delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void done() {
		delegate.done();
		instanceIdentifiers = null;
	}

	@Override
	public synchronized boolean found(MotifInstance<V,E> instance) {
		// check whether there already is a variant for this instance
		Object identifier = groupBy.getGroupIdentifier(instance);
		if (instanceIdentifiers.add(identifier)) {
//			System.out.println("added," + identifier+','+Thread.currentThread().getId());
			return delegate.found(instance);
		}
//		else {
//			System.out.println("rejected," + identifier+','+Thread.currentThread().getId());
//		}
		return true;
	}

	@Override
	public void progressMade(int progress, int total) {
		delegate.progressMade(progress, total);
	}
	
}
