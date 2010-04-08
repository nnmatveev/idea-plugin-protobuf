package protobuf.lang.parser.parsing.statements;

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
public class OptionStatement implements ProtobufElementTypes {
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
        optionMarker.done(OPTION_DECL);
        return true;
    }

    //done

    public static boolean parseOptionAssigment(PatchedPsiBuilder builder) {
        PsiBuilder.Marker optionAssigmentMarker = builder.mark();
        if (builder.compareToken(IK)) {
            PsiBuilder.Marker optionNameMarker = builder.mark();
            builder.match(IK);
            optionNameMarker.done(NAME);
        } else if (!ReferenceElement.parseForCustomOption(builder)) {
            optionAssigmentMarker.drop();
            return false;
        }
        builder.match(EQUAL, "equal.expected");
        if (!parseOptionValue(builder)) {
            builder.error("value.expected");
        }
        optionAssigmentMarker.done(OPTION_ASSIGMENT);
        return true;
    }

    public static boolean parseOptionValue(PatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();        
        if (builder.match(NUMBERS)) {
        } else if (builder.match(MINUS)) {
            if (builder.match(NUMBERS)) {
            } else if (builder.match("inf")) {
            } else if (builder.match("nan")) {
            } else {
                builder.error("number.expexted");
            }
        } else if (builder.match(BOOL_VALUES)) {
        } else if (builder.match(IK)) {
        } else if (builder.match(STRING_LITERALS)) {
        } else {
            marker.drop();
            return false;
        }
        marker.done(VALUE);
        return true;
    }
}
