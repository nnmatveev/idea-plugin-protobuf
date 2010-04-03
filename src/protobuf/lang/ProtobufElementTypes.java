package protobuf.lang;

import com.intellij.psi.tree.TokenSet;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */
public interface ProtobufElementTypes extends ProtobufTokenTypes {

    ProtobufElementType REF = new ProtobufElementType("Reference");

    ProtobufElementType TYPE_REF = new ProtobufElementType("Reference");

    ProtobufElementType PACKAGE_DEF = new ProtobufElementType("Package definition");
    ProtobufElementType PACKAGE_REF = new ProtobufElementType("Package reference");

    ProtobufElementType OPTION_DEF = new ProtobufElementType("Option definition");
    ProtobufElementType OPTION_NAME = new ProtobufElementType("Option name");
    ProtobufElementType OPTION_REF = new ProtobufElementType("Option reference");
    ProtobufElementType OPTION_ASSIGMENT = new ProtobufElementType("Optionassigment");

    ProtobufElementType IMPORT_DEF = new ProtobufElementType("Import definition");
    ProtobufElementType IMPORT_REF = new ProtobufElementType("Import reference");

    ProtobufElementType SERVICE_DEF = new ProtobufElementType("Service definition");
    ProtobufElementType SERVICE_BLOCK = new ProtobufElementType("Service block");
    ProtobufElementType SERVICE_METHOD_DEF = new ProtobufElementType("Service method");
    ProtobufElementType SERVICE_METHOD_BLOCK = new ProtobufElementType("Service method block");

    ProtobufElementType EXTEND_DEF = new ProtobufElementType("Extend definition");
    ProtobufElementType EXTEND_BLOCK = new ProtobufElementType("Extend block");

    ProtobufElementType MESSAGE_DEF = new ProtobufElementType("Message definition");
    ProtobufElementType MESSAGE_NAME = new ProtobufElementType("Message definition");
    ProtobufElementType MESSAGE_BLOCK = new ProtobufElementType("Message block");
    
    ProtobufElementType FIELD_DEF = new ProtobufElementType("Field definition");
    ProtobufElementType FIELD_TYPE = new ProtobufElementType("Field type");
    ProtobufElementType FIELD_LABEL = new ProtobufElementType("Field label");
    ProtobufElementType OPTION_LIST = new ProtobufElementType("Field settings");
    ProtobufElementType GROUP_DEF = new ProtobufElementType("Group field definition");
    ProtobufElementType GROUP_BLOCK = new ProtobufElementType("group block");

    ProtobufElementType ENUM_DEF = new ProtobufElementType("Enum definition");
    ProtobufElementType ENUM_NAME = new ProtobufElementType("Enum name");
    ProtobufElementType ENUM_BLOCK = new ProtobufElementType("Enum block");    
    ProtobufElementType ENUM_CONST_DEF = new ProtobufElementType("Enum constant");


    ProtobufElementType EXTENSIONS_DEF = new ProtobufElementType("Extensions definition");
    ProtobufElementType EXTENSIONS_RANGE = new ProtobufElementType("Extensions range");

    ProtobufElementType NAME = new ProtobufElementType("Name");
    ProtobufElementType VALUE = new ProtobufElementType("Value");
    
    TokenSet SKIPED_ELEMENTS = TokenSet.orSet(WHITE_SPACES,COMMENTS);
    
    TokenSet BLOCKS = TokenSet.create(MESSAGE_BLOCK,ENUM_BLOCK,SERVICE_BLOCK,SERVICE_METHOD_BLOCK,EXTEND_BLOCK);    
    TokenSet DEFS = TokenSet.create(MESSAGE_DEF,ENUM_DEF,SERVICE_DEF,SERVICE_METHOD_DEF,EXTEND_DEF,EXTENSIONS_DEF,IMPORT_DEF,PACKAGE_DEF,FIELD_DEF,OPTION_DEF,ENUM_CONST_DEF);            

}
