grammar guery;


options {
output=AST; ASTLabelType=CommonTree;

}

@parser::header{ 
	package nz.ac.massey.cs.guery.io.dsl; 
	import nz.ac.massey.cs.guery.*;
	import java.util.Map;
	import java.util.HashMap;
	import java.util.List;
	import java.util.ArrayList;
	import java.util.Collection;
	import nz.ac.massey.cs.guery.mvel.*;
	import com.google.common.base.Predicate;
	import com.google.common.collect.Collections2;
}
@parser::members {
	private DefaultMotif motif = new DefaultMotif();
	private List<String> constraintDefs = new ArrayList<String>();
	private Map<String, Class> typeInfo = new HashMap<String,Class>();
	private ClassLoader processorClassLoader = this.getClass().getClassLoader();
	
    	public ClassLoader getProcessorClassLoader() {
		return processorClassLoader;
	}
	public void setProcessorClassLoader(ClassLoader processorClassLoader) {
		this.processorClassLoader = processorClassLoader;
	}
		
	
	public Motif getMotif () {
		return motif;
	}
       	private void exception(String message,Token token) {
       		StringBuffer b = new StringBuffer();
       		b.append(message);
       		b.append(" Position: row ");
       		b.append(token.getLine());
       		b.append(", column ");
       		b.append(token.getCharPositionInLine());
       		b.append(".");
       		exception(b.toString());
       	}
       	private void exception(String message,Token token,Exception cause) {
       		StringBuffer b = new StringBuffer();
       		b.append(message);
       		b.append(" Position: row ");
       		b.append(token.getLine());
       		b.append(", column ");
       		b.append(token.getCharPositionInLine());
       		b.append(".");
       		exception(b.toString(),cause);
       	}
       	
       	private void exception(String message) {
       		throw new SemanticException(message);
       	}
       	private void exception(String message,Exception cause) {
       		throw new SemanticException(message,cause);
       	}
    	private int intValue(Token t) {
    		return Integer.parseInt(t.getText());
    	}
	private void addVertexRole(Token roleT) {
		String role = roleT.getText();
		motif.getRoles().add(role);
		typeInfo.put(role,Vertex.class);
	}
	private void addConstraint(Token constraintT) {
    		PropertyConstraint constraint = buildConstraint(constraintT);
    			
		// try to attach constraint directly to path constraint 
		Collection<PathConstraint> pathConstraints = Collections2.filter(motif.getConstraints(),new Predicate(){@Override public boolean apply(Object arg) {return arg instanceof PathConstraint;}});
		for (PathConstraint pc:pathConstraints) {
			if (constraint.getRoles().size()==1 && constraint.getFirstRole().equals(pc.getRole())) {
				// attach property constraint to path constraint - this means that it will be evaluated
				// immediately if paths are explored
				pc.addConstraint(constraint);
				return;
			}
		}
		// else, add constraint to list of "global" constraints
		motif.getConstraints().add(constraint);
			
	}
	
    	private void addGroupBy(Token groupByT) {
    		String def = removeDoubleQuotes(groupByT.getText());
    		CompiledGroupByClause clause = new CompiledGroupByClause(def);
		String role = clause.getRole();
		if (!motif.getRoles().contains(role)) {
			exception("Group by clause " + def + " does contain an input that has not been declared as a role ",groupByT);
		}
		motif.getGroupByClauses().add(clause);
    	}
    	
       	private void addProcessor(Token classNameT) {
       		String className = classNameT.getText();
       		Class clazz = null;
       		try {
			clazz = processorClassLoader.loadClass(className);
		} 
		catch (ClassNotFoundException e) {
			exception("Processor class " + className + " cannot be loaded",e);
		}
       		if (!Processor.class.isAssignableFrom(clazz)) {
       			exception("Class " + className + " does not implement " + Processor.class.getName());
       		}
       		try {
			Processor processor = (Processor) clazz.newInstance();
			motif.getGraphProcessors().add(processor);
		} catch (Exception e) {
				exception("Processor class " + className + " cannot be instantiated",e);
		} 
       	}
	
	private void addPathRole(Token roleT,Token fromT,Token toT,PathLengthConstraint c,boolean findAll,boolean negated) {
		String source = fromT.getText();
		String target = toT.getText();
		String role = roleT.getText();
		if (motif.getPathRoles().contains(role)) {
			exception("Path role "+role+" is already defined",roleT);	
		}
		if (!motif.getRoles().contains(source)) {
			exception("Source vertex role not defined for path role "+role,fromT);
		}
		if (!motif.getRoles().contains(target)) {
			exception("Target vertex role not defined for path role "+role,toT);
		}
		if (negated) motif.getNegatedPathRoles().add(role);
		else motif.getPathRoles().add(role);
		
		typeInfo.put(role,Edge.class);
		
		// build path constraint
		PathConstraint constraint = new PathConstraint();
		constraint.setRole(role);
		constraint.setSource(source);
		constraint.setTarget(target);
		constraint.setNegated(false);
		constraint.setMinLength(c==null?1:c.getMinLength());
		constraint.setMaxLength(c==null?-1:c.getMaxLength());
		constraint.setComputeAll(findAll);
		constraint.setNegated(negated);
		
		motif.getConstraints().add(constraint);
	}
	
	private String removeDoubleQuotes(String s) {
    		if (s.charAt(0)=='\"' && s.charAt(s.length()-1)=='\"') {
    			return s.substring(1,s.length()-1);
    		} 
    		else {
    			return s;
    		}
    	}
	
	
	private PropertyConstraint buildConstraint(Token constraintDefT) {
		String constraintDef = removeDoubleQuotes(constraintDefT.getText());
		CompiledPropertyConstraint constraint = null;
		try {
			constraint = new CompiledPropertyConstraint(constraintDef,typeInfo);
		}
		catch (Exception x) {
			exception("Error compiling mvel expression "+constraintDef,constraintDefT,x);
		}
		// check whether inputs are in roles
		for (String role:constraint.getRoles()) {
			if (!motif.getRoles().contains(role) && !motif.getPathRoles().contains(role) && !motif.getNegatedPathRoles().contains(role)) {
				exception("The expression "+constraintDef + "contains the variable " + role + " which has not yet been declared as a role",constraintDefT);
			}
		}
		
		return constraint;
	}
}
@lexer::header{ package nz.ac.massey.cs.guery.io.dsl; }

@lexer::members {
	@Override
	public void reportError(RecognitionException e) {
  		// Thrower.sneakyThrow(e);
  	}
}



ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'.')*;
INT :	'0'..'9'+;
COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

NL : '\r'? '\n' ;
STRING :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"';

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
    
fragment
CLASS_NAME 
	:	 ID (.ID)*;    

query : declaration? prepare* select+ (connected_by|not_connected_by|where)* groupby*; 

declaration 	: 'motif' n=ID{motif.setName(n.getText());} NL;	 	  

select	:	'select' vertex=ID{addVertexRole(vertex);} (','vertex=ID{addVertexRole(vertex);})* NL?;  
	

path_length_constraint returns [PathLengthConstraint c] 
	@init { $c = new PathLengthConstraint(); }
	: '[' min=INT {$c.setMinLength(intValue(min));} ',' max=(INT | '*'){$c.setMaxLength(max.getText().equals("*")?-1:intValue(max));} ']'; 
	
find_all returns [boolean value]
	@init {$value = false;}
	: v='find all'?{$value = v!=null;};
	
prepare : 'prepare with' scriptClass=ID {addProcessor(scriptClass);} NL; 		

connected_by_single : path=ID '(' from=ID '>' to=ID ')'  lengthConstraint=path_length_constraint?  all=find_all {addPathRole(path,from,to,lengthConstraint==null?null:lengthConstraint.c,all.value,false);}; 
not_connected_by_single : path=ID '(' from=ID '>' to=ID ')'  lengthConstraint=path_length_constraint?  all=find_all {addPathRole(path,from,to,lengthConstraint==null?null:lengthConstraint.c,all.value,true);}; 	  

connected_by : 'connected by' connected_by_single ('and' connected_by_single)* NL?;
not_connected_by : 'not connected by' not_connected_by_single ('and' not_connected_by_single)* NL?;

constraint : expr=STRING{addConstraint(expr);} NL?;	  	 

where : 'where' constraint ('and' constraint)* NL?;

aggregation_clause : expr=STRING{addGroupBy(expr);} NL?;
 	 
groupby : 'group by' aggregation_clause ('and' aggregation_clause)* NL?;
	


    
    
