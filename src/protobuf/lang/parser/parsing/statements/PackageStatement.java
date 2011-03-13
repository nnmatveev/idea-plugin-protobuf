package protobuf.lang.parser.parsing.statements;

import com.intellij.lang.PsiBuilder.Marker;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.parsing.ReferenceElement;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 * Date: Mar 9, 2010
 */

//  grammar - ok
//  PbPackageDef ::= 'package' IDENTIFIER('.'IDENTIFIER)* ';'

// done
public class PackageStatement implements PbElementTypes {
    public static boolean parse(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(PACKAGE)) {
            return false;
        }
        Marker outerMarker = builder.mark();
        builder.match(PACKAGE);
        if(!ReferenceElement.parseForPackage(builder)) {
            builder.error("identifier.expected");
        }
        builder.match(SEMICOLON,"semicolon.expected");
        outerMarker.done(PACKAGE_DECL);
        return true;
    }
}
