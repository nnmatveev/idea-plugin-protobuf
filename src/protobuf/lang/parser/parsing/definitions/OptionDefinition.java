package protobuf.lang.parser.parsing.definitions;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.ProtobufElementTypes;
import protobuf.lang.parser.parsing.ReferenceElement;
import protobuf.lang.parser.util.PatchedPsiBuilder;

/**
 * author: Nikolay Matveev
 * Date: Mar 10, 2010
 */

//  grammar - ok
//  PbOptionDef ::= "option" optionAssigment ";"
//  optionAssigment ::= optionName "=" STRING_LITERALS    
//  optionName ::= optionNamePart ("."optionNamePart)*
//  optionNamePart ::= IK |"(" IK("."IK)* ")


//done
public class OptionDefinition implements ProtobufElementTypes {
    public static boolean parse(PatchedPsiBuilder builder) {
        if (!builder.compareToken(OPTION)) {
            return false;
        }
        PsiBuilder.Marker optionMarker = builder.mark();
        builder.match(OPTION);
        if (!parseOptionAssigment(builder)) {
            builder.error("option.assigment.expected");
        }
        builder.match(SEMICOLON, "semicolon.expected");
        optionMarker.done(OPTION_DEF);
        return true;
    }

    //done

    public static boolean parseOptionAssigment(PatchedPsiBuilder builder) {
        PsiBuilder.Marker optionAssigmentMarker = builder.mark();
        if (!ReferenceElement.parseForOptionName(builder)) {
            optionAssigmentMarker.rollbackTo();
            return false;
        }
        builder.match(EQUAL, "equal.expected");
        builder.match(CONSTANT_LITERALS, "constant.literal.expected");
        optionAssigmentMarker.done(OPTION_ASSIGMENT);
        return true;
    }   
}
