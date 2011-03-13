package protobuf.formatter;

import com.intellij.openapi.util.Pair;
import protobuf.file.PbFileType;
import protobuf.util.PbTestUtil;

import java.io.IOException;

/**
 * @author Nikolay Matveev
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
            myFixture.configureByText(PbFileType.PROTOBUF_FILE_TYPE, testMaterial.getFirst());
            myFixture.type('\n');
            myFixture.checkResult(testMaterial.getSecond());
        } catch (IOException e) {
            assertTrue("exception",false);
            e.printStackTrace();
        }
    }

    public void testFile1() {doTest();}
    public void testBlock1() {doTest();}
    public void testDefault1() {doTest();}
}
