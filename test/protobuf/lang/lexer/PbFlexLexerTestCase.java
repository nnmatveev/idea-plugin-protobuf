package protobuf.lang.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;
import protobuf.util.PbTestUtil;

/**
 * author: Nikolay Matveev
 * Date: Apr 7, 2010
 */
public class PbFlexLexerTestCase extends PbLexerTestCase {
    @Override
    protected Lexer createLexer() {
        return new PbFlexLexer();
    }

    public void testCommon(){
        doFileTest("test","flex/");
    }

    public void testComments(){
        doFileTest("test","flex/");
    }

    public void testNumbers(){
        doFileTest("test","flex/");
    }

    public void testKeywords(){
        doFileTest("test","flex/");
    }

    public void testStrings(){
        doFileTest("test","flex/");
    }
}
