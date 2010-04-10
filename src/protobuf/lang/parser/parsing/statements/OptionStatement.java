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
    
    public static boolean parseSeparateOption(PatchedPsiBuilder builder) {
        if (!builder.compareToken(OPTION)) {
            return false;
        }
        PsiBuilder.Marker optionAssigmentMarker = builder.mark();
        builder.match(OPTION);
        if (!parseOptionAssigment(builder)) {
            builder.error("option.assigment.expected");
        }
        builder.match(SEMICOLON, "semicolon.expected");
        optionAssigmentMarker.done(OPTION_ASSIGNMENT);        
        return true;
    }

    public static boolean parseOptionList(PatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_BRACE)) {
            return false;
        }
        PsiBuilder.Marker optionsMarker = builder.mark();
        builder.match(OPEN_BRACE);
        do {
            PsiBuilder.Marker optionAssigmentMarker = builder.mark();
            if (parseOptionAssigment(builder)) {
                optionAssigmentMarker.done(OPTION_ASSIGNMENT);
            } else {
                optionAssigmentMarker.drop();
                builder.error("option.assigment.expected");
                break;
            }
        } while (!builder.eof() && builder.match(COMMA));
        builder.match(CLOSE_BRACE, "close.brace.expected");
        optionsMarker.done(OPTION_LIST);
        return true;
    }

    public static boolean parseOptionAssigment(PatchedPsiBuilder builder) {
        if (builder.compareToken(IK)) {
            builder.match(IK);            
        } else if (!ReferenceElement.parseForCustomOption(builder)) {            
            return false;
        }
        builder.match(EQUAL, "equal.expected");
        if (!parseOptionValue(builder)) {
            builder.error("value.expected");
        }
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
