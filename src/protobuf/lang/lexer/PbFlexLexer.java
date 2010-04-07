package protobuf.lang.lexer;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

/**
 * author: Nikolay Matveev
 * Date: Mar 7, 2010
 */
public class PbFlexLexer extends FlexAdapter {
    public PbFlexLexer() {
        super(new _ProtobufLexer((Reader) null));
    }
}
