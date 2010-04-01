package protobuf.lang.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * author: Nikolay Matveev
 * Date: Mar 7, 2010
 */
public class ProtobufFlexLexer extends FlexAdapter {
    public ProtobufFlexLexer() {
        super(new _ProtobufLexer((Reader) null));
    }
}
