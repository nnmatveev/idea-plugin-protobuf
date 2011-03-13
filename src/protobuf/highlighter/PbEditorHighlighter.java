package protobuf.highlighter;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LexerEditorHighlighter;

/**
 * author: Nikolay Matveev
 * Date: Mar 7, 2010
 */
public class PbEditorHighlighter extends LexerEditorHighlighter {
    public PbEditorHighlighter(EditorColorsScheme scheme) {
        super(new PbSyntaxHighlighter(), scheme);
  }
}
