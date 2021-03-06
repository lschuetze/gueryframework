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


package nz.ac.massey.cs.guery.impl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Some loggers are defined here.
 *
 * @author jens dietrich
 */
public class Logging {
    public final static Logger LOG = Logger.getLogger(Logging.class);
    public final static Logger LOG_GQL = Logger.getLogger(GQLImpl.class);
    public final static Logger LOG_BIND = Logger.getLogger("" + Controller.class + ":binding");
    public final static Logger LOG_BACKJUMP = Logger.getLogger("" + Controller.class + ":backjump");
    public final static Logger LOG_INST = Logger.getLogger(MotifInstanceImpl.class);
    public final static Logger LOG_SCHED = Logger.getLogger(ConstraintScheduler.class);
    public final static Logger LOG_PATHFINDER = Logger.getLogger(BreadthFirstPathFinder.class);

    public static void setLogLevel(Level level) {
        LOG_GQL.setLevel(level);
        LOG_BIND.setLevel(level);
        LOG_BACKJUMP.setLevel(level);
        LOG_INST.setLevel(level);
        LOG_SCHED.setLevel(level);
        LOG_PATHFINDER.setLevel(level);
    }

}
