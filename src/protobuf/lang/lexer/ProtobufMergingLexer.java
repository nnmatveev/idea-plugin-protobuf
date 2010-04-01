package protobuf.lang.lexer;

import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;

import static protobuf.lang.ProtobufElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 21, 2010
 */

public class ProtobufMergingLexer extends MergingLexerAdapter {
    private static final TokenSet tokensToMerge = TokenSet.orSet(WHITE_SPACES,COMMENTS);

    public ProtobufMergingLexer(){
        super(new ProtobufFlexLexer(),tokensToMerge);
    }
}
