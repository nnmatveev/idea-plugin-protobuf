package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 * Date: Mar 10, 2010
 */

//  grammar - ok
//  PbEnumDef ::= 'enum' IDENTIFIER '{' enumBlock '}'
//  enumBlock ::= (enumStatement)*
//  enumStatement ::= PbOptionDef | enumConstant | ';'
//  enumConstant ::=  IDENTIFIER '=' NUM_INT fieldOptions? ';'

public class EnumDeclaration implements PbElementTypes {
    public static boolean parse(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(ENUM)) {
            return false;
        }
        PsiBuilder.Marker enumMarker = builder.mark();
        builder.match(ENUM);
        builder.match(IK,"identifier.expected");
        //builder.matchAs(IK,NAME, "identifier.expected");
        if(!parseEnumBlock(builder)){
            builder.error("enum.block.expected");
        }        
        enumMarker.done(ENUM_DECL);
        return true;
    }
    //done
    public static boolean parseEnumBlock(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_BLOCK)) {
            return false;
        }
        PsiBuilder.Marker enumBlockMarker = builder.mark();
        builder.match(OPEN_BLOCK);
        while(!builder.eof() && !builder.compareToken(CLOSE_BLOCK)){
            if(!parseEnumStatement(builder)){
                builder.eatError("unexpected.token");
            }
        }
        builder.match(CLOSE_BLOCK,"close.block.expected");
        enumBlockMarker.done(ENUM_BLOCK);
        return true;
    }

    
    public static boolean parseEnumStatement(PbPatchedPsiBuilder builder) {
        //PsiBuilder.Marker enumStatementMarker = builder.mark();
        if(builder.match(SEMICOLON)){
        } else if(OptionDeclaration.parseSeparateOption(builder)){ //todo: maybe make a lookahead to option because it is not clear if enum name is 'option'
        } else if(parseEnumConstant(builder)){      //
        } else {
            //enumStatementMarker.drop();
            return false;
        }
        //enumStatementMarker.done(ENUM_STATEMENT);
        return true;
    }

    //done
    public static boolean parseEnumConstant(PbPatchedPsiBuilder builder) {
        if(!builder.compareToken(IK)){
            return false;
        }
        PsiBuilder.Marker enumConstantkMarker = builder.mark();
        builder.match(IK,"identifier.expected");
        //builder.matchAs(IK,NAME,"identifier.expected");
        builder.match(EQUAL,"equal.expected");
        if(builder.compareToken(MINUS)){
            PsiBuilder.Marker marker = builder.mark();
            builder.match(MINUS);
            builder.match(NUM_INT,"num.integer.expected");
            marker.done(VALUE);
        } else {
            builder.matchAs(NUM_INT,VALUE,"num.integer.expected");
        }
        OptionDeclaration.parseOptionList(builder);
        builder.match(SEMICOLON,"semicolon.expected");
        enumConstantkMarker.done(ENUM_CONST_DECL);
        return true;
    }


}
