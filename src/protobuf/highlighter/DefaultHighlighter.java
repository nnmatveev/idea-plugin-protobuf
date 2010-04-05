package protobuf.highlighter;

import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;

import java.awt.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 6, 2010
 */
public class DefaultHighlighter{

    //static final String TEXT_ID = "program text";
    static final String LINE_COMMENT_ID = "line comment";
    static final String C_STYLE_COMMENT_ID = "line comment";
    static final String STRING_ID = "string";
    static final String WRONG_STRING_ID = "wrong string";
    static final String BAD_CHARACTER_ID = "bad character";
    static final String NUMBER_ID = "number";
    static final String KEYWORD_ID = "keyword";
    //static final String ENUM_CONSTANT_ID = "enum constant";

    static final TextAttributes LINE_COMMENT_ATTR = SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes();
    static final TextAttributes C_STYLE_COMMENT_ATTR = SyntaxHighlighterColors.JAVA_BLOCK_COMMENT.getDefaultAttributes();
    static final TextAttributes STRING_ATTR = SyntaxHighlighterColors.STRING.getDefaultAttributes();
    static final TextAttributes WRONG_STRING_ATTR = SyntaxHighlighterColors.STRING.getDefaultAttributes();
    static final TextAttributes NUMBER_ATTR = SyntaxHighlighterColors.NUMBER.getDefaultAttributes();
    static final TextAttributes BAD_CHARACTER_ATTR = SyntaxHighlighterColors.KEYWORD.getDefaultAttributes().clone();
    static{
        BAD_CHARACTER_ATTR.setForegroundColor(new Color(255,0,0));
    }
    static final TextAttributes KEYWORD_ATTR = SyntaxHighlighterColors.KEYWORD.getDefaultAttributes().clone();
    static{
        KEYWORD_ATTR.setForegroundColor(new Color(0,0,67));
        KEYWORD_ATTR.setFontType(Font.BOLD);
    }
    static final TextAttributes ENUM_CONSTANT_ATTR = new TextAttributes();
    static{
        ENUM_CONSTANT_ATTR.setForegroundColor(new Color(102,14,122));
        ENUM_CONSTANT_ATTR.setFontType(Font.BOLD);
    }
    static final TextAttributes TEXT_ATTR = new TextAttributes();
    static{
        TEXT_ATTR.setForegroundColor(new Color(0,0,0));
        //todo [low] regular text
        
        TEXT_ATTR.setFontType(Font.ITALIC);
    }
    static final TextAttributes ERROR_INFO_ATTR = new TextAttributes();
    static{
        ERROR_INFO_ATTR.setForegroundColor(new Color(255,0,0));
        ERROR_INFO_ATTR.setFontType(Font.PLAIN);
    }

    


    public static TextAttributesKey LINE_COMMENT_ATTR_KEY = TextAttributesKey.createTextAttributesKey(LINE_COMMENT_ID, LINE_COMMENT_ATTR);
    public static TextAttributesKey C_STYLE_COMMENT_ATTR_KEY = TextAttributesKey.createTextAttributesKey(C_STYLE_COMMENT_ID, C_STYLE_COMMENT_ATTR);
    public static TextAttributesKey STRING_ATTR_KEY = TextAttributesKey.createTextAttributesKey(STRING_ID, STRING_ATTR);
    public static TextAttributesKey WRONG_STRING_ATTR_KEY = TextAttributesKey.createTextAttributesKey(WRONG_STRING_ID, WRONG_STRING_ATTR);
    public static TextAttributesKey BAD_CHARACTER_ATTR_KEY = TextAttributesKey.createTextAttributesKey(BAD_CHARACTER_ID, BAD_CHARACTER_ATTR);
    public static TextAttributesKey NUMBER_ATTR_KEY = TextAttributesKey.createTextAttributesKey(NUMBER_ID,NUMBER_ATTR);
    public static TextAttributesKey KEYWORD_ATTR_KEY = TextAttributesKey.createTextAttributesKey(KEYWORD_ID,KEYWORD_ATTR);
    public static TextAttributesKey ENUM_CONSTANT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("enum constant",ENUM_CONSTANT_ATTR);
    public static TextAttributesKey TEXT_ATTR_KEY = TextAttributesKey.createTextAttributesKey("program text",TEXT_ATTR);
    public static TextAttributesKey ERROR_INFO_ATTR_KEY = TextAttributesKey.createTextAttributesKey("error",ERROR_INFO_ATTR);
}

