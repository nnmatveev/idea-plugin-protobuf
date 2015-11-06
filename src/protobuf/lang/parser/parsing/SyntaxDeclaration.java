package protobuf.lang.parser.parsing;

import com.intellij.lang.PsiBuilder;
import protobuf.lang.PbElementTypes;
import protobuf.lang.parser.util.PbPatchedPsiBuilder;

/**
 * Protobuf <code>syntax</code> element representing a declaration like:
 * <code>
 *     syntax = "proto2";
 * </code>
 * @author Travis Cripps
 */
public class SyntaxDeclaration implements PbElementTypes {

    public static boolean parse(PbPatchedPsiBuilder builder, CompilationUnit.Context parsingContext) {
        if (!builder.compareToken(SYNTAX)) {
            return false;
        }
        PsiBuilder.Marker marker = builder.mark();
        builder.match(SYNTAX);
        builder.match(EQUAL, "equal.expected");

        PsiBuilder.Marker valueMarker = builder.mark();
        parsingContext.setSyntax(builder.getTokenText());
        if (builder.match(STRING_LITERALS)) {
            valueMarker.done(VALUE);
        } else {
            builder.error("value.expected");
            valueMarker.drop();
        }

        builder.match(SEMICOLON, "semicolon.expected");
        marker.done(SYNTAX);

        return true;
    }

}
