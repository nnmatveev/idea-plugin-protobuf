package protobuf.lang.parser;

import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import protobuf.util.PbTestUtil;
import protobuf.util.TestPath;

import java.io.IOException;

/**
 * author: Nikolay Matveev
 */

public class PbParserTestCase extends LightCodeInsightFixtureTestCase {

    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() + TestPath.PARSER_TEST_DIR;
    }

    public void doTest() {
        doTest(getTestName(true).replace('$', '/') + ".test");
    }

    protected void doTest(String fileName) {
        try {
            final Pair<String, String> testMaterial = PbTestUtil.getSimpleTestMaterialsFromFile(getBasePath() + fileName);
            final PsiFile psiFile = PbTestUtil.createPseudoProtoFile(getProject(), fileName, testMaterial.getFirst());
            final String psiTree = DebugUtil.psiToString(psiFile, true,false);
            assertEquals(testMaterial.getSecond(), psiTree.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //file
    public void testFile1() { doTest(); }
    
    //declarations
    public void testDeclaration$enum1() { doTest(); }
    public void testDeclaration$enumconstant1() { doTest(); }
    public void testDeclaration$extend1() { doTest(); }
    public void testDeclaration$extensions1() { doTest(); }
    public void testDeclaration$field1() { doTest(); }
    public void testDeclaration$group1() { doTest(); }
    public void testDeclaration$import1() { doTest(); }
    public void testDeclaration$message1() { doTest(); }
    public void testDeclaration$option1() { doTest(); }
    public void testDeclaration$package1() { doTest(); }
    public void testDeclaration$service1() { doTest(); }
    public void testDeclaration$servicemethod1() { doTest(); }

    //blocks
    public void testBlock$enum1() { doTest(); }
    public void testBlock$extend1() { doTest(); }
    public void testBlock$message1() { doTest(); }
    public void testBlock$service1() { doTest(); }
    public void testBlock$servicemethod1() { doTest(); }

    //members
    public void testMember$fieldtype1() { doTest(); }
    public void testMember$fieldlabel1() { doTest(); }
    public void testMember$optionassigment1() { doTest(); }
    public void testMember$optionlist1() { doTest(); }
    public void testMember$optionrefseq1() { doTest(); }

    //references
    public void testReference$customoption1() { doTest(); }
    public void testReference$customtype1() { doTest(); }
    public void testReference$import1() { doTest(); }
    public void testReference$package1() { doTest(); }

}

