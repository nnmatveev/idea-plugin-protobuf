package protobuf.lang;

import com.intellij.psi.tree.TokenSet;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public interface ProtobufElementTypes extends ProtobufTokenTypes {

    ProtobufElementType CUSTOM_TYPE_REF = new ProtobufElementType("CUSTOM_TYPE_REF");
    ProtobufElementType BLOCK = new ProtobufElementType("BLOCK");

    ProtobufElementType PACKAGE_DECL = new ProtobufElementType("PACKAGE_DECL");
    ProtobufElementType PACKAGE_REF = new ProtobufElementType("PACKAGE_REF");

    ProtobufElementType OPTION_REF = new ProtobufElementType("OPTION_REF");
    ProtobufElementType OPTION_REF_SEQ = new ProtobufElementType("OPTION_REF_SEQ");
    ProtobufElementType OPTION_ASSIGNMENT = new ProtobufElementType("OPTION_ASSIGNMENT");

    ProtobufElementType IMPORT_DECL = new ProtobufElementType("IMPORT_DECL");
    ProtobufElementType IMPORT_REF = new ProtobufElementType("IMPORT_REF");

    ProtobufElementType SERVICE_DECL = new ProtobufElementType("SERVICE_DECL");
    ProtobufElementType SERVICE_BLOCK = new ProtobufElementType("SERVICE_BLOCK");
    ProtobufElementType SERVICE_METHOD_DECL = new ProtobufElementType("SERVICE_METHOD_DECL");
    ProtobufElementType SERVICE_METHOD_BLOCK = new ProtobufElementType("SERVICE_METHOD_BLOCK");

    ProtobufElementType EXTEND_DECL = new ProtobufElementType("EXTEND_DECL");
    ProtobufElementType EXTEND_BLOCK = new ProtobufElementType("EXTEND_BLOCK");

    ProtobufElementType MESSAGE_DECL = new ProtobufElementType("MESSAGE_DECL");
    ProtobufElementType MESSAGE_BLOCK = new ProtobufElementType("MESSAGE_BLOCK");
    
    ProtobufElementType FIELD_DECL = new ProtobufElementType("FIELD_DECL");
    ProtobufElementType FIELD_TYPE = new ProtobufElementType("FIELD_TYPE");
    ProtobufElementType GROUP_DECL = new ProtobufElementType("GROUP_DECL");
    ProtobufElementType OPTION_LIST = new ProtobufElementType("OPTION_LIST");

    ProtobufElementType ENUM_DECL = new ProtobufElementType("ENUM_DECL");
    ProtobufElementType ENUM_BLOCK = new ProtobufElementType("ENUM_BLOCK");
    ProtobufElementType ENUM_CONST_DECL = new ProtobufElementType("ENUM_CONST_DECL");


    ProtobufElementType EXTENSIONS_DECL = new ProtobufElementType("EXTENSIONS_DECL");
    ProtobufElementType EXTENSIONS_RANGE = new ProtobufElementType("EXTENSIONS_RANGE");
    
    ProtobufElementType VALUE = new ProtobufElementType("VALUE");
    
    TokenSet SKIPPED_ELEMENTS = TokenSet.orSet(WHITE_SPACES,COMMENTS);
    
    TokenSet BLOCKS = TokenSet.create(BLOCK,MESSAGE_BLOCK,ENUM_BLOCK,SERVICE_BLOCK,SERVICE_METHOD_BLOCK,EXTEND_BLOCK);    
    TokenSet DECLS = TokenSet.create(MESSAGE_DECL, ENUM_DECL, SERVICE_DECL, SERVICE_METHOD_DECL, EXTEND_DECL, EXTENSIONS_DECL, IMPORT_DECL, PACKAGE_DECL, FIELD_DECL, ENUM_CONST_DECL,GROUP_DECL);

}
