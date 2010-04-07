package protobuf.lang.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.LexerTestCase;
import com.intellij.testFramework.UsefulTestCase;
import org.jetbrains.annotations.NonNls;
import protobuf.util.PbTestUtil;

import java.io.File;
import java.io.IOException;

/**
 * author: Nikolay Matveev
 * Date: Apr 7, 2010
 */
public abstract class PbLexerTestCase extends UsefulTestCase {

    protected void doTest(@NonNls String text,String path) {
        Lexer lexer = createLexer();
        lexer.start(text);
        String result = "";
        while (true) {
            IElementType tokenType = lexer.getTokenType();
            if (tokenType == null) {
                break;
            }
            String tokenText = getTokenText(lexer);
            String tokenTypeName = tokenType.toString();
            String line = tokenTypeName + "#" + tokenText + "\n";
            result += line;
            lexer.advance();
        }
        assertSameLinesWithFile(PbTestUtil.getTestDataPath() + "/lexer/" + path +  getTestName(true) + ".txt", result);
    }

    protected void doFileTest(@NonNls String fileExt, String path) {
        String fileName = PbTestUtil.getTestDataPath() + "/lexer/"  + path + getTestName(true) + "." + fileExt;
        String text = "";
        try {
            text = new String(FileUtil.loadFileText(new File(fileName))).trim();
        }
        catch (IOException e) {
            fail("can't load file " + fileName + ": " + e.getMessage());
        }
        doTest(text,path);
    }

    private static String getTokenText(Lexer lexer) {
        String text = lexer.getBufferSequence().subSequence(lexer.getTokenStart(), lexer.getTokenEnd()).toString();
        text = StringUtil.replace(text, "\r", "\\r");
        text = StringUtil.replace(text, "\n", "\\n");
        text = StringUtil.replace(text, "\t", "\\t");
        text = StringUtil.replace(text, "\f", "\\f");
        text = StringUtil.replace(text, " ", "\\_");
        return text;
    }

    protected abstract Lexer createLexer();
}
