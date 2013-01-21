package protobuf.highlighter;

import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;

import java.awt.*;

/**
 * @author Nikolay Matveev
 * Date: Mar 6, 2010
 */
public class PbDefaultHighlighter {

    static final TextAttributes TEXT_ATTR = new TextAttributes(new Color(0,0,0), null, null, null, Font.PLAIN);
    static final TextAttributes COMMENT_ATTR = SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes();    
    static final TextAttributes STRING_ATTR = SyntaxHighlighterColors.STRING.getDefaultAttributes();
    static final TextAttributes WRONG_STRING_ATTR = SyntaxHighlighterColors.STRING.getDefaultAttributes();
    static final TextAttributes NUMBER_ATTR = SyntaxHighlighterColors.NUMBER.getDefaultAttributes();
    static final TextAttributes BAD_CHARACTER_ATTR = SyntaxHighlighterColors.KEYWORD.getDefaultAttributes().clone();
    static final TextAttributes KEYWORD_ATTR = SyntaxHighlighterColors.KEYWORD.getDefaultAttributes().clone();
    static final TextAttributes ENUM_CONSTANT_ATTR = CodeInsightColors.STATIC_FINAL_FIELD_ATTRIBUTES.getDefaultAttributes().clone();
    static final TextAttributes ERROR_INFO_ATTR = CodeInsightColors.WRONG_REFERENCES_ATTRIBUTES.getDefaultAttributes().clone();

    static { // Apply attribute customizations.
        BAD_CHARACTER_ATTR.setForegroundColor(new Color(255,0,0));
        ENUM_CONSTANT_ATTR.setFontType(Font.BOLD);
    }

    public static TextAttributesKey TEXT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("default text", TEXT_ATTR);
    public static TextAttributesKey LINE_COMMENT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("line comment", COMMENT_ATTR);
    public static TextAttributesKey STRING_ATTR_KEY = TextAttributesKey.createTextAttributesKey("string", STRING_ATTR);
    public static TextAttributesKey WRONG_STRING_ATTR_KEY = TextAttributesKey.createTextAttributesKey("wrong string", WRONG_STRING_ATTR);
    public static TextAttributesKey BAD_CHARACTER_ATTR_KEY = TextAttributesKey.createTextAttributesKey("bad character", BAD_CHARACTER_ATTR);
    public static TextAttributesKey NUMBER_ATTR_KEY = TextAttributesKey.createTextAttributesKey("number", NUMBER_ATTR);
    public static TextAttributesKey KEYWORD_ATTR_KEY = TextAttributesKey.createTextAttributesKey("keyword", KEYWORD_ATTR);
    public static TextAttributesKey ENUM_CONSTANT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("enum constant", ENUM_CONSTANT_ATTR);
    public static TextAttributesKey ERROR_INFO_ATTR_KEY = TextAttributesKey.createTextAttributesKey("error", ERROR_INFO_ATTR);
    
}

