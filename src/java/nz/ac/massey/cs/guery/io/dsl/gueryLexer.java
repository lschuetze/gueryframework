// $ANTLR 3.2 Sep 23, 2009 12:02:23 /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g 2010-07-09 01:54:24
 package nz.ac.massey.cs.guery.io.dsl; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class gueryLexer extends Lexer {
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int CLASS_NAME=13;
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
    public static final int T__16=16;
    public static final int ESC_SEQ=8;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__14=14;
    public static final int NL=7;
    public static final int COMMENT=6;
    public static final int STRING=9;

    	@Override
    	public void reportError(RecognitionException e) {
      		// Thrower.sneakyThrow(e);
      	}


    // delegates
    // delegators

    public gueryLexer() {;} 
    public gueryLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public gueryLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "./grammar/guery.g"; }

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:11:7: ( 'motif' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:11:9: 'motif'
            {
            match("motif"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:12:7: ( 'select' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:12:9: 'select'
            {
            match("select"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:13:7: ( ',' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:13:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:14:7: ( '[' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:14:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:15:7: ( '*' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:15:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:16:7: ( ']' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:16:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:17:7: ( 'find all' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:17:9: 'find all'
            {
            match("find all"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:18:7: ( 'prepare with' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:18:9: 'prepare with'
            {
            match("prepare with"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:19:7: ( '(' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:19:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:20:7: ( '>' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:20:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:21:7: ( ')' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:21:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:22:7: ( 'connected by' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:22:9: 'connected by'
            {
            match("connected by"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:23:7: ( 'and' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:23:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:24:7: ( 'not connected by' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:24:9: 'not connected by'
            {
            match("not connected by"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:25:7: ( 'where' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:25:9: 'where'
            {
            match("where"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:26:7: ( 'group by' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:26:9: 'group by'
            {
            match("group by"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:193:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '.' )* )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:193:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '.' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:193:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '.' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='.'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:
            	    {
            	    if ( input.LA(1)=='.'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:194:5: ( ( '0' .. '9' )+ )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:194:7: ( '0' .. '9' )+
            {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:194:7: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:194:7: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:196:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='/') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='/') ) {
                    alt6=1;
                }
                else if ( (LA6_1=='*') ) {
                    alt6=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:196:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match("//"); 

                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:196:14: (~ ( '\\n' | '\\r' ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:196:14: ~ ( '\\n' | '\\r' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:196:28: ( '\\r' )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='\r') ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:196:28: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 
                    _channel=HIDDEN;

                    }
                    break;
                case 2 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:197:9: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 

                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:197:14: ( options {greedy=false; } : . )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0=='*') ) {
                            int LA5_1 = input.LA(2);

                            if ( (LA5_1=='/') ) {
                                alt5=2;
                            }
                            else if ( ((LA5_1>='\u0000' && LA5_1<='.')||(LA5_1>='0' && LA5_1<='\uFFFF')) ) {
                                alt5=1;
                            }


                        }
                        else if ( ((LA5_0>='\u0000' && LA5_0<=')')||(LA5_0>='+' && LA5_0<='\uFFFF')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:197:42: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);

                    match("*/"); 

                    _channel=HIDDEN;

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "NL"
    public final void mNL() throws RecognitionException {
        try {
            int _type = NL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:200:4: ( ( '\\r' )? '\\n' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:200:6: ( '\\r' )? '\\n'
            {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:200:6: ( '\\r' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\r') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:200:6: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NL"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:201:8: ( '\"' ( ESC_SEQ | ~ ( '\\\\' | '\"' ) )* '\"' )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:201:11: '\"' ( ESC_SEQ | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:201:15: ( ESC_SEQ | ~ ( '\\\\' | '\"' ) )*
            loop8:
            do {
                int alt8=3;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='\\') ) {
                    alt8=1;
                }
                else if ( ((LA8_0>='\u0000' && LA8_0<='!')||(LA8_0>='#' && LA8_0<='[')||(LA8_0>=']' && LA8_0<='\uFFFF')) ) {
                    alt8=2;
                }


                switch (alt8) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:201:17: ESC_SEQ
            	    {
            	    mESC_SEQ(); 

            	    }
            	    break;
            	case 2 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:201:27: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:204:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:204:13: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "ESC_SEQ"
    public final void mESC_SEQ() throws RecognitionException {
        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:208:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt9=3;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt9=1;
                    }
                    break;
                case 'u':
                    {
                    alt9=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt9=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:208:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:209:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 

                    }
                    break;
                case 3 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:210:9: OCTAL_ESC
                    {
                    mOCTAL_ESC(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "ESC_SEQ"

    // $ANTLR start "OCTAL_ESC"
    public final void mOCTAL_ESC() throws RecognitionException {
        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt10=3;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\\') ) {
                int LA10_1 = input.LA(2);

                if ( ((LA10_1>='0' && LA10_1<='3')) ) {
                    int LA10_2 = input.LA(3);

                    if ( ((LA10_2>='0' && LA10_2<='7')) ) {
                        int LA10_4 = input.LA(4);

                        if ( ((LA10_4>='0' && LA10_4<='7')) ) {
                            alt10=1;
                        }
                        else {
                            alt10=2;}
                    }
                    else {
                        alt10=3;}
                }
                else if ( ((LA10_1>='4' && LA10_1<='7')) ) {
                    int LA10_3 = input.LA(3);

                    if ( ((LA10_3>='0' && LA10_3<='7')) ) {
                        alt10=2;
                    }
                    else {
                        alt10=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:14: ( '0' .. '3' )
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:25: ( '0' .. '7' )
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:36: ( '0' .. '7' )
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:215:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:216:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:216:14: ( '0' .. '7' )
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:216:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:216:25: ( '0' .. '7' )
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:216:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:217:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:217:14: ( '0' .. '7' )
                    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:217:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OCTAL_ESC"

    // $ANTLR start "UNICODE_ESC"
    public final void mUNICODE_ESC() throws RecognitionException {
        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:222:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:222:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 
            match('u'); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 
            mHEX_DIGIT(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UNICODE_ESC"

    // $ANTLR start "CLASS_NAME"
    public final void mCLASS_NAME() throws RecognitionException {
        try {
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:227:2: ( ID ( . ID )* )
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:227:5: ID ( . ID )*
            {
            mID(); 
            // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:227:8: ( . ID )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\u0000' && LA11_0<='\uFFFF')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:227:9: . ID
            	    {
            	    matchAny(); 
            	    mID(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "CLASS_NAME"

    public void mTokens() throws RecognitionException {
        // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:8: ( T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | ID | INT | COMMENT | NL | STRING )
        int alt12=21;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:10: T__14
                {
                mT__14(); 

                }
                break;
            case 2 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:16: T__15
                {
                mT__15(); 

                }
                break;
            case 3 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:22: T__16
                {
                mT__16(); 

                }
                break;
            case 4 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:28: T__17
                {
                mT__17(); 

                }
                break;
            case 5 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:34: T__18
                {
                mT__18(); 

                }
                break;
            case 6 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:40: T__19
                {
                mT__19(); 

                }
                break;
            case 7 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:46: T__20
                {
                mT__20(); 

                }
                break;
            case 8 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:52: T__21
                {
                mT__21(); 

                }
                break;
            case 9 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:58: T__22
                {
                mT__22(); 

                }
                break;
            case 10 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:64: T__23
                {
                mT__23(); 

                }
                break;
            case 11 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:70: T__24
                {
                mT__24(); 

                }
                break;
            case 12 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:76: T__25
                {
                mT__25(); 

                }
                break;
            case 13 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:82: T__26
                {
                mT__26(); 

                }
                break;
            case 14 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:88: T__27
                {
                mT__27(); 

                }
                break;
            case 15 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:94: T__28
                {
                mT__28(); 

                }
                break;
            case 16 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:100: T__29
                {
                mT__29(); 

                }
                break;
            case 17 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:106: ID
                {
                mID(); 

                }
                break;
            case 18 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:109: INT
                {
                mINT(); 

                }
                break;
            case 19 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:113: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 20 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:121: NL
                {
                mNL(); 

                }
                break;
            case 21 :
                // /Users/jbdietri/development/gql4jung/workspace/guery/grammar/guery.g:1:124: STRING
                {
                mSTRING(); 

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA12_eotS =
        "\1\uffff\2\21\4\uffff\2\21\3\uffff\5\21\5\uffff\16\21\1\55\10\21"+
        "\2\uffff\2\21\1\70\1\21\1\uffff\2\21\1\74\1\21\1\uffff\1\76\2\21"+
        "\3\uffff\2\21\1\uffff\2\21\1\uffff";
    static final String DFA12_eofS =
        "\105\uffff";
    static final String DFA12_minS =
        "\1\12\1\157\1\145\4\uffff\1\151\1\162\3\uffff\1\157\1\156\1\157"+
        "\1\150\1\162\5\uffff\1\164\1\154\1\156\1\145\1\156\1\144\1\164\1"+
        "\145\1\157\1\151\1\145\1\144\1\160\1\156\1\56\1\40\1\162\1\165\1"+
        "\146\1\143\1\40\1\141\1\145\2\uffff\1\145\1\160\1\56\1\164\1\uffff"+
        "\1\162\1\143\1\56\1\40\1\uffff\1\56\1\145\1\164\3\uffff\1\40\1\145"+
        "\1\uffff\1\144\1\40\1\uffff";
    static final String DFA12_maxS =
        "\1\172\1\157\1\145\4\uffff\1\151\1\162\3\uffff\1\157\1\156\1\157"+
        "\1\150\1\162\5\uffff\1\164\1\154\1\156\1\145\1\156\1\144\1\164\1"+
        "\145\1\157\1\151\1\145\1\144\1\160\1\156\1\172\1\40\1\162\1\165"+
        "\1\146\1\143\1\40\1\141\1\145\2\uffff\1\145\1\160\1\172\1\164\1"+
        "\uffff\1\162\1\143\1\172\1\40\1\uffff\1\172\1\145\1\164\3\uffff"+
        "\1\40\1\145\1\uffff\1\144\1\40\1\uffff";
    static final String DFA12_acceptS =
        "\3\uffff\1\3\1\4\1\5\1\6\2\uffff\1\11\1\12\1\13\5\uffff\1\21\1\22"+
        "\1\23\1\24\1\25\27\uffff\1\15\1\16\4\uffff\1\7\4\uffff\1\1\3\uffff"+
        "\1\17\1\20\1\2\2\uffff\1\10\2\uffff\1\14";
    static final String DFA12_specialS =
        "\105\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\24\2\uffff\1\24\24\uffff\1\25\5\uffff\1\11\1\13\1\5\1\uffff"+
            "\1\3\2\uffff\1\23\12\22\4\uffff\1\12\2\uffff\32\21\1\4\1\uffff"+
            "\1\6\1\uffff\1\21\1\uffff\1\15\1\21\1\14\2\21\1\7\1\20\5\21"+
            "\1\1\1\16\1\21\1\10\2\21\1\2\3\21\1\17\3\21",
            "\1\26",
            "\1\27",
            "",
            "",
            "",
            "",
            "\1\30",
            "\1\31",
            "",
            "",
            "",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "",
            "",
            "",
            "",
            "",
            "\1\37",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\21\1\uffff\12\21\7\uffff\32\21\4\uffff\1\21\1\uffff\32\21",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "",
            "",
            "\1\66",
            "\1\67",
            "\1\21\1\uffff\12\21\7\uffff\32\21\4\uffff\1\21\1\uffff\32\21",
            "\1\71",
            "",
            "\1\72",
            "\1\73",
            "\1\21\1\uffff\12\21\7\uffff\32\21\4\uffff\1\21\1\uffff\32\21",
            "\1\75",
            "",
            "\1\21\1\uffff\12\21\7\uffff\32\21\4\uffff\1\21\1\uffff\32\21",
            "\1\77",
            "\1\100",
            "",
            "",
            "",
            "\1\101",
            "\1\102",
            "",
            "\1\103",
            "\1\104",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | ID | INT | COMMENT | NL | STRING );";
        }
    }
 

}