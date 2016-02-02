/*
 * Copyright 2011 Jens Dietrich Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/agpl.html Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package nz.ac.massey.cs.guery.benchmark;

import nz.ac.massey.cs.guery.MotifInstance;
import nz.ac.massey.cs.guery.ResultListener;

/**
 * Simple result counter.
 * @author jens dietrich
 * @param <V>
 * @param <E>
 */
public class ResultCounter<V, E> implements ResultListener<V, E> {
	private int counter = 0;

	@Override
	public synchronized boolean  found(MotifInstance<V, E> instance) {
		counter = counter+1;
		return true;
	}

	@Override
	public void progressMade(int progress, int total) {}

	@Override
	public void done() {}

	public int getCounter() {
		return counter;
	}

}
