package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.parser.util.PatchedPsiBuilder;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 25, 2010
 */
public class ReferenceElement {

    public static boolean parseForPackage(PatchedPsiBuilder builder) {
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

    public static boolean parseForImport(PatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        builder.match(STRING_LITERAL, "string.literal.expected");
        marker.done(IMPORT_REF);
        return true;
    }

    public static boolean parseForCustomType(PatchedPsiBuilder builder) {
        if (!builder.compareToken(DOT) && !builder.compareToken(IK)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match(DOT);
        do {
            if (builder.match(IK, "identifier.expected")) {
                marker.done(TYPE_REF);
                marker = marker.precede();
            } else {
                marker.done(TYPE_REF);
                marker = marker.precede();
                break;
            }
        } while (builder.match(DOT));
        marker.drop();
        return true;
    }

    public static boolean parseForCustomOption(PatchedPsiBuilder builder) {        
        if (!builder.compareToken(OPEN_PARANT)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        parseOptionPart(builder);
        while (!builder.eof() && builder.compareToken(DOT)) {
            builder.match(DOT);
            if (!parseOptionPart(builder)) {
                builder.error("identifier.or.open.parant.expected");
                marker.done(OPTION_REF);
                marker = marker.precede();
                break;
            }
            marker.done(OPTION_REF);
            marker = marker.precede();
        }
        marker.drop();
        return true;
    }

    private static boolean parseOptionPart(PatchedPsiBuilder builder) {
        if (builder.compareToken(IK)) {
            PsiBuilder.Marker marker = builder.mark();
            builder.match(IK);
            marker.done(OPTION_REF);
            return true;
        }
        if (!builder.compareToken(OPEN_PARANT)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match(OPEN_PARANT);
        builder.match(DOT);
        builder.match(IK, "identifier.expected");
        while (!builder.eof() && builder.compareToken(DOT)) {
            marker.done(OPTION_REF);
            marker = marker.precede();
            builder.match(DOT);
            builder.match(IK, "identifier.expected");            
        }
        builder.match(CLOSE_PARANT, "close.parant.expected");
        marker.done(OPTION_REF);
        return true;
    }

}
