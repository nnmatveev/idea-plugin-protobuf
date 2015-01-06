package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author David Elkind
 */

public class OneofDeclaration implements PbElementTypes {
    public static boolean parse(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(ONEOF)) {
            return false;
        }
        PsiBuilder.Marker oneOfMarker = builder.mark();
        builder.match(ONEOF);
        builder.match(IK, "identifier.expected");
        if (!parseOneofBlock(builder)) {
            builder.error("oneof.block.expected");
        }
        oneOfMarker.done(ONEOF_DECL);
        return true;
    }

    public static boolean parseOneofBlock(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(OPEN_BLOCK)) {
            return false;
        }
        PsiBuilder.Marker blockMarker = builder.mark();
        builder.match(OPEN_BLOCK); //matching OPEN_BLOCK
        while (!builder.eof() && !builder.compareToken(CLOSE_BLOCK)) {
            if (!parseOneofMember(builder)) {
                builder.eatError("unexpected.token");
            }
        }
        builder.match(CLOSE_BLOCK, "close.block.expected");
        blockMarker.done(ONEOF_BLOCK);
        return true;
    }

    public static boolean parseOneofMember(PbPatchedPsiBuilder builder) {
        PsiBuilder.Marker memberMarker = builder.mark();
        if(!parseType(builder)) {
            memberMarker.drop();
            return false;
        }
        builder.match(IDENTIFIER, "identifier.expected");
        builder.match(EQUAL, "equal.expected");
        builder.matchAs(NUM_INT, VALUE, "num.integer.expected");
        builder.match(SEMICOLON, "semicolon.expected");
        memberMarker.done(ONEOF_MEMBER_DECL);
        return true;
    }

    public static boolean parseType(PbPatchedPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (builder.match(BUILT_IN_TYPES)) {
        } else if (ReferenceElement.parseForCustomType(builder)) {
        } else {
            marker.drop();
            return false;
        }
        marker.done(ONEOF_MEMBER_TYPE);
        return true;
    }
}
