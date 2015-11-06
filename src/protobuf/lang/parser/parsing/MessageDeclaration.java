package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * @author Nikolay Matveev
 */

public class MessageDeclaration implements PbElementTypes {

    public static boolean parse(PbPatchedPsiBuilder builder, boolean proto3Syntax) {
        if (!builder.compareToken(MESSAGE)) {
            return false;
        }
        PsiBuilder.Marker messageMarker = builder.mark();
        builder.match(MESSAGE);
        //builder.matchAs(IK,NAME,"identifier.expected");
        builder.match(IK, "identifier.expected");
        if (!parseMessageBlock(builder, proto3Syntax)) {
            builder.error("message.block.expected");
        }
        messageMarker.done(MESSAGE_DECL);
        return true;
    }

    public static boolean parseMessageBlock(PbPatchedPsiBuilder builder, boolean isProto3Syntax) {
        if (!builder.compareToken(OPEN_BLOCK)) {
            return false;
        }
        PsiBuilder.Marker blockMarker = builder.mark();
        builder.match(OPEN_BLOCK); //matching OPEN_BLOCK
        while (!builder.eof() && !builder.compareToken(CLOSE_BLOCK)) {
            if (!parseMessageMember(builder, isProto3Syntax)) {
                builder.eatError("unexpected.token");
            }
        }
        builder.match(CLOSE_BLOCK, "close.block.expected");
        blockMarker.done(MESSAGE_BLOCK);
        return true;
    }

    public static boolean parseMessageMember(PbPatchedPsiBuilder builder, boolean proto3Syntax) {
        //PsiBuilder.Marker statementMarker = builder.mark();
        if (builder.match(SEMICOLON)) {
        } else if (MessageDeclaration.parse(builder, proto3Syntax)) {

        } else if (OneofDeclaration.parse(builder)) {

        } else if (EnumDeclaration.parse(builder)) {

        } else if (ExtendDeclaration.parse(builder, proto3Syntax)) {

        } else if (OptionDeclaration.parseSeparateOption(builder)) {

        } else if (parseExtensions(builder)) {

        } else if (proto3Syntax ? Proto3FieldDeclaration.parse(builder) : FieldDeclaration.parse(builder)) {
        } else {
            //statementMarker.drop();
            return false;
        }
        //statementMarker.done(MESSAGE_STATEMENT);
        return true;
    }

    public static boolean parseExtensions(PbPatchedPsiBuilder builder) {
        if (!builder.compareToken(EXTENSIONS)) {
            return false;
        }
        PsiBuilder.Marker extMarker = builder.mark();
        builder.match(EXTENSIONS);
        PsiBuilder.Marker rangeMarker;
        boolean isRangeMatched;
        do {
            isRangeMatched = false;
            rangeMarker = builder.mark();
            if (!builder.match(NUM_INT, "num.integer.expected")) {
                rangeMarker.drop();
                break;
            }
            builder.match(NUM_INT);
            if (!builder.match(TO)) {
            } else if (!builder.match(NUM_INT) && !builder.match(MAX)) {
            } else {
                isRangeMatched = true;
            }
            rangeMarker.done(EXTENSIONS_RANGE);
        } while (!builder.eof() && builder.match(COMMA));

        if (isRangeMatched) builder.match(SEMICOLON, "semicolon.expected");
        extMarker.done(EXTENSIONS_DECL);
        return true;
    }
}
