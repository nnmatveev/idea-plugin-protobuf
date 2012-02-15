package protobuf.lang;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.TokenSet;

/**
 * @author Nikolay Matveev
 * Date: Mar 5, 2010
 */
public interface PbTokenTypes {

    IElementType IDENTIFIER = new PbElementType("IDENTIFIER");
    TokenSet IDENTIFIERS = TokenSet.create(IDENTIFIER);

    IElementType SEMICOLON = new PbElementType("SEMICOLON");
    IElementType DOT = new PbElementType("DOT");
    IElementType COMMA = new PbElementType("COMMA");
    IElementType EQUAL = new PbElementType("EQUAL");
    IElementType MINUS = new PbElementType("MINUS");

    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    TokenSet BAD_CHARACTERS = TokenSet.create(BAD_CHARACTER);

    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    TokenSet WHITE_SPACES = TokenSet.create(WHITE_SPACE);

    IElementType LINE_COMMENT = new PbElementType("LINE_COMMENT");
    IElementType C_STYLE_COMMENT = new PbElementType("C_STYLE_COMMENT");
    IElementType WRONG_C_STYLE_COMMENT = new PbElementType("WRONG_C_STYLE_COMMENT");
    TokenSet COMMENTS = TokenSet.create(LINE_COMMENT,C_STYLE_COMMENT,WRONG_C_STYLE_COMMENT);        

    IElementType STRING_LITERAL = new PbElementType("STRING_LITERAL");
    IElementType WRONG_STRING_LITERAL = new PbElementType("WRONG_STRING_LITERAL");
    TokenSet STRING_LITERALS = TokenSet.create(STRING_LITERAL,WRONG_STRING_LITERAL);
    TokenSet WRONG_STRING_LITERALS = TokenSet.create(WRONG_STRING_LITERAL);

    //braces
    IElementType OPEN_BLOCK = new PbElementType("OPEN_BLOCK");
    IElementType CLOSE_BLOCK = new PbElementType("CLOSE_BLOCK");
    IElementType OPEN_BRACE = new PbElementType("OPEN_BRACE");
    IElementType CLOSE_BRACE = new PbElementType("CLOSE_BRACE");
    IElementType OPEN_PARENTHESIS = new PbElementType("OPEN_PARENTHESIS");
    IElementType CLOSE_PARENTHESIS = new PbElementType("CLOSE_PARENTHESIS");
    TokenSet BRACES = TokenSet.create(OPEN_BLOCK,CLOSE_BLOCK,OPEN_BRACE,CLOSE_BRACE, OPEN_PARENTHESIS, CLOSE_PARENTHESIS);

    //Numbers
    IElementType NUM_INT = new PbElementType("NUM_INT");
    IElementType NUM_DOUBLE = new PbElementType("NUM_DOUBLE");
    TokenSet NUMBERS = TokenSet.create(NUM_INT,NUM_DOUBLE);


//keywords    

    //statements
    IElementType IMPORT = new PbElementType("IMPORT");
    IElementType PACKAGE = new PbElementType("PACKAGE");
    IElementType MESSAGE = new PbElementType("MESSAGE");
    IElementType EXTEND = new PbElementType("EXTEND");
    IElementType SERVICE = new PbElementType("SERVICE");
    IElementType OPTION = new PbElementType("OPTION");
    IElementType ENUM = new PbElementType("ENUM");
    TokenSet DECLARATIONS = TokenSet.create(IMPORT,PACKAGE,MESSAGE,EXTEND,SERVICE,OPTION,ENUM); //keywords


    IElementType RPC = new PbElementType("RPC");
    IElementType RETURNS = new PbElementType("RETURNS");
    IElementType EXTENSIONS = new PbElementType("EXTENSIONS");
    //--

    //special words
    IElementType TO = new PbElementType("TO");
    IElementType MAX = new PbElementType("MAX");

    //boolean values
    IElementType TRUE = new PbElementType("TRUE");
    IElementType FALSE = new PbElementType("FALSE");
    TokenSet BOOL_VALUES = TokenSet.create(TRUE,FALSE); //keywords

    //spec field label
    IElementType REQUIRED = new PbElementType("REQUIRED");
    IElementType OPTIONAL = new PbElementType("OPTIONAL");
    IElementType REPEATED = new PbElementType("REPEATED");
    TokenSet FIELD_LABELS = TokenSet.create(REQUIRED,OPTIONAL,REPEATED); //keywords


    IElementType GROUP = new PbElementType("GROUP");  TokenSet GROUP_SET = TokenSet.create(GROUP); //hack for lookAhead
    
    //Built-in types
    IElementType DOUBLE = new PbElementType("DOUBLE");
    IElementType FLOAT = new PbElementType("FLOAT");
    IElementType INT32 = new PbElementType("INT32");
    IElementType INT64 = new PbElementType("INT64");
    IElementType UINT32 = new PbElementType("UINT32");
    IElementType UINT64 = new PbElementType("UINT64");
    IElementType SINT32 = new PbElementType("SINT32");
    IElementType SINT64 = new PbElementType("SINT64");
    IElementType FIXED32 = new PbElementType("FIXED32");
    IElementType FIXED64 = new PbElementType("FIXED64");
    IElementType SFIXED32 = new PbElementType("SFIXED32");
    IElementType SFIXED64 = new PbElementType("SFIXED4");
    IElementType BOOL = new PbElementType("BOOL");
    IElementType STRING = new PbElementType("STRING");
    IElementType BYTES = new PbElementType("BYTES");
    TokenSet BUILT_IN_TYPES = TokenSet.create(DOUBLE,FLOAT,INT32,INT64,
                                                UINT32,UINT64,SINT32,SINT64,
                                                FIXED32,FIXED64,SFIXED32,
                                                SFIXED64,BOOL,STRING,BYTES);  //keywords

    TokenSet OTHER_KEYWORDS = TokenSet.create(RPC,RETURNS,GROUP,EXTENSIONS,TO,MAX);//keywords

    //Options
    //--File settings
    /*IElementType OPTIMIZE_FOR = new PbElementType("optimize for");
    IElementType JAVA_PACKAGE = new PbElementType("java_package");
    IElementType JAVA_OUTER_CLASSNAME = new PbElementType("java_outer_classname");
    IElementType JAVA_MULTIPLE_FILES = new PbElementType("java_multiple_files");
    IElementType CC_ABSTRACT_SERVICES = new PbElementType("cc_abstract_services");
    IElementType JAVA_ABSTRACT_SERVICES = new PbElementType("java_abstract_services");
    IElementType PY_ABSTRACT_SERVICES = new PbElementType("py_abstract_services");
    TokenSet FILE_OPTIONS = TokenSet.create(OPTIMIZE_FOR,JAVA_PACKAGE,JAVA_OUTER_CLASSNAME,
                                            JAVA_MULTIPLE_FILES,CC_ABSTRACT_SERVICES,
                                            JAVA_ABSTRACT_SERVICES,PY_ABSTRACT_SERVICES); //keyword

    //--Message settings
    IElementType MESSAGE_SET_WIRE_FORMAT = new PbElementType("message set wire format");
    TokenSet MESSAGE_OPTIONS = TokenSet.create(MESSAGE_SET_WIRE_FORMAT); //keyword
    */

    //--Field settings
    //IElementType DEPRECATED = new PbElementType("deprecated");
    //IElementType PACKED = new PbElementType("packed");
    //IElementType DEFAULT = new PbElementType("default");
    //TokenSet FIELD_OPTIONS = TokenSet.create(DEPRECATED,PACKED,DEFAULT);  //keyword

    TokenSet KEYWORDS = TokenSet.orSet(BUILT_IN_TYPES,
                                        /*FIELD_OPTIONS,*/
                                        FIELD_LABELS,
                                        BOOL_VALUES,
            DECLARATIONS,
                                        OTHER_KEYWORDS
                                        );
    TokenSet IK = TokenSet.orSet(IDENTIFIERS,KEYWORDS);
    TokenSet CONSTANT_LITERALS = TokenSet.orSet(STRING_LITERALS,NUMBERS,BOOL_VALUES,IK); //filed option value
}
