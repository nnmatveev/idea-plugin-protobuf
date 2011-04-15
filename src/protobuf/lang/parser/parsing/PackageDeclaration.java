package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder.Marker;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 */

public class PackageDeclaration implements PbElementTypes {
    public static boolean parse(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(PACKAGE)) {
            return false;
        }
        Marker outerMarker = builder.mark();
        builder.match(PACKAGE);
        if (!ReferenceElement.parseForPackage(builder)) {
            builder.error("identifier.expected");
        }
        builder.match(SEMICOLON, "semicolon.expected");
        outerMarker.done(PACKAGE_DECL);
        return true;
    }
}
