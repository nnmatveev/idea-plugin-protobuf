package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 */

public class OptionDeclaration implements PbElementTypes {

    public static boolean parseSeparateOption(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(OPTION)) {
            return false;
        }
        PsiBuilder.Marker optionAssignmentMarker = builder.mark();
        builder.match(OPTION);
        if (!parseOptionAssignment(builder)) {
            builder.error("option.assignment.expected");
        }
        builder.match(SEMICOLON, "semicolon.expected");
        optionAssignmentMarker.done(OPTION_ASSIGNMENT);
        return true;
    }

    public static boolean parseOptionList(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_BRACE)) {
            return false;
        }
        PsiBuilder.Marker optionsMarker = builder.mark();
        builder.match(OPEN_BRACE);
        do {
            PsiBuilder.Marker optionAssignmentMarker = builder.mark();
            if (parseOptionAssignment(builder)) {
                optionAssignmentMarker.done(OPTION_ASSIGNMENT);
            } else {
                optionAssignmentMarker.drop();
                builder.error("option.assignment.expected");
                break;
            }
        } while (!builder.eof() && builder.match(COMMA));
        builder.match(CLOSE_BRACE, "close.brace.expected");
        optionsMarker.done(OPTION_LIST);
        return true;
    }

    public static boolean parseOptionAssignment(PbPatchedPsiBuilder builder) {
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

    public static boolean parseOptionValue(PbPatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (builder.match(NUMBERS)) {
        } else if (builder.match(MINUS)) {
            if (builder.match(NUMBERS)) {
            } else if (builder.match("inf")) {
            } else if (builder.match("nan")) {
            } else {
                builder.error("number.expected");
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
