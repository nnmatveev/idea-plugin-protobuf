package protobuf.lang.parser.parsing.definitions;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.parser.parsing.ReferenceElement;
import protobuf.lang.parser.util.PatchedPsiBuilder;

import static protobuf.lang.ProtobufElementTypes.*;
/**
 * author: Nikolay Matveev
 * Date: Mar 25, 2010
 */
public class FieldDefinition {

    public static boolean parse(PatchedPsiBuilder builder) {
        if (!builder.compareToken(FIELD_LABELS)) {
            return false;
        }
        PsiBuilder.Marker messageMarker = builder.mark();
        //builder.matchAs(FIELD_LABELS,FIELD_LABEL,"");
        builder.match(FIELD_LABELS);
        boolean isGroupField = false;
        if (builder.compareToken(GROUP)) isGroupField = true;
        parseType(builder);
        builder.matchAs(IK, NAME, "identifier.expected");
        builder.match(EQUAL, "equal.expected");
        builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
        parseOptions(builder);
        if(isGroupField) MessageDefinition.parseMessageBlock(builder);
        if(!isGroupField) builder.match(SEMICOLON,"semicolon.expected");
        messageMarker.done(FIELD_DEF);
        return true;
    }

    //done
    public static boolean parseType(PatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (builder.compareToken(BUILT_IN_TYPES) || builder.compareToken(GROUP)) {
            builder.match(); //matching token BUILT_IN_TYPES or GROUP            
        } else if (ReferenceElement.parseForCustomType(builder)) {
        } else {
            marker.drop();
            return false;
        }
        marker.done(FIELD_TYPE);
        return true;
    }
    //done
    public static boolean parseOptions(PatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_BRACE)) {
            return false;
        }
        PsiBuilder.Marker optionsMarker = builder.mark();
        builder.match(OPEN_BRACE);
        do {
            if (OptionDefinition.parseOptionAssigment(builder)) {
            } else {
                builder.error("option.assigment.expected");
                break;
            }
        } while (!builder.eof() && builder.match(COMMA));
        builder.match(CLOSE_BRACE, "close.brace.expected");
        optionsMarker.done(OPTION_LIST);
        return true;
    }
}
