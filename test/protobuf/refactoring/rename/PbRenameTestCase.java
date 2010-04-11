package protobuf.refactoring.rename;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import protobuf.file.ProtobufFileType;
import protobuf.util.PbTestUtil;

import java.io.IOException;

/**
 * author: Nikolay Matveev
 */

public class PbRenameTestCase extends LightCodeInsightFixtureTestCase {

    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() + "refactoring/rename/";
    }

    void doTest() throws Throwable {
    }

    protected void doTest(String fileName) throws Throwable {
        final Pair<String, String> testMaterial = PbTestUtil.getSimpleTestMaterialsFromFile(getBasePath() + fileName, true);
        myFixture.configureByText(ProtobufFileType.PROTOBUF_FILE_TYPE, testMaterial.getFirst());
        myFixture.renameElementAtCaret("NewName");
        myFixture.checkResult(testMaterial.getSecond());
    }

    public void testMessage1() throws Throwable {
        doTest();
    }

    public void testGroup1() throws Throwable {
        doTest();
    }

    public void testField1() throws Throwable {
        doTest();
    }
}
