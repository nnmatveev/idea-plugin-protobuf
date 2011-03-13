package protobuf.lang.parser.parsing;

import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;
import protobuf.lang.parser.parsing.statements.*;

/**
 * @author Nikolay Matveev
 * Date: Mar 9, 2010
 */   

//  ProtoDefinition :: = ( PbMessageDef | PbExtendDef | MembersDefinition | PbImportDef | package | option | service | ";" )*
//  STRING_LITERALS :: = NUMBERS | IDENTIFIER | STRING | KEYWORD | BOOL_VALUES 
    
public class CompilationUnit implements PbElementTypes {
    public static void parse(PbPatchedPsiBuilder builder) {
        //parseSeparateOption root level statements
        while (!builder.eof()) {
            if (PackageStatement.parse(builder)) {
            } else if (ImportStatement.parse(builder)) {
            } else if (OptionStatement.parseSeparateOption(builder)) {
            } else if (ExtendStatement.parse(builder)) {
            } else if (ServiceStatement.parse(builder)) {
            } else if (MessageStatement.parse(builder)) {
            } else if (EnumStatement.parse(builder)) {
            } else if (builder.match(SEMICOLON)) {
            } else {                                
                builder.eatError("top.level.def.expected");
            }                           
        }
    }
}