package protobuf.lang;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.TokenSet;
import protobuf.lang.ProtobufElementType;

/**
 * author: Nikolay Matveev
 * Date: Mar 5, 2010
 */
public interface ProtobufTokenTypes {     

    IElementType IDENTIFIER = new ProtobufElementType("IDENTIFIER");
    TokenSet IDENTIFIERS = TokenSet.create(IDENTIFIER);

    IElementType SEMICOLON = new ProtobufElementType("SEMICOLON");
    IElementType DOT = new ProtobufElementType("DOT");
    IElementType COMMA = new ProtobufElementType("COMMA");
    
    IElementType EQUAL = new ProtobufElementType("EQUAL");

    IElementType MINUS = new ProtobufElementType("MINUS");

    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    TokenSet BAD_CHARACTERS = TokenSet.create(BAD_CHARACTER);

    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    TokenSet WHITE_SPACES = TokenSet.create(WHITE_SPACE);

    IElementType LINE_COMMENT = new ProtobufElementType("LINE_COMMENT");
    IElementType C_STYLE_COMMENT = new ProtobufElementType("C_STYLE_COMMENT");
    IElementType WRONG_C_STYLE_COMMENT = new ProtobufElementType("WRONG_C_STYLE_COMMENT");
    TokenSet COMMENTS = TokenSet.create(LINE_COMMENT,C_STYLE_COMMENT,WRONG_C_STYLE_COMMENT);        

    IElementType STRING_LITERAL = new ProtobufElementType("STRING_LITERAL");
    IElementType WRONG_STRING_LITERAL = new ProtobufElementType("WRONG_STRING_LITERAL");
    TokenSet STRING_LITERALS = TokenSet.create(STRING_LITERAL,WRONG_STRING_LITERAL);
    TokenSet WRONG_STRING_LITERALS = TokenSet.create(WRONG_STRING_LITERAL);

    //braces
    IElementType OPEN_BLOCK = new ProtobufElementType("OPEN_BLOCK");
    IElementType CLOSE_BLOCK = new ProtobufElementType("CLOSE_BLOCK");
    IElementType OPEN_BRACE = new ProtobufElementType("OPEN_BRACE");
    IElementType CLOSE_BRACE = new ProtobufElementType("CLOSE_BRACE");
    IElementType OPEN_PARANT = new ProtobufElementType("OPEN_PARANT");
    IElementType CLOSE_PARANT = new ProtobufElementType("CLOSE_PARANT");
    TokenSet BRACES = TokenSet.create(OPEN_BLOCK,CLOSE_BLOCK,OPEN_BRACE,CLOSE_BRACE,OPEN_PARANT,CLOSE_PARANT);

    //Numbers
    IElementType NUM_INT = new ProtobufElementType("NUM_INT");
    IElementType NUM_DOUBLE = new ProtobufElementType("NUM_DOUBLE");
    TokenSet NUMBERS = TokenSet.create(NUM_INT,NUM_DOUBLE);


//keywords    

    //definitions
    IElementType IMPORT = new ProtobufElementType("IMPORT");
    IElementType PACKAGE = new ProtobufElementType("PACKAGE");
    IElementType MESSAGE = new ProtobufElementType("MESSAGE");
    IElementType EXTEND = new ProtobufElementType("EXTEND");
    IElementType SERVICE = new ProtobufElementType("SERVICE");
    IElementType OPTION = new ProtobufElementType("OPTION");
    IElementType ENUM = new ProtobufElementType("ENUM");
    TokenSet DEFINITIONS = TokenSet.create(IMPORT,PACKAGE,MESSAGE,EXTEND,SERVICE,OPTION,ENUM); //keywords


    IElementType RPC = new ProtobufElementType("RPC");
    IElementType RETURNS = new ProtobufElementType("RETURNS");
    IElementType EXTENSIONS = new ProtobufElementType("EXTENSIONS");
    //--

    //special words
    IElementType TO = new ProtobufElementType("TO");
    IElementType MAX = new ProtobufElementType("MAX");

    //boolean values
    IElementType TRUE = new ProtobufElementType("TRUE");
    IElementType FALSE = new ProtobufElementType("FALSE");
    TokenSet BOOL_VALUES = TokenSet.create(TRUE,FALSE); //keywords

    //spec field label
    IElementType REQUIRED = new ProtobufElementType("REQUIRED");
    IElementType OPTIONAL = new ProtobufElementType("OPTIONAL");
    IElementType REPEATED = new ProtobufElementType("REPEATED");
    TokenSet FIELD_LABELS = TokenSet.create(REQUIRED,OPTIONAL,REPEATED); //keywords


    IElementType GROUP = new ProtobufElementType("GROUP");  TokenSet GROUP_SET = TokenSet.create(GROUP); //hack for lookAhead
    
    //Built-in types
    IElementType DOUBLE = new ProtobufElementType("DOUBLE");
    IElementType FLOAT = new ProtobufElementType("FLOAT");
    IElementType INT32 = new ProtobufElementType("INT32");
    IElementType INT64 = new ProtobufElementType("INT64");
    IElementType UINT32 = new ProtobufElementType("UINT32");
    IElementType UINT64 = new ProtobufElementType("UINT64");
    IElementType SINT32 = new ProtobufElementType("SINT32");
    IElementType SINT64 = new ProtobufElementType("SINT64");
    IElementType FIXED32 = new ProtobufElementType("FIXED32");
    IElementType FIXED64 = new ProtobufElementType("FIXED64");
    IElementType SFIXED32 = new ProtobufElementType("SFIXED32");
    IElementType SFIXED64 = new ProtobufElementType("SFIXED4");
    IElementType BOOL = new ProtobufElementType("BOOL");
    IElementType STRING = new ProtobufElementType("STRING");
    IElementType BYTES = new ProtobufElementType("BYTES");
    TokenSet BUILT_IN_TYPES = TokenSet.create(DOUBLE,FLOAT,INT32,INT64,
                                                UINT32,UINT64,SINT32,SINT64,
                                                FIXED32,FIXED64,SFIXED32,
                                                SFIXED64,BOOL,STRING,BYTES);  //keywords

    TokenSet OTHER_KEYWORDS = TokenSet.create(RPC,RETURNS,GROUP,EXTENSIONS,TO,MAX);//keywords

    //Options
    //--File settings
    /*IElementType OPTIMIZE_FOR = new ProtobufElementType("optimize for");
    IElementType JAVA_PACKAGE = new ProtobufElementType("java_package");
    IElementType JAVA_OUTER_CLASSNAME = new ProtobufElementType("java_outer_classname");
    IElementType JAVA_MULTIPLE_FILES = new ProtobufElementType("java_multiple_files");
    IElementType CC_ABSTRACT_SERVICES = new ProtobufElementType("cc_abstract_services");
    IElementType JAVA_ABSTRACT_SERVICES = new ProtobufElementType("java_abstract_services");
    IElementType PY_ABSTRACT_SERVICES = new ProtobufElementType("py_abstract_services");
    TokenSet FILE_OPTIONS = TokenSet.create(OPTIMIZE_FOR,JAVA_PACKAGE,JAVA_OUTER_CLASSNAME,
                                            JAVA_MULTIPLE_FILES,CC_ABSTRACT_SERVICES,
                                            JAVA_ABSTRACT_SERVICES,PY_ABSTRACT_SERVICES); //keyword

    //--Message settings
    IElementType MESSAGE_SET_WIRE_FORMAT = new ProtobufElementType("message set wire format");
    TokenSet MESSAGE_OPTIONS = TokenSet.create(MESSAGE_SET_WIRE_FORMAT); //keyword
    */

    //--Field settings
    //IElementType DEPRECATED = new ProtobufElementType("deprecated");
    //IElementType PACKED = new ProtobufElementType("packed");
    //IElementType DEFAULT = new ProtobufElementType("default");
    //TokenSet FIELD_OPTIONS = TokenSet.create(DEPRECATED,PACKED,DEFAULT);  //keyword

    TokenSet KEYWORDS = TokenSet.orSet(BUILT_IN_TYPES,
                                        /*FIELD_OPTIONS,*/
                                        FIELD_LABELS,
                                        BOOL_VALUES,
                                        DEFINITIONS,
                                        OTHER_KEYWORDS
                                        );
    TokenSet IK = TokenSet.orSet(IDENTIFIERS,KEYWORDS);
    TokenSet CONSTANT_LITERALS = TokenSet.orSet(STRING_LITERALS,NUMBERS,BOOL_VALUES,IK); //filed option value
}
