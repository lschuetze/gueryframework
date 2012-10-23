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


package nz.ac.massey.cs.guery.io.xml;

import java.io.InputStream;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import nz.ac.massey.cs.guery.*;
import nz.ac.massey.cs.guery.mvel.CompiledGroupByClause;
import nz.ac.massey.cs.guery.mvel.CompiledPropertyConstraint;

/**
 * Builds motifs from xml streams
 * @author jens dietrich
 */
public class XMLMotifReader<V,E> implements MotifReader<V,E> {

	@Override
	public nz.ac.massey.cs.guery.Motif<V,E>  read(InputStream source) throws MotifReaderException {
		try {
			DefaultMotif<V,E>  motif = new DefaultMotif<V,E> ();
			List<String> vertexRoles = new ArrayList<String>();
			List<String> pathRoles = new ArrayList<String>();
			List<String> posPathRoles = new ArrayList<String>();
			List<String> negPathRoles = new ArrayList<String>();
			List<String> constraintDefs = new ArrayList<String>();
			List<GroupByClause<V>> groupByClauses = new ArrayList<GroupByClause<V>>();
			List<Processor<V,E> > graphProcessors = new ArrayList<Processor<V,E> >();
			List<PathConstraint<V,E> > pathConstraints = new ArrayList<PathConstraint<V,E> >();
			
			
			// use this list to keep track of the order of constraints
			// this is important for scheduling them later - a scheduler may want to retain the order
			// to give users a chance to arrange constraints manually
			List<Object> constraintOrder = new ArrayList<Object>();
			Map<String,PropertyConstraint> propertyConstraintMap = new HashMap<String,PropertyConstraint>();
			
			//unmarshalling xml query
			JAXBContext jc= JAXBContext.newInstance("nz.ac.massey.cs.guery.io.xml");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			Motif query = (Motif)unmarshaller.unmarshal(source);
			
			motif.setName(query.getName());
						
			for (Object o:query.getAnnotateOrSelectOrConstraint()) {
				if (o instanceof Annotate) {
					Annotate ann = (Annotate)o;
					String clazzName = ann.getClazz();
					try {
						Processor<V,E>  processor = (Processor<V,E> )Class.forName(clazzName).newInstance();
						graphProcessors.add(processor);
					}
					catch (Exception x) {
						throw new MotifReaderException("The processor class " + clazzName + " cannot be found or instantiated",x);
					}
				}
				if (o instanceof Select) {
					Select select = (Select)o;
					vertexRoles.add(select.getRole());
					collectConstraintDefs(select.getConstraint(),constraintDefs,constraintOrder);
				}
				else if (o instanceof ConnectedBy) {
					ConnectedBy connectedBy = (ConnectedBy)o;
					PathConstraint<V,E>  c = new PathConstraint<V,E> ();
					c.setMaxLength(connectedBy.getMaxLength());
					c.setMinLength(connectedBy.getMinLength());
					c.setComputeAll(connectedBy.isComputeAll());
					// resolve IDREFs here
					c.setSource(((Select)connectedBy.getFrom()).getRole());
					c.setTarget(((Select)connectedBy.getTo()).getRole());
					c.setRole(connectedBy.getRole());
					c.setNegated(false);
					pathConstraints.add(c);
					constraintOrder.add(c);
					pathRoles.add(connectedBy.getRole());
					posPathRoles.add(connectedBy.getRole());
					collectConstraintDefs(connectedBy.getConstraint(),constraintDefs,constraintOrder);
				}
				else if (o instanceof NotConnectedBy) {
					NotConnectedBy connectedBy = (NotConnectedBy)o;
					PathConstraint<V,E>  c = new PathConstraint<V,E> ();
					c.setMaxLength(connectedBy.getMaxLength());
					c.setMinLength(connectedBy.getMinLength());
					c.setComputeAll(false);
					// resolve IDREFs here
					c.setSource(((Select)connectedBy.getFrom()).getRole());
					c.setTarget(((Select)connectedBy.getTo()).getRole());
					c.setRole(connectedBy.getRole());
					c.setNegated(true);
					pathConstraints.add(c);
					constraintOrder.add(c);
					pathRoles.add(connectedBy.getRole());
					negPathRoles.add(connectedBy.getRole());
					collectConstraintDefs(connectedBy.getConstraint(),constraintDefs,constraintOrder);
				}
				else if (o instanceof String) {
					String constraintDef = (String)o;
					if (constraintDef!=null) constraintDef = constraintDef.trim();
					if (constraintDef!=null && constraintDef.length()>0) {
						constraintDefs.add(constraintDef);
						constraintOrder.add(constraintDef);
					}
				}
				else if (o instanceof GroupBy) {
					GroupBy groupBy = (GroupBy)o;
					for (String e:groupBy.getElement()) {
						GroupByClause<V> clause = new CompiledGroupByClause<V>(e);
						// TODO we could check roles here
						String role = clause.getRole();
						if (!vertexRoles.contains(role)) {
							throw new MotifReaderException("Group by clause " + e + " does contain an input that has not been declared as a role");
						}
						groupByClauses.add(clause);
					}
				}
			}
			
			// build expressions
			List<Constraint> constraints = new ArrayList<Constraint>();
			for (String constraintDef:constraintDefs) {
				PropertyConstraint constraint = buildConstraint(constraintDef,vertexRoles,pathRoles);
				constraints.add(constraint);
				propertyConstraintMap.put(constraintDef, constraint);
			}
			// associate property constraints with path constraints 
			for (PathConstraint<V,E>  pconstraint:pathConstraints) {
				addConstraints(pconstraint,constraints);
				constraints.add(pconstraint);
			}
			// build final constraint list, arrange constraints in order
			List<Constraint> list = new ArrayList<Constraint>();
			for (Object o:constraintOrder) {
				if (o instanceof Constraint) {
					list.add((Constraint)o);
				}
				else if (o instanceof String) { // this is a property def
					PropertyConstraint pc = propertyConstraintMap.get((String)o);
					if (constraints.contains(pc)) { // it might have been removed when it was associated with a path c.
						list.add(pc);
					}
				}
			}
			
			motif.setRoles(vertexRoles);
			motif.setPathRoles(posPathRoles);
			motif.setNegatedPathRoles(negPathRoles);
			motif.setGroupByClauses(groupByClauses);
			motif.setGraphProcessor(graphProcessors);
			motif.setConstraints(list);
			
			return motif;
		
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new MotifReaderException("exception reading motif from xml",e);
		}	
				
	}

	private void addConstraints(PathConstraint<V,E>  pathConstraint,	List<Constraint> constraints) {
		for (Iterator<Constraint> iter=constraints.iterator();iter.hasNext();) {
			Constraint p = iter.next();
			if (p instanceof PropertyConstraint) {
				PropertyConstraint pc = (PropertyConstraint)p;
				if (pc.getRoles().size()==1 && pc.getFirstRole().equals(pathConstraint.getRole())) {
					// attach property constraint to path constraint - this means that it will be evaluated
					// immediately if paths are explored
					pathConstraint.addConstraint(pc);
					// remove from constraint list to prevent double checking
					iter.remove();
				}
			}
		}		
	}

	private PropertyConstraint buildConstraint(String constraintDef,Collection<String> vRoles,Collection<String> pRoles) throws MotifReaderException {
		CompiledPropertyConstraint constraint = null;
		try {
			constraint = new CompiledPropertyConstraint(constraintDef);
		}
		catch (Exception x) {
			throw new MotifReaderException("Error compiling mvel expression "+constraintDef,x);
		}
		// check whether inputs are in roles
		for (String role:constraint.getRoles()) {
			if (!vRoles.contains(role) && !pRoles.contains(role)) {
				throw new MotifReaderException("The expression "+constraintDef + "contains the variable " + role + " which has not been declared as a role");
			}
		}
		
		return constraint;
	}


	private void collectConstraintDefs(List<String> defs,List<String> constraintDefs,List<Object> constraintOrder) {
		if (defs!=null) {
			for (String constraintDef:defs) {
				if (constraintDef!=null) constraintDef = constraintDef.trim();
				if (constraintDef!=null && constraintDef.length()>0) {
					constraintDefs.add(constraintDef);
					constraintOrder.add(constraintDef);
				}
			}
		}
	}

	
}
