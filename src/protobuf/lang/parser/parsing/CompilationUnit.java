package protobuf.lang.parser.parsing;

import protobuf.lang.ProtobufElementTypes;
import protobuf.lang.parser.util.PatchedPsiBuilder;
import protobuf.lang.parser.parsing.definitions.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 9, 2010
 */   

//  ProtoDefinition :: = ( PbMessageDef | PbExtendDef | MembersDefinition | PbImportDef | package | option | service | ";" )*
//  STRING_LITERALS :: = NUMBERS | IDENTIFIER | STRING | KEYWORD | BOOL_VALUES 
    
public class CompilationUnit implements ProtobufElementTypes {
    public static void parse(PatchedPsiBuilder builder) {
        //parse root level definitions
        while (!builder.eof()) {
            if (PackageDefinition.parse(builder)) {
            } else if (ImportDefinition.parse(builder)) {
            } else if (OptionDefinition.parse(builder)) {
            } else if (ExtendDefinition.parse(builder)) {
            } else if (ServiceDefinition.parse(builder)) {
            } else if (MessageDefinition.parse(builder)) {
            } else if (EnumDefinition.parse(builder)) {                
            } else if (builder.match(SEMICOLON)) {
            } else {                                
                builder.eatError("top.level.def.expected");
            }                           
        }
    }
}