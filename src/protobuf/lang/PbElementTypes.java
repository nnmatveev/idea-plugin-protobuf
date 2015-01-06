package protobuf.lang;

import com.intellij.psi.tree.TokenSet;

/**
 * @author Nikolay Matveev
 * Date: Mar 9, 2010
 */
public interface PbElementTypes extends PbTokenTypes {

    PbElementType CUSTOM_TYPE_REF = new PbElementType("CUSTOM_TYPE_REF");
    PbElementType BLOCK = new PbElementType("BLOCK");

    PbElementType PACKAGE_DECL = new PbElementType("PACKAGE_DECL");
    PbElementType PACKAGE_REF = new PbElementType("PACKAGE_REF");

    PbElementType OPTION_REF = new PbElementType("OPTION_REF");
    PbElementType OPTION_REF_SEQ = new PbElementType("OPTION_REF_SEQ");
    PbElementType OPTION_ASSIGNMENT = new PbElementType("OPTION_ASSIGNMENT");

    PbElementType IMPORT_DECL = new PbElementType("IMPORT_DECL");
    PbElementType IMPORT_REF = new PbElementType("IMPORT_REF");

    PbElementType SERVICE_DECL = new PbElementType("SERVICE_DECL");
    PbElementType SERVICE_BLOCK = new PbElementType("SERVICE_BLOCK");
    PbElementType SERVICE_METHOD_DECL = new PbElementType("SERVICE_METHOD_DECL");
    PbElementType SERVICE_METHOD_BLOCK = new PbElementType("SERVICE_METHOD_BLOCK");

    PbElementType EXTEND_DECL = new PbElementType("EXTEND_DECL");
    PbElementType EXTEND_BLOCK = new PbElementType("EXTEND_BLOCK");

    PbElementType MESSAGE_DECL = new PbElementType("MESSAGE_DECL");
    PbElementType MESSAGE_BLOCK = new PbElementType("MESSAGE_BLOCK");
    
    PbElementType FIELD_DECL = new PbElementType("FIELD_DECL");
    PbElementType FIELD_TYPE = new PbElementType("FIELD_TYPE");
    PbElementType GROUP_DECL = new PbElementType("GROUP_DECL");
    PbElementType OPTION_LIST = new PbElementType("OPTION_LIST");

    PbElementType ENUM_DECL = new PbElementType("ENUM_DECL");
    PbElementType ENUM_BLOCK = new PbElementType("ENUM_BLOCK");
    PbElementType ENUM_CONST_DECL = new PbElementType("ENUM_CONST_DECL");

    PbElementType ONEOF_DECL = new PbElementType("ONEOF_DECL");
    PbElementType ONEOF_BLOCK = new PbElementType("ONEOF_BLOCK");
    PbElementType ONEOF_MEMBER_DECL = new PbElementType("ONEOF_MEMBER_DECL");
    PbElementType ONEOF_MEMBER_TYPE = new PbElementType("ONEOF_MEMBER_TYPE");

    PbElementType EXTENSIONS_DECL = new PbElementType("EXTENSIONS_DECL");
    PbElementType EXTENSIONS_RANGE = new PbElementType("EXTENSIONS_RANGE");
    
    PbElementType VALUE = new PbElementType("VALUE");
    
    TokenSet SKIPPED_ELEMENTS = TokenSet.orSet(WHITE_SPACES,COMMENTS);
    
    TokenSet BLOCKS = TokenSet.create(BLOCK,MESSAGE_BLOCK,ENUM_BLOCK,SERVICE_BLOCK,SERVICE_METHOD_BLOCK,EXTEND_BLOCK);    
    TokenSet DECLS = TokenSet.create(MESSAGE_DECL, ENUM_DECL, SERVICE_DECL, SERVICE_METHOD_DECL, EXTEND_DECL, EXTENSIONS_DECL, IMPORT_DECL, PACKAGE_DECL, FIELD_DECL, ENUM_CONST_DECL,GROUP_DECL);

}
