package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 */

public class ImportDeclaration implements PbElementTypes {
    public static boolean parse(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(IMPORT)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match(IMPORT);
        ReferenceElement.parseForImport(builder);
        builder.match(SEMICOLON, "semicolon.expected");
        marker.done(IMPORT_DECL);
        return true;
    }
}
