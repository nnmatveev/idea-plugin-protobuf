package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.parser.util.PatchedPsiBuilder;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 25, 2010
 */
public class ReferenceElement {
    public static boolean parseForImport(PatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        builder.match(STRING_LITERAL, "string.literal.expected");
        marker.done(IMPORT_REF_ELEMENT);
        return true;
    }

    public static boolean parseForCustomType(PatchedPsiBuilder builder) {
        if (!builder.compareToken(DOT) && !builder.compareToken(IK)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        /*if(builder.match(DOT)){
            marker.done(REFERENCE_ELEMENT);
            marker = marker.precede();
        } */
        builder.match(DOT);
        do {
            if (builder.match(IK, "identifier.expected")) {
                marker.done(REFERENCE_ELEMENT);
                marker = marker.precede();
            } else {
                marker.done(REFERENCE_ELEMENT);
                marker = marker.precede();
                break;
            }
        } while (builder.match(DOT));
        marker.drop();
        return true;
    }

    //todo [middle] now it is not works good 
    public static boolean parseForOptionName(PatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (!parseOptionNamePart(builder, marker)) {
            marker.drop();
            return false;
        }
        boolean isLastDropped = false;
        while (builder.match(DOT)) {
            if (!parseOptionNamePart(builder, marker)) {
                builder.error("identifier.or.open.parant.expected = identifier or '(' expected");
                //marker.drop();
                isLastDropped = true;
                break;
            }
        }
        //if (!isLastDropped) marker.drop();
        marker.done(REFERENCE_ELEMENT);
        return true;
    }


    private static boolean parseOptionNamePart(PatchedPsiBuilder builder, PsiBuilder.Marker marker) {
        if (builder.match(IK)) {
            //marker.done(REFERENCE_ELEMENT);
            //marker = marker.precede();
            return true;
        }
        if (!builder.match(OPEN_PARANT)) {
            return false;
        }
        if (builder.match(IK) || builder.compareToken(DOT)) {
            while (!builder.eof() && builder.match(DOT)) {
                if (!builder.match(IK, "identifier.expected")) {
                    break;
                }
            }
        } else {
            builder.error("identifier.or.dot.expected");
        }
        builder.match(CLOSE_PARANT, "close.parant.expected");
        //marker.done(REFERENCE_ELEMENT);
        //marker = marker.precede();
        return true;
    }

    public static boolean parseForPackage(PatchedPsiBuilder builder) {
        if (!builder.compareToken(IK)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match();
        marker.done(PACKAGE_REF_ELEMENT);
        marker = marker.precede();
        while (!builder.eof() && builder.compareToken(DOT)) {
            builder.match(DOT);
            if (!builder.match(IDENTIFIER, "identifier.expected")) {
                marker.done(PACKAGE_REF_ELEMENT);
                marker = marker.precede();
                break;
            }
            marker.done(PACKAGE_REF_ELEMENT);
            marker = marker.precede();
        }
        marker.drop();        
        return true;
    }

}
