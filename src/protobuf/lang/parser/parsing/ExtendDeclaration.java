package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 */

public class ExtendDeclaration implements PbElementTypes {

    public static boolean parse(PbPatchedPsiBuilder builder, boolean proto3Syntax) {
        if (!builder.compareToken(EXTEND)) {
            return false;
        }
        PsiBuilder.Marker extendMarker = builder.mark();
        builder.match(EXTEND);
        if (!ReferenceElement.parseForCustomType(builder)) {
            builder.error("type.expected");
        }
        if (!parseExtendBlock(builder, proto3Syntax)) {
            builder.error("open.block.expected");
        }
        extendMarker.done(EXTEND_DECL);
        return true;
    }

    public static boolean parseExtendBlock(PbPatchedPsiBuilder builder, boolean proto3Syntax) {
        if (!builder.compareToken(OPEN_BLOCK)) {
            return false;
        }
        PsiBuilder.Marker extendBlockMarker = builder.mark();
        builder.match(OPEN_BLOCK);
        while (!builder.eof() && !builder.compareToken(CLOSE_BLOCK)) {
            if (!parseField(builder, proto3Syntax)) {
                builder.eatError("unexpected.token");
            }
        }
        builder.match(CLOSE_BLOCK, "close.block.expected");
        extendBlockMarker.done(EXTEND_BLOCK);
        return true;
    }

    private static boolean parseField(PbPatchedPsiBuilder builder, boolean proto3Syntax) {
        return proto3Syntax ? Proto3FieldDeclaration.parse(builder) : FieldDeclaration.parse(builder);
    }
}