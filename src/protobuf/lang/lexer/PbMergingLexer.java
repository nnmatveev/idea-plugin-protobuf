package protobuf.lang.lexer;

import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;

import static protobuf.lang.PbElementTypes.*;

/**
 * author: Nikolay Matveev
 * Date: Mar 21, 2010
 */

public class PbMergingLexer extends MergingLexerAdapter {
    private static final TokenSet tokensToMerge = TokenSet.create(WHITE_SPACE);

    public PbMergingLexer(){
        super(new PbFlexLexer(),tokensToMerge);        
    }
}
