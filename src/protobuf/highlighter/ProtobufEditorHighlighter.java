package protobuf.highlighting;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LexerEditorHighlighter;

/**
 * author: Nikolay Matveev
 * Date: Mar 7, 2010
 */
public class ProtobufEditorHighlighter extends LexerEditorHighlighter {
    public ProtobufEditorHighlighter(EditorColorsScheme scheme) {
        super(new ProtobufSyntaxHighlighter(), scheme);
  }
}
