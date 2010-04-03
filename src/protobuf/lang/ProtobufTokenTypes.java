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

    IElementType IDENTIFIER = new ProtobufElementType("identifier");
    TokenSet IDENTIFIERS = TokenSet.create(IDENTIFIER);

    IElementType SEMICOLON = new ProtobufElementType(";");
    IElementType DOT = new ProtobufElementType(".");
    IElementType COMMA = new ProtobufElementType(",");
    
    IElementType EQUAL = new ProtobufElementType("=");

    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    TokenSet BAD_CHARACTERS = TokenSet.create(BAD_CHARACTER);

    IElementType NEW_LINE = new ProtobufElementType("new line");
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    TokenSet WHITE_SPACES = TokenSet.create(WHITE_SPACE,NEW_LINE);

    IElementType LINE_COMMENT = new ProtobufElementType("line comment");
    IElementType C_STYLE_COMMENT = new ProtobufElementType("c style comment");
    TokenSet COMMENTS = TokenSet.create(LINE_COMMENT,C_STYLE_COMMENT);       

    IElementType STRING_LITERAL = new ProtobufElementType("string literal");
    TokenSet STRING_LITERALS = TokenSet.create(STRING_LITERAL);

    IElementType WRONG_STRING_LITERAL = new ProtobufElementType("wrong string literal");
    TokenSet WRONG_STRING_LITERALS = TokenSet.create(WRONG_STRING_LITERAL);

    //braces
    IElementType OPEN_BLOCK = new ProtobufElementType("{");
    IElementType CLOSE_BLOCK = new ProtobufElementType("}");
    IElementType OPEN_BRACE = new ProtobufElementType("[");
    IElementType CLOSE_BRACE = new ProtobufElementType("]");
    IElementType OPEN_PARANT = new ProtobufElementType("(");
    IElementType CLOSE_PARANT = new ProtobufElementType(")");
    TokenSet BRACES = TokenSet.create(OPEN_BLOCK,CLOSE_BLOCK,OPEN_BRACE,CLOSE_BRACE,OPEN_PARANT,CLOSE_PARANT);

    //Numbers
    IElementType NUM_INT = new ProtobufElementType("num integer");
    IElementType NUM_DOUBLE = new ProtobufElementType("num double");
    TokenSet NUMBERS = TokenSet.create(NUM_INT,NUM_DOUBLE);


//keywords    

    //definitions
    IElementType IMPORT = new ProtobufElementType("import");
    IElementType PACKAGE = new ProtobufElementType("package");
    IElementType MESSAGE = new ProtobufElementType("message");
    IElementType EXTEND = new ProtobufElementType("extend");
    IElementType SERVICE = new ProtobufElementType("service");
    IElementType OPTION = new ProtobufElementType("option");
    IElementType ENUM = new ProtobufElementType("members");
    TokenSet DEFINITIONS = TokenSet.create(IMPORT,PACKAGE,MESSAGE,EXTEND,SERVICE,OPTION,ENUM); //keywords


    IElementType RPC = new ProtobufElementType("rpc");
    IElementType RETURNS = new ProtobufElementType("returns");
    IElementType EXTENSIONS = new ProtobufElementType("extensions");
    //--

    //special words
    IElementType TO = new ProtobufElementType("to");
    IElementType MAX = new ProtobufElementType("max");

    //boolean values
    IElementType TRUE = new ProtobufElementType("true");
    IElementType FALSE = new ProtobufElementType("false");
    TokenSet BOOL_VALUES = TokenSet.create(TRUE,FALSE); //keyword

    //spec field label
    IElementType REQUIRED = new ProtobufElementType("required");
    IElementType OPTIONAL = new ProtobufElementType("optional");
    IElementType REPEATED = new ProtobufElementType("repeated");
    TokenSet FIELD_LABELS = TokenSet.create(REQUIRED,OPTIONAL,REPEATED); //keyword


    IElementType GROUP = new ProtobufElementType("group");  TokenSet GROUP_SET = TokenSet.create(GROUP); //hack for lookAhead
    
    //Built-in types
    IElementType DOUBLE = new ProtobufElementType("double");
    IElementType FLOAT = new ProtobufElementType("float");
    IElementType INT32 = new ProtobufElementType("int32");
    IElementType INT64 = new ProtobufElementType("int64");
    IElementType UINT32 = new ProtobufElementType("uint32");
    IElementType UINT64 = new ProtobufElementType("uint64");
    IElementType SINT32 = new ProtobufElementType("sint32");
    IElementType SINT64 = new ProtobufElementType("sint64");
    IElementType FIXED32 = new ProtobufElementType("fixed32");
    IElementType FIXED64 = new ProtobufElementType("fixed64");
    IElementType SFIXED32 = new ProtobufElementType("sfixed32");
    IElementType SFIXED64 = new ProtobufElementType("sfixed64");
    IElementType BOOL = new ProtobufElementType("bool");
    IElementType STRING = new ProtobufElementType("string");
    IElementType BYTES = new ProtobufElementType("bytes");
    TokenSet BUILT_IN_TYPES = TokenSet.create(DOUBLE,FLOAT,INT32,INT64,
                                                UINT32,UINT64,SINT32,SINT64,
                                                FIXED32,FIXED64,SFIXED32,
                                                SFIXED64,BOOL,STRING,BYTES);  //keyword

    TokenSet OTHER_KEYWORDS = TokenSet.create(RPC,RETURNS,GROUP,EXTENSIONS,TO,MAX);//keyword

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
