package protobuf.lang.parser.parsing.statements;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.parser.parsing.ReferenceElement;
import protobuf.lang.parser.util.PatchedPsiBuilder;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 25, 2010
 */
public class FieldStatement {

    public static boolean parse(PatchedPsiBuilder builder) {
        if (!builder.compareToken(FIELD_LABELS)) {
            return false;
        }
        if (builder.lookAhead(FIELD_LABELS, GROUP_SET)) {
            PsiBuilder.Marker messageMarker = builder.mark();
            builder.match(FIELD_LABELS);
            builder.match(GROUP);
            builder.match(IK,"identifier.expected");
            //builder.matchAs(IK, NAME, "identifier.expected");
            builder.match(EQUAL, "equal.expected");
            builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
            OptionStatement.parseOptionList(builder);
            if (!MessageStatement.parseMessageBlock(builder)) {
                builder.error("group.block.expected");
            }            
            messageMarker.done(GROUP_DECL);

        } else {
            PsiBuilder.Marker messageMarker = builder.mark();            
            builder.match(FIELD_LABELS);
            parseType(builder);
            builder.match(IK, "identifier.expected");
            //builder.matchAs(IK, NAME, "identifier.expected");
            builder.match(EQUAL, "equal.expected");
            builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
            OptionStatement.parseOptionList(builder);
            builder.match(SEMICOLON, "semicolon.expected");
            messageMarker.done(FIELD_DECL);
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
}
