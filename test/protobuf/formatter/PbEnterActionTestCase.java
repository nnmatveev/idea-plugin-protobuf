package protobuf.formatter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import protobuf.file.ProtobufFileType;
import protobuf.util.PbTestUtil;
import protobuf.util.TestPath;

import java.io.IOException;

/**
 * author: Nikolay Matveev
 */

public class PbEnterActionTestCase extends PbFormatterTestCase {
    @Override
    protected String getBasePath() {
        return super.getBasePath() + "enter_action/";
    }

    void doTest() {
        doTest(getTestName(true).replace('$', '/') + ".test");
    }

    void doTest(String fileName) {
        try {
            final Pair<String, String> testMaterial = PbTestUtil.getSimpleTestMaterialsFromFile(getBasePath() + fileName,true);
            myFixture.configureByText(ProtobufFileType.PROTOBUF_FILE_TYPE, testMaterial.getFirst());
            myFixture.type('\n');
            System.out.println(myFixture.getFile().getText());
            myFixture.checkResult(testMaterial.getSecond());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFile1() {doTest();}
    public void testBlock1() {doTest();}
    public void testDefault1() {doTest();}
}
