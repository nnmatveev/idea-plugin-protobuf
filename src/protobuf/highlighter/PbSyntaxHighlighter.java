package protobuf.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.PbTokenTypes;
import protobuf.lang.lexer.PbMergingLexer;

import java.util.HashMap;
import java.util.Map;

/**
 * author: Nikolay Matveev
 * Date: Mar 5, 2010
 */
public class PbSyntaxHighlighter extends SyntaxHighlighterBase{

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

    static {
        fillMap(ATTRIBUTES, PbTokenTypes.COMMENTS, PbDefaultHighlighter.LINE_COMMENT_ATTR_KEY);
        fillMap(ATTRIBUTES, PbTokenTypes.STRING_LITERALS, PbDefaultHighlighter.STRING_ATTR_KEY);
        fillMap(ATTRIBUTES, PbTokenTypes.WRONG_STRING_LITERALS, PbDefaultHighlighter.WRONG_STRING_ATTR_KEY);
        fillMap(ATTRIBUTES, PbTokenTypes.BAD_CHARACTERS, PbDefaultHighlighter.BAD_CHARACTER_ATTR_KEY);
        fillMap(ATTRIBUTES, PbTokenTypes.NUMBERS, PbDefaultHighlighter.NUMBER_ATTR_KEY);
        fillMap(ATTRIBUTES, PbTokenTypes.KEYWORDS, PbDefaultHighlighter.KEYWORD_ATTR_KEY);
    }

    @NotNull
    public Lexer getHighlightingLexer() {        
        return new PbMergingLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        return pack(ATTRIBUTES.get(iElementType));
    }


}
