package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

import static protobuf.lang.PbElementTypes.*;

/**
 * @author Nikolay Matveev
 *         Date: Mar 25, 2010
 */
public class Proto3FieldDeclaration {

    public static boolean parse(PbPatchedPsiBuilder builder) {
        if (builder.lookAhead(PROTO3_FIELD_LABELS, GROUP_SET) || builder.lookAhead(GROUP_SET)) {
            PsiBuilder.Marker messageMarker = builder.mark();
            builder.match(PROTO3_FIELD_LABELS);
            builder.match(GROUP);
            builder.match(IK, "identifier.expected");
            //builder.matchAs(IK, NAME, "identifier.expected");
            builder.match(EQUAL, "equal.expected");
            builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
            OptionDeclaration.parseOptionList(builder);
            if (!MessageDeclaration.parseMessageBlock(builder, true)) {
                builder.error("group.block.expected");
            }
            messageMarker.done(GROUP_DECL);

        } else {
            PsiBuilder.Marker messageMarker = builder.mark();
            builder.match(PROTO3_FIELD_LABELS);
            parseType(builder);
            builder.match(IK, "identifier.expected");
            //builder.matchAs(IK, NAME, "identifier.expected");
            builder.match(EQUAL, "equal.expected");
            builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
            OptionDeclaration.parseOptionList(builder);
            builder.match(SEMICOLON, "semicolon.expected");
            messageMarker.done(FIELD_DECL);
        }
        return true;
    }

    //done
    public static boolean parseType(PbPatchedPsiBuilder builder) {
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
