package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

import static protobuf.lang.PbElementTypes.*;

/**
 * @author Nikolay Matveev
 *         Date: Mar 25, 2010
 */
public class ReferenceElement {

    public static boolean parseForPackage(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(IK)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match();
        marker.done(PACKAGE_REF);
        marker = marker.precede();
        while (!builder.eof() && builder.compareToken(DOT)) {
            builder.match(DOT);
            if (!builder.match(IDENTIFIER, "identifier.expected")) {
                marker.done(PACKAGE_REF);
                marker = marker.precede();
                break;
            }
            marker.done(PACKAGE_REF);
            marker = marker.precede();
        }
        marker.drop();
        return true;
    }

    public static boolean parseForImport(PbPatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        builder.match(STRING_LITERAL, "string.literal.expected");
        marker.done(IMPORT_REF);
        return true;
    }

    public static boolean parseForCustomType(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(DOT) && !builder.compareToken(IK)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match(DOT);
        do {
            if (builder.match(IK, "identifier.expected")) {
                marker.done(CUSTOM_TYPE_REF);
                marker = marker.precede();
            } else {
                marker.done(CUSTOM_TYPE_REF);
                marker = marker.precede();
                break;
            }
        } while (builder.match(DOT));
        marker.drop();
        return true;
    }

    public static boolean parseForCustomOption(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_PARENTHESIS)) {
            return false;
        }
        PsiBuilder.Marker refSeqMarker = builder.mark();
        PsiBuilder.Marker refMarker = builder.mark();
        parseOptionPart(builder);
        while (!builder.eof() && builder.compareToken(DOT)) {
            builder.match(DOT);
            if (builder.compareToken(IK)) {
                //complete previous marker
                builder.match(IK);
                refMarker.done(OPTION_REF);
                refMarker = refMarker.precede();
            } else if (builder.compareToken(OPEN_PARENTHESIS)) {
                //drop previous precede marker and start new marker
                refMarker.drop();
                refMarker = builder.mark();
                parseOptionPart(builder);
            } else {
                builder.error("identifier.or.open.parenthesis.expected");
                break;
            }
        }
        refMarker.drop();
        refSeqMarker.done(OPTION_REF_SEQ);
        return true;
    }

    private static boolean parseOptionPart(PbPatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        builder.match(OPEN_PARENTHESIS);
        builder.match(DOT);
        builder.match(IK, "identifier.expected");
        while (!builder.eof() && builder.compareToken(DOT)) {
            marker.done(OPTION_REF);
            marker = marker.precede();
            builder.match(DOT);
            builder.match(IK, "identifier.expected");
        }
        builder.match(CLOSE_PARENTHESIS, "close.parenthesis.expected");
        marker.done(OPTION_REF);
        return true;
    }

}
