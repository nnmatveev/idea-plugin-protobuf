package protobuf.lang.parser.parsing.statements;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.parsing.ReferenceElement;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 * Date: Mar 9, 2010
 */

//  grammar - ok
//  PbImportDef ::= 'import' STRING_LITERAL ';'

//done    
public class ImportStatement implements PbElementTypes {
    public static boolean parse(PbPatchedPsiBuilder builder) {
        if(!builder.compareToken(IMPORT)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match(IMPORT);
        ReferenceElement.parseForImport(builder);        
        builder.match(SEMICOLON,"semicolon.expected");
        marker.done(IMPORT_DECL);
        return true;                
  }
}
