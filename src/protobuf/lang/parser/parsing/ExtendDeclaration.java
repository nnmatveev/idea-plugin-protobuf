package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 * Date: Mar 10, 2010
 */

//  grammar - ok
//  PbExtendDef ::= 'extend' userDefinedType '{' extendBlock '}'
//  extendBlock ::= (messageField ';')*

    //done
public class ExtendDeclaration implements PbElementTypes {
    public static boolean parse(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(EXTEND)) {
            return false;
        }
        PsiBuilder.Marker extendMarker = builder.mark();
        builder.match(EXTEND);
        if (!ReferenceElement.parseForCustomType(builder)) {
            builder.error("type.expected");
        }
        if (!parseExtendBlock(builder)) {
            builder.error("open.block.expected");
        }
        extendMarker.done(EXTEND_DECL);
        return true;
    }

    //done
    public static boolean parseExtendBlock(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_BLOCK)) {
            return false;
        }
        PsiBuilder.Marker extendBlockMarker = builder.mark();
        builder.match(OPEN_BLOCK);
        while(!builder.eof() && !builder.compareToken(CLOSE_BLOCK)){
            if(!FieldDeclaration.parse(builder)){
                builder.eatError("unexpected.token");
            }
        }
        builder.match(CLOSE_BLOCK,"close.block.expected");
        extendBlockMarker.done(EXTEND_BLOCK);
        return true;
    }


}