package protobuf.lang.parser.parsing;

import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 *         Date: Mar 9, 2010
 */

//  ProtoDefinition :: = ( PbMessageDef | PbExtendDef | MembersDefinition | PbImportDef | syntax | package | option | service | ";" )*
//  STRING_LITERALS :: = NUMBERS | IDENTIFIER | STRING | KEYWORD | BOOL_VALUES 

public class CompilationUnit implements PbElementTypes {
    public static void parse(PbPatchedPsiBuilder builder) {
        //parseSeparateOption root level statements
        while (!builder.eof()) {
            if (SyntaxDeclaration.parse(builder)) {
            } else if (PackageDeclaration.parse(builder)) {
            } else if (ImportDeclaration.parse(builder)) {
            } else if (OptionDeclaration.parseSeparateOption(builder)) {
            } else if (ExtendDeclaration.parse(builder)) {
            } else if (ServiceDeclaration.parse(builder)) {
            } else if (MessageDeclaration.parse(builder)) {
            } else if (EnumDeclaration.parse(builder)) {
            } else if (builder.match(SEMICOLON)) {
            } else {
                builder.eatError("top.level.def.expected");
            }
        }
    }
}