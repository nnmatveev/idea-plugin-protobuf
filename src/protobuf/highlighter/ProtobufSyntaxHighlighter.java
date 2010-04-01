package protobuf.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import protobuf.lang.ProtobufTokenTypes;
import protobuf.lang.lexer.ProtobufMergingLexer;

import java.util.HashMap;
import java.util.Map;

/**
 * author: Nikolay Matveev
 * Date: Mar 5, 2010
 */
public class ProtobufSyntaxHighlighter extends SyntaxHighlighterBase{

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<IElementType, TextAttributesKey>();

    static {
        fillMap(ATTRIBUTES, ProtobufTokenTypes.COMMENTS, DefaultHighlighter.LINE_COMMENT_ATTR_KEY);        
        fillMap(ATTRIBUTES,ProtobufTokenTypes.STRING_LITERALS, DefaultHighlighter.STRING_ATTR_KEY);
        fillMap(ATTRIBUTES,ProtobufTokenTypes.WRONG_STRING_LITERALS, DefaultHighlighter.WRONG_STRING_ATTR_KEY);
        fillMap(ATTRIBUTES,ProtobufTokenTypes.BAD_CHARACTERS, DefaultHighlighter.BAD_CHARACTER_ATTR_KEY);
        fillMap(ATTRIBUTES,ProtobufTokenTypes.NUMBERS, DefaultHighlighter.NUMBER_ATTR_KEY);
        fillMap(ATTRIBUTES,ProtobufTokenTypes.KEYWORDS, DefaultHighlighter.KEYWORD_ATTR_KEY);        
    }

    @NotNull
    public Lexer getHighlightingLexer() {        
        return new ProtobufMergingLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
        return pack(ATTRIBUTES.get(iElementType));
    }


}
