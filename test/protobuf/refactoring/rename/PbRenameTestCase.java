package protobuf.refactoring.rename;

import com.intellij.openapi.util.Pair;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import protobuf.file.PbFileType;
import protobuf.util.PbTestUtil;

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
        myFixture.configureByText(PbFileType.PROTOBUF_FILE_TYPE, testMaterial.getFirst());
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
