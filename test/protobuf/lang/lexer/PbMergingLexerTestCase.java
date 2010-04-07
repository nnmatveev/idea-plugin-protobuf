package protobuf.lang.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.application.PathManager;
import com.intellij.testFramework.LexerTestCase;

/**
 * author: Nikolay Matveev
 * Date: Apr 7, 2010
 */
public class PbMergingLexerTestCase extends PbLexerTestCase {
    @Override
    protected Lexer createLexer() {
        return new PbMergingLexer();
    }

    public void testMerging() {
        doFileTest("test", "");
    }

}
