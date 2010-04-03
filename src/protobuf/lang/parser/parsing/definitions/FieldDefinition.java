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
        if (builder.lookAhead(FIELD_LABELS, GROUP_SET)) {
            PsiBuilder.Marker messageMarker = builder.mark();
            builder.match(FIELD_LABELS);
            builder.match(GROUP);
            builder.matchAs(IK, NAME, "identifier.expected");
            builder.match(EQUAL, "equal.expected");
            builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
            parseOptions(builder);
            if (!MessageDefinition.parseMessageBlock(builder)) {
                builder.error("group.block.expected");
            }            
            messageMarker.done(GROUP_DEF);

        } else {
            PsiBuilder.Marker messageMarker = builder.mark();            
            builder.match(FIELD_LABELS);
            parseType(builder);
            builder.matchAs(IK, NAME, "identifier.expected");
            builder.match(EQUAL, "equal.expected");
            builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
            parseOptions(builder);
            builder.match(SEMICOLON, "semicolon.expected");
            messageMarker.done(FIELD_DEF);
        }
        return true;
    }

    //done
    public static boolean parseType(PatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (builder.match(BUILT_IN_TYPES)) {                        
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
