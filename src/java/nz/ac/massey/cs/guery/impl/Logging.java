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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
/**
 * Some loggers are defined here.
 * @author jens dietrich
 */
class Logging {
	static Logger LOG_GQL = Logger.getLogger(GQLImpl.class);
	static Logger LOG_BIND = Logger.getLogger(""+Controller.class+":binding");
	static Logger LOG_BACKJUMP = Logger.getLogger(""+Controller.class+":backjump");
	static Logger LOG_INST = Logger.getLogger(MotifInstanceImpl.class);
	static Logger LOG_SCHED = Logger.getLogger(ConstraintScheduler.class);
	static Logger LOG_PATHFINDER = Logger.getLogger(BreadthFirstPathFinder.class);
	
	static {
		BasicConfigurator.configure();
	}
	Logging() {
		super();

	}
}
