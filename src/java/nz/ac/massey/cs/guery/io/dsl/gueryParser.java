// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g 2010-07-09 01:54:24
 
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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class gueryParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "COMMENT", "NL", "ESC_SEQ", "STRING", "HEX_DIGIT", "UNICODE_ESC", "OCTAL_ESC", "CLASS_NAME", "'motif'", "'select'", "','", "'['", "'*'", "']'", "'find all'", "'prepare with'", "'('", "'>'", "')'", "'connected by'", "'and'", "'not connected by'", "'where'", "'group by'"
    };
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int CLASS_NAME=13;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int UNICODE_ESC=11;
    public static final int T__20=20;
    public static final int OCTAL_ESC=12;
    public static final int HEX_DIGIT=10;
    public static final int INT=5;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int ESC_SEQ=8;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__14=14;
    public static final int NL=7;
    public static final int COMMENT=6;
    public static final int STRING=9;

    // delegates
    // delegators


        public gueryParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public gueryParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return gueryParser.tokenNames; }
    public String getGrammarFileName() { return "./grammar/guery.g"; }


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


    public static class query_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "query"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:1: query : ( declaration )? ( prepare )* ( select )+ ( connected_by | not_connected_by | where )* ( groupby )* ;
    public final gueryParser.query_return query() throws RecognitionException {
        gueryParser.query_return retval = new gueryParser.query_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        gueryParser.declaration_return declaration1 = null;

        gueryParser.prepare_return prepare2 = null;

        gueryParser.select_return select3 = null;

        gueryParser.connected_by_return connected_by4 = null;

        gueryParser.not_connected_by_return not_connected_by5 = null;

        gueryParser.where_return where6 = null;

        gueryParser.groupby_return groupby7 = null;



        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:7: ( ( declaration )? ( prepare )* ( select )+ ( connected_by | not_connected_by | where )* ( groupby )* )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:9: ( declaration )? ( prepare )* ( select )+ ( connected_by | not_connected_by | where )* ( groupby )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:9: ( declaration )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==14) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:9: declaration
                    {
                    pushFollow(FOLLOW_declaration_in_query427);
                    declaration1=declaration();

                    state._fsp--;

                    adaptor.addChild(root_0, declaration1.getTree());

                    }
                    break;

            }

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:22: ( prepare )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==21) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:22: prepare
            	    {
            	    pushFollow(FOLLOW_prepare_in_query430);
            	    prepare2=prepare();

            	    state._fsp--;

            	    adaptor.addChild(root_0, prepare2.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:31: ( select )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==15) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:31: select
            	    {
            	    pushFollow(FOLLOW_select_in_query433);
            	    select3=select();

            	    state._fsp--;

            	    adaptor.addChild(root_0, select3.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:39: ( connected_by | not_connected_by | where )*
            loop4:
            do {
                int alt4=4;
                switch ( input.LA(1) ) {
                case 25:
                    {
                    alt4=1;
                    }
                    break;
                case 27:
                    {
                    alt4=2;
                    }
                    break;
                case 28:
                    {
                    alt4=3;
                    }
                    break;

                }

                switch (alt4) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:40: connected_by
            	    {
            	    pushFollow(FOLLOW_connected_by_in_query437);
            	    connected_by4=connected_by();

            	    state._fsp--;

            	    adaptor.addChild(root_0, connected_by4.getTree());

            	    }
            	    break;
            	case 2 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:53: not_connected_by
            	    {
            	    pushFollow(FOLLOW_not_connected_by_in_query439);
            	    not_connected_by5=not_connected_by();

            	    state._fsp--;

            	    adaptor.addChild(root_0, not_connected_by5.getTree());

            	    }
            	    break;
            	case 3 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:70: where
            	    {
            	    pushFollow(FOLLOW_where_in_query441);
            	    where6=where();

            	    state._fsp--;

            	    adaptor.addChild(root_0, where6.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:78: ( groupby )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==29) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:229:78: groupby
            	    {
            	    pushFollow(FOLLOW_groupby_in_query445);
            	    groupby7=groupby();

            	    state._fsp--;

            	    adaptor.addChild(root_0, groupby7.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "query"

    public static class declaration_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declaration"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:231:1: declaration : 'motif' n= ID NL ;
    public final gueryParser.declaration_return declaration() throws RecognitionException {
        gueryParser.declaration_return retval = new gueryParser.declaration_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token n=null;
        Token string_literal8=null;
        Token NL9=null;

        CommonTree n_tree=null;
        CommonTree string_literal8_tree=null;
        CommonTree NL9_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:231:14: ( 'motif' n= ID NL )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:231:16: 'motif' n= ID NL
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal8=(Token)match(input,14,FOLLOW_14_in_declaration456); 
            string_literal8_tree = (CommonTree)adaptor.create(string_literal8);
            adaptor.addChild(root_0, string_literal8_tree);

            n=(Token)match(input,ID,FOLLOW_ID_in_declaration460); 
            n_tree = (CommonTree)adaptor.create(n);
            adaptor.addChild(root_0, n_tree);

            motif.setName(n.getText());
            NL9=(Token)match(input,NL,FOLLOW_NL_in_declaration463); 
            NL9_tree = (CommonTree)adaptor.create(NL9);
            adaptor.addChild(root_0, NL9_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "declaration"

    public static class select_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "select"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:233:1: select : 'select' vertex= ID ( ',' vertex= ID )* ( NL )? ;
    public final gueryParser.select_return select() throws RecognitionException {
        gueryParser.select_return retval = new gueryParser.select_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token vertex=null;
        Token string_literal10=null;
        Token char_literal11=null;
        Token NL12=null;

        CommonTree vertex_tree=null;
        CommonTree string_literal10_tree=null;
        CommonTree char_literal11_tree=null;
        CommonTree NL12_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:233:8: ( 'select' vertex= ID ( ',' vertex= ID )* ( NL )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:233:10: 'select' vertex= ID ( ',' vertex= ID )* ( NL )?
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal10=(Token)match(input,15,FOLLOW_15_in_select476); 
            string_literal10_tree = (CommonTree)adaptor.create(string_literal10);
            adaptor.addChild(root_0, string_literal10_tree);

            vertex=(Token)match(input,ID,FOLLOW_ID_in_select480); 
            vertex_tree = (CommonTree)adaptor.create(vertex);
            adaptor.addChild(root_0, vertex_tree);

            addVertexRole(vertex);
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:233:53: ( ',' vertex= ID )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==16) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:233:54: ',' vertex= ID
            	    {
            	    char_literal11=(Token)match(input,16,FOLLOW_16_in_select484); 
            	    char_literal11_tree = (CommonTree)adaptor.create(char_literal11);
            	    adaptor.addChild(root_0, char_literal11_tree);

            	    vertex=(Token)match(input,ID,FOLLOW_ID_in_select487); 
            	    vertex_tree = (CommonTree)adaptor.create(vertex);
            	    adaptor.addChild(root_0, vertex_tree);

            	    addVertexRole(vertex);

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:233:93: ( NL )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==NL) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:233:93: NL
                    {
                    NL12=(Token)match(input,NL,FOLLOW_NL_in_select492); 
                    NL12_tree = (CommonTree)adaptor.create(NL12);
                    adaptor.addChild(root_0, NL12_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "select"

    public static class path_length_constraint_return extends ParserRuleReturnScope {
        public PathLengthConstraint c;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "path_length_constraint"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:236:1: path_length_constraint returns [PathLengthConstraint c] : '[' min= INT ',' max= ( INT | '*' ) ']' ;
    public final gueryParser.path_length_constraint_return path_length_constraint() throws RecognitionException {
        gueryParser.path_length_constraint_return retval = new gueryParser.path_length_constraint_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token min=null;
        Token max=null;
        Token char_literal13=null;
        Token char_literal14=null;
        Token char_literal15=null;

        CommonTree min_tree=null;
        CommonTree max_tree=null;
        CommonTree char_literal13_tree=null;
        CommonTree char_literal14_tree=null;
        CommonTree char_literal15_tree=null;

         retval.c = new PathLengthConstraint(); 
        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:238:2: ( '[' min= INT ',' max= ( INT | '*' ) ']' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:238:4: '[' min= INT ',' max= ( INT | '*' ) ']'
            {
            root_0 = (CommonTree)adaptor.nil();

            char_literal13=(Token)match(input,17,FOLLOW_17_in_path_length_constraint517); 
            char_literal13_tree = (CommonTree)adaptor.create(char_literal13);
            adaptor.addChild(root_0, char_literal13_tree);

            min=(Token)match(input,INT,FOLLOW_INT_in_path_length_constraint521); 
            min_tree = (CommonTree)adaptor.create(min);
            adaptor.addChild(root_0, min_tree);

            retval.c.setMinLength(intValue(min));
            char_literal14=(Token)match(input,16,FOLLOW_16_in_path_length_constraint525); 
            char_literal14_tree = (CommonTree)adaptor.create(char_literal14);
            adaptor.addChild(root_0, char_literal14_tree);

            max=(Token)input.LT(1);
            if ( input.LA(1)==INT||input.LA(1)==18 ) {
                input.consume();
                adaptor.addChild(root_0, (CommonTree)adaptor.create(max));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            retval.c.setMaxLength(max.getText().equals("*")?-1:intValue(max));
            char_literal15=(Token)match(input,19,FOLLOW_19_in_path_length_constraint538); 
            char_literal15_tree = (CommonTree)adaptor.create(char_literal15);
            adaptor.addChild(root_0, char_literal15_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "path_length_constraint"

    public static class find_all_return extends ParserRuleReturnScope {
        public boolean value;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "find_all"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:240:1: find_all returns [boolean value] : (v= 'find all' )? ;
    public final gueryParser.find_all_return find_all() throws RecognitionException {
        gueryParser.find_all_return retval = new gueryParser.find_all_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token v=null;

        CommonTree v_tree=null;

        retval.value = false;
        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:242:2: ( (v= 'find all' )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:242:4: (v= 'find all' )?
            {
            root_0 = (CommonTree)adaptor.nil();

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:242:5: (v= 'find all' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==20) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:242:5: v= 'find all'
                    {
                    v=(Token)match(input,20,FOLLOW_20_in_find_all561); 
                    v_tree = (CommonTree)adaptor.create(v);
                    adaptor.addChild(root_0, v_tree);


                    }
                    break;

            }

            retval.value = v!=null;

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "find_all"

    public static class prepare_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "prepare"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:244:1: prepare : 'prepare with' scriptClass= ID NL ;
    public final gueryParser.prepare_return prepare() throws RecognitionException {
        gueryParser.prepare_return retval = new gueryParser.prepare_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token scriptClass=null;
        Token string_literal16=null;
        Token NL17=null;

        CommonTree scriptClass_tree=null;
        CommonTree string_literal16_tree=null;
        CommonTree NL17_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:244:9: ( 'prepare with' scriptClass= ID NL )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:244:11: 'prepare with' scriptClass= ID NL
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal16=(Token)match(input,21,FOLLOW_21_in_prepare572); 
            string_literal16_tree = (CommonTree)adaptor.create(string_literal16);
            adaptor.addChild(root_0, string_literal16_tree);

            scriptClass=(Token)match(input,ID,FOLLOW_ID_in_prepare576); 
            scriptClass_tree = (CommonTree)adaptor.create(scriptClass);
            adaptor.addChild(root_0, scriptClass_tree);

            addProcessor(scriptClass);
            NL17=(Token)match(input,NL,FOLLOW_NL_in_prepare580); 
            NL17_tree = (CommonTree)adaptor.create(NL17);
            adaptor.addChild(root_0, NL17_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "prepare"

    public static class connected_by_single_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "connected_by_single"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:246:1: connected_by_single : path= ID '(' from= ID '>' to= ID ')' (lengthConstraint= path_length_constraint )? all= find_all ;
    public final gueryParser.connected_by_single_return connected_by_single() throws RecognitionException {
        gueryParser.connected_by_single_return retval = new gueryParser.connected_by_single_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token path=null;
        Token from=null;
        Token to=null;
        Token char_literal18=null;
        Token char_literal19=null;
        Token char_literal20=null;
        gueryParser.path_length_constraint_return lengthConstraint = null;

        gueryParser.find_all_return all = null;


        CommonTree path_tree=null;
        CommonTree from_tree=null;
        CommonTree to_tree=null;
        CommonTree char_literal18_tree=null;
        CommonTree char_literal19_tree=null;
        CommonTree char_literal20_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:246:21: (path= ID '(' from= ID '>' to= ID ')' (lengthConstraint= path_length_constraint )? all= find_all )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:246:23: path= ID '(' from= ID '>' to= ID ')' (lengthConstraint= path_length_constraint )? all= find_all
            {
            root_0 = (CommonTree)adaptor.nil();

            path=(Token)match(input,ID,FOLLOW_ID_in_connected_by_single593); 
            path_tree = (CommonTree)adaptor.create(path);
            adaptor.addChild(root_0, path_tree);

            char_literal18=(Token)match(input,22,FOLLOW_22_in_connected_by_single595); 
            char_literal18_tree = (CommonTree)adaptor.create(char_literal18);
            adaptor.addChild(root_0, char_literal18_tree);

            from=(Token)match(input,ID,FOLLOW_ID_in_connected_by_single599); 
            from_tree = (CommonTree)adaptor.create(from);
            adaptor.addChild(root_0, from_tree);

            char_literal19=(Token)match(input,23,FOLLOW_23_in_connected_by_single601); 
            char_literal19_tree = (CommonTree)adaptor.create(char_literal19);
            adaptor.addChild(root_0, char_literal19_tree);

            to=(Token)match(input,ID,FOLLOW_ID_in_connected_by_single605); 
            to_tree = (CommonTree)adaptor.create(to);
            adaptor.addChild(root_0, to_tree);

            char_literal20=(Token)match(input,24,FOLLOW_24_in_connected_by_single607); 
            char_literal20_tree = (CommonTree)adaptor.create(char_literal20);
            adaptor.addChild(root_0, char_literal20_tree);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:246:74: (lengthConstraint= path_length_constraint )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==17) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:246:74: lengthConstraint= path_length_constraint
                    {
                    pushFollow(FOLLOW_path_length_constraint_in_connected_by_single612);
                    lengthConstraint=path_length_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, lengthConstraint.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_find_all_in_connected_by_single618);
            all=find_all();

            state._fsp--;

            adaptor.addChild(root_0, all.getTree());
            addPathRole(path,from,to,lengthConstraint==null?null:lengthConstraint.c,all.value,false);

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "connected_by_single"

    public static class not_connected_by_single_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "not_connected_by_single"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:247:1: not_connected_by_single : path= ID '(' from= ID '>' to= ID ')' (lengthConstraint= path_length_constraint )? all= find_all ;
    public final gueryParser.not_connected_by_single_return not_connected_by_single() throws RecognitionException {
        gueryParser.not_connected_by_single_return retval = new gueryParser.not_connected_by_single_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token path=null;
        Token from=null;
        Token to=null;
        Token char_literal21=null;
        Token char_literal22=null;
        Token char_literal23=null;
        gueryParser.path_length_constraint_return lengthConstraint = null;

        gueryParser.find_all_return all = null;


        CommonTree path_tree=null;
        CommonTree from_tree=null;
        CommonTree to_tree=null;
        CommonTree char_literal21_tree=null;
        CommonTree char_literal22_tree=null;
        CommonTree char_literal23_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:247:25: (path= ID '(' from= ID '>' to= ID ')' (lengthConstraint= path_length_constraint )? all= find_all )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:247:27: path= ID '(' from= ID '>' to= ID ')' (lengthConstraint= path_length_constraint )? all= find_all
            {
            root_0 = (CommonTree)adaptor.nil();

            path=(Token)match(input,ID,FOLLOW_ID_in_not_connected_by_single630); 
            path_tree = (CommonTree)adaptor.create(path);
            adaptor.addChild(root_0, path_tree);

            char_literal21=(Token)match(input,22,FOLLOW_22_in_not_connected_by_single632); 
            char_literal21_tree = (CommonTree)adaptor.create(char_literal21);
            adaptor.addChild(root_0, char_literal21_tree);

            from=(Token)match(input,ID,FOLLOW_ID_in_not_connected_by_single636); 
            from_tree = (CommonTree)adaptor.create(from);
            adaptor.addChild(root_0, from_tree);

            char_literal22=(Token)match(input,23,FOLLOW_23_in_not_connected_by_single638); 
            char_literal22_tree = (CommonTree)adaptor.create(char_literal22);
            adaptor.addChild(root_0, char_literal22_tree);

            to=(Token)match(input,ID,FOLLOW_ID_in_not_connected_by_single642); 
            to_tree = (CommonTree)adaptor.create(to);
            adaptor.addChild(root_0, to_tree);

            char_literal23=(Token)match(input,24,FOLLOW_24_in_not_connected_by_single644); 
            char_literal23_tree = (CommonTree)adaptor.create(char_literal23);
            adaptor.addChild(root_0, char_literal23_tree);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:247:78: (lengthConstraint= path_length_constraint )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==17) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:247:78: lengthConstraint= path_length_constraint
                    {
                    pushFollow(FOLLOW_path_length_constraint_in_not_connected_by_single649);
                    lengthConstraint=path_length_constraint();

                    state._fsp--;

                    adaptor.addChild(root_0, lengthConstraint.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_find_all_in_not_connected_by_single655);
            all=find_all();

            state._fsp--;

            adaptor.addChild(root_0, all.getTree());
            addPathRole(path,from,to,lengthConstraint==null?null:lengthConstraint.c,all.value,true);

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "not_connected_by_single"

    public static class connected_by_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "connected_by"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:249:1: connected_by : 'connected by' connected_by_single ( 'and' connected_by_single )* ( NL )? ;
    public final gueryParser.connected_by_return connected_by() throws RecognitionException {
        gueryParser.connected_by_return retval = new gueryParser.connected_by_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal24=null;
        Token string_literal26=null;
        Token NL28=null;
        gueryParser.connected_by_single_return connected_by_single25 = null;

        gueryParser.connected_by_single_return connected_by_single27 = null;


        CommonTree string_literal24_tree=null;
        CommonTree string_literal26_tree=null;
        CommonTree NL28_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:249:14: ( 'connected by' connected_by_single ( 'and' connected_by_single )* ( NL )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:249:16: 'connected by' connected_by_single ( 'and' connected_by_single )* ( NL )?
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal24=(Token)match(input,25,FOLLOW_25_in_connected_by669); 
            string_literal24_tree = (CommonTree)adaptor.create(string_literal24);
            adaptor.addChild(root_0, string_literal24_tree);

            pushFollow(FOLLOW_connected_by_single_in_connected_by671);
            connected_by_single25=connected_by_single();

            state._fsp--;

            adaptor.addChild(root_0, connected_by_single25.getTree());
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:249:51: ( 'and' connected_by_single )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==26) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:249:52: 'and' connected_by_single
            	    {
            	    string_literal26=(Token)match(input,26,FOLLOW_26_in_connected_by674); 
            	    string_literal26_tree = (CommonTree)adaptor.create(string_literal26);
            	    adaptor.addChild(root_0, string_literal26_tree);

            	    pushFollow(FOLLOW_connected_by_single_in_connected_by676);
            	    connected_by_single27=connected_by_single();

            	    state._fsp--;

            	    adaptor.addChild(root_0, connected_by_single27.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:249:80: ( NL )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==NL) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:249:80: NL
                    {
                    NL28=(Token)match(input,NL,FOLLOW_NL_in_connected_by680); 
                    NL28_tree = (CommonTree)adaptor.create(NL28);
                    adaptor.addChild(root_0, NL28_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "connected_by"

    public static class not_connected_by_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "not_connected_by"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:250:1: not_connected_by : 'not connected by' not_connected_by_single ( 'and' not_connected_by_single )* ( NL )? ;
    public final gueryParser.not_connected_by_return not_connected_by() throws RecognitionException {
        gueryParser.not_connected_by_return retval = new gueryParser.not_connected_by_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal29=null;
        Token string_literal31=null;
        Token NL33=null;
        gueryParser.not_connected_by_single_return not_connected_by_single30 = null;

        gueryParser.not_connected_by_single_return not_connected_by_single32 = null;


        CommonTree string_literal29_tree=null;
        CommonTree string_literal31_tree=null;
        CommonTree NL33_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:250:18: ( 'not connected by' not_connected_by_single ( 'and' not_connected_by_single )* ( NL )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:250:20: 'not connected by' not_connected_by_single ( 'and' not_connected_by_single )* ( NL )?
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal29=(Token)match(input,27,FOLLOW_27_in_not_connected_by688); 
            string_literal29_tree = (CommonTree)adaptor.create(string_literal29);
            adaptor.addChild(root_0, string_literal29_tree);

            pushFollow(FOLLOW_not_connected_by_single_in_not_connected_by690);
            not_connected_by_single30=not_connected_by_single();

            state._fsp--;

            adaptor.addChild(root_0, not_connected_by_single30.getTree());
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:250:63: ( 'and' not_connected_by_single )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==26) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:250:64: 'and' not_connected_by_single
            	    {
            	    string_literal31=(Token)match(input,26,FOLLOW_26_in_not_connected_by693); 
            	    string_literal31_tree = (CommonTree)adaptor.create(string_literal31);
            	    adaptor.addChild(root_0, string_literal31_tree);

            	    pushFollow(FOLLOW_not_connected_by_single_in_not_connected_by695);
            	    not_connected_by_single32=not_connected_by_single();

            	    state._fsp--;

            	    adaptor.addChild(root_0, not_connected_by_single32.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:250:96: ( NL )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==NL) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:250:96: NL
                    {
                    NL33=(Token)match(input,NL,FOLLOW_NL_in_not_connected_by699); 
                    NL33_tree = (CommonTree)adaptor.create(NL33);
                    adaptor.addChild(root_0, NL33_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "not_connected_by"

    public static class constraint_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constraint"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:252:1: constraint : expr= STRING ( NL )? ;
    public final gueryParser.constraint_return constraint() throws RecognitionException {
        gueryParser.constraint_return retval = new gueryParser.constraint_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token expr=null;
        Token NL34=null;

        CommonTree expr_tree=null;
        CommonTree NL34_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:252:12: (expr= STRING ( NL )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:252:14: expr= STRING ( NL )?
            {
            root_0 = (CommonTree)adaptor.nil();

            expr=(Token)match(input,STRING,FOLLOW_STRING_in_constraint710); 
            expr_tree = (CommonTree)adaptor.create(expr);
            adaptor.addChild(root_0, expr_tree);

            addConstraint(expr);
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:252:48: ( NL )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==NL) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:252:48: NL
                    {
                    NL34=(Token)match(input,NL,FOLLOW_NL_in_constraint713); 
                    NL34_tree = (CommonTree)adaptor.create(NL34);
                    adaptor.addChild(root_0, NL34_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "constraint"

    public static class where_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "where"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:254:1: where : 'where' constraint ( 'and' constraint )* ( NL )? ;
    public final gueryParser.where_return where() throws RecognitionException {
        gueryParser.where_return retval = new gueryParser.where_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal35=null;
        Token string_literal37=null;
        Token NL39=null;
        gueryParser.constraint_return constraint36 = null;

        gueryParser.constraint_return constraint38 = null;


        CommonTree string_literal35_tree=null;
        CommonTree string_literal37_tree=null;
        CommonTree NL39_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:254:7: ( 'where' constraint ( 'and' constraint )* ( NL )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:254:9: 'where' constraint ( 'and' constraint )* ( NL )?
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal35=(Token)match(input,28,FOLLOW_28_in_where727); 
            string_literal35_tree = (CommonTree)adaptor.create(string_literal35);
            adaptor.addChild(root_0, string_literal35_tree);

            pushFollow(FOLLOW_constraint_in_where729);
            constraint36=constraint();

            state._fsp--;

            adaptor.addChild(root_0, constraint36.getTree());
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:254:28: ( 'and' constraint )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==26) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:254:29: 'and' constraint
            	    {
            	    string_literal37=(Token)match(input,26,FOLLOW_26_in_where732); 
            	    string_literal37_tree = (CommonTree)adaptor.create(string_literal37);
            	    adaptor.addChild(root_0, string_literal37_tree);

            	    pushFollow(FOLLOW_constraint_in_where734);
            	    constraint38=constraint();

            	    state._fsp--;

            	    adaptor.addChild(root_0, constraint38.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:254:48: ( NL )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==NL) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:254:48: NL
                    {
                    NL39=(Token)match(input,NL,FOLLOW_NL_in_where738); 
                    NL39_tree = (CommonTree)adaptor.create(NL39);
                    adaptor.addChild(root_0, NL39_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "where"

    public static class aggregation_clause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "aggregation_clause"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:256:1: aggregation_clause : expr= STRING ( NL )? ;
    public final gueryParser.aggregation_clause_return aggregation_clause() throws RecognitionException {
        gueryParser.aggregation_clause_return retval = new gueryParser.aggregation_clause_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token expr=null;
        Token NL40=null;

        CommonTree expr_tree=null;
        CommonTree NL40_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:256:20: (expr= STRING ( NL )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:256:22: expr= STRING ( NL )?
            {
            root_0 = (CommonTree)adaptor.nil();

            expr=(Token)match(input,STRING,FOLLOW_STRING_in_aggregation_clause749); 
            expr_tree = (CommonTree)adaptor.create(expr);
            adaptor.addChild(root_0, expr_tree);

            addGroupBy(expr);
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:256:53: ( NL )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==NL) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:256:53: NL
                    {
                    NL40=(Token)match(input,NL,FOLLOW_NL_in_aggregation_clause752); 
                    NL40_tree = (CommonTree)adaptor.create(NL40);
                    adaptor.addChild(root_0, NL40_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "aggregation_clause"

    public static class groupby_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "groupby"
    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:258:1: groupby : 'group by' aggregation_clause ( 'and' aggregation_clause )* ( NL )? ;
    public final gueryParser.groupby_return groupby() throws RecognitionException {
        gueryParser.groupby_return retval = new gueryParser.groupby_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal41=null;
        Token string_literal43=null;
        Token NL45=null;
        gueryParser.aggregation_clause_return aggregation_clause42 = null;

        gueryParser.aggregation_clause_return aggregation_clause44 = null;


        CommonTree string_literal41_tree=null;
        CommonTree string_literal43_tree=null;
        CommonTree NL45_tree=null;

        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:258:9: ( 'group by' aggregation_clause ( 'and' aggregation_clause )* ( NL )? )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:258:11: 'group by' aggregation_clause ( 'and' aggregation_clause )* ( NL )?
            {
            root_0 = (CommonTree)adaptor.nil();

            string_literal41=(Token)match(input,29,FOLLOW_29_in_groupby764); 
            string_literal41_tree = (CommonTree)adaptor.create(string_literal41);
            adaptor.addChild(root_0, string_literal41_tree);

            pushFollow(FOLLOW_aggregation_clause_in_groupby766);
            aggregation_clause42=aggregation_clause();

            state._fsp--;

            adaptor.addChild(root_0, aggregation_clause42.getTree());
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:258:41: ( 'and' aggregation_clause )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==26) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:258:42: 'and' aggregation_clause
            	    {
            	    string_literal43=(Token)match(input,26,FOLLOW_26_in_groupby769); 
            	    string_literal43_tree = (CommonTree)adaptor.create(string_literal43);
            	    adaptor.addChild(root_0, string_literal43_tree);

            	    pushFollow(FOLLOW_aggregation_clause_in_groupby771);
            	    aggregation_clause44=aggregation_clause();

            	    state._fsp--;

            	    adaptor.addChild(root_0, aggregation_clause44.getTree());

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:258:69: ( NL )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==NL) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:258:69: NL
                    {
                    NL45=(Token)match(input,NL,FOLLOW_NL_in_groupby775); 
                    NL45_tree = (CommonTree)adaptor.create(NL45);
                    adaptor.addChild(root_0, NL45_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "groupby"

    // Delegated rules


 

    public static final BitSet FOLLOW_declaration_in_query427 = new BitSet(new long[]{0x0000000000208000L});
    public static final BitSet FOLLOW_prepare_in_query430 = new BitSet(new long[]{0x0000000000208000L});
    public static final BitSet FOLLOW_select_in_query433 = new BitSet(new long[]{0x000000003A208002L});
    public static final BitSet FOLLOW_connected_by_in_query437 = new BitSet(new long[]{0x000000003A000002L});
    public static final BitSet FOLLOW_not_connected_by_in_query439 = new BitSet(new long[]{0x000000003A000002L});
    public static final BitSet FOLLOW_where_in_query441 = new BitSet(new long[]{0x000000003A000002L});
    public static final BitSet FOLLOW_groupby_in_query445 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_14_in_declaration456 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_declaration460 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_NL_in_declaration463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_select476 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_select480 = new BitSet(new long[]{0x0000000000010082L});
    public static final BitSet FOLLOW_16_in_select484 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_select487 = new BitSet(new long[]{0x0000000000010082L});
    public static final BitSet FOLLOW_NL_in_select492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_path_length_constraint517 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_path_length_constraint521 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_path_length_constraint525 = new BitSet(new long[]{0x0000000000040020L});
    public static final BitSet FOLLOW_set_in_path_length_constraint529 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_path_length_constraint538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_find_all561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_prepare572 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_prepare576 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_NL_in_prepare580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_connected_by_single593 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_connected_by_single595 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_connected_by_single599 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_connected_by_single601 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_connected_by_single605 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_connected_by_single607 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_path_length_constraint_in_connected_by_single612 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_find_all_in_connected_by_single618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_not_connected_by_single630 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_not_connected_by_single632 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_not_connected_by_single636 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_not_connected_by_single638 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_not_connected_by_single642 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_not_connected_by_single644 = new BitSet(new long[]{0x0000000000120000L});
    public static final BitSet FOLLOW_path_length_constraint_in_not_connected_by_single649 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_find_all_in_not_connected_by_single655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_connected_by669 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_connected_by_single_in_connected_by671 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_26_in_connected_by674 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_connected_by_single_in_connected_by676 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_NL_in_connected_by680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_not_connected_by688 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_not_connected_by_single_in_not_connected_by690 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_26_in_not_connected_by693 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_not_connected_by_single_in_not_connected_by695 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_NL_in_not_connected_by699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constraint710 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_NL_in_constraint713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_where727 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_constraint_in_where729 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_26_in_where732 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_constraint_in_where734 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_NL_in_where738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_aggregation_clause749 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_NL_in_aggregation_clause752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_groupby764 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_aggregation_clause_in_groupby766 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_26_in_groupby769 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_aggregation_clause_in_groupby771 = new BitSet(new long[]{0x0000000004000082L});
    public static final BitSet FOLLOW_NL_in_groupby775 = new BitSet(new long[]{0x0000000000000002L});

}