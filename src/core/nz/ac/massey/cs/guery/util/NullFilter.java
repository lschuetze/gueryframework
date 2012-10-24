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

package nz.ac.massey.cs.guery.util;

import com.google.common.base.Predicate;
/**
 * Singleton filter that accept all objects.
 * @author jens dietrich
 */
public class NullFilter implements Predicate {
	
	private NullFilter() {
		super();
	}
	
	public static final NullFilter DEFAULT = new NullFilter();

	@Override
	public boolean apply(Object obj) {
		return true;
	}

	
}