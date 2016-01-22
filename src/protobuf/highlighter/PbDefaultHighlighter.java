package protobuf.highlighter;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

/**
 * @author Nikolay Matveev
 * Date: Mar 6, 2010
 */
public class PbDefaultHighlighter {

    public static TextAttributesKey TEXT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("default text", HighlighterColors.TEXT);
    public static TextAttributesKey LINE_COMMENT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("line comment", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static TextAttributesKey STRING_ATTR_KEY = TextAttributesKey.createTextAttributesKey("string", DefaultLanguageHighlighterColors.STRING);
    public static TextAttributesKey WRONG_STRING_ATTR_KEY = TextAttributesKey.createTextAttributesKey("wrong string", DefaultLanguageHighlighterColors.STRING);
    public static TextAttributesKey BAD_CHARACTER_ATTR_KEY = TextAttributesKey.createTextAttributesKey("bad character", HighlighterColors.BAD_CHARACTER);
    public static TextAttributesKey NUMBER_ATTR_KEY = TextAttributesKey.createTextAttributesKey("number", DefaultLanguageHighlighterColors.NUMBER);
    public static TextAttributesKey KEYWORD_ATTR_KEY = TextAttributesKey.createTextAttributesKey("keyword", DefaultLanguageHighlighterColors.KEYWORD);
    public static TextAttributesKey ENUM_CONSTANT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("enum constant", CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
    public static TextAttributesKey ERROR_INFO_ATTR_KEY = TextAttributesKey.createTextAttributesKey("error", CodeInsightColors.WRONG_REFERENCES_ATTRIBUTES);
}

