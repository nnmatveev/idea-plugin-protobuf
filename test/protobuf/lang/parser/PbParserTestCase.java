package protobuf.lang.parser;

import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.text.StringUtil;
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
    protected void setUp() throws Exception {
        super.setUp();
        doTest();
    }

    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() + TestPath.PARSER_TEST_DIR;
    }

    public void doTest() {
        doTest(getTestName(true).replace('$', '/') + ".test");
    }

    protected void doTest(String fileName) {
        try {
            final Pair<String, String> testMaterial = PbTestUtil.getSimpleTestMaterialsFromFile(getBasePath() + fileName,true);
            final PsiFile psiFile = PbTestUtil.createPseudoProtoFile(getProject(), fileName, testMaterial.getFirst());
            final String psiTree = DebugUtil.psiToString(psiFile, true, false);
            assertEquals(StringUtil.convertLineSeparators(testMaterial.getSecond()), StringUtil.convertLineSeparators(psiTree.trim()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //file
    public void testFile1(){}

    //declaration
    public void testDeclaration$enum1(){}
    public void testDeclaration$enumconstant1(){}
    public void testDeclaration$extend1(){}
    public void testDeclaration$extensions1(){}
    public void testDeclaration$field1(){}
    public void testDeclaration$group1(){}
    public void testDeclaration$import1(){}
    public void testDeclaration$message1(){}
    public void testDeclaration$option1(){}
    public void testDeclaration$package1(){}
    public void testDeclaration$service1(){}
    public void testDeclaration$servicemethod1(){}

    //block
    public void testBlock$enum1(){}
    public void testBlock$extend1(){}
    public void testBlock$message1(){}
    public void testBlock$service1(){}
    public void testBlock$servicemethod1(){}

    //member
    public void testMember$fieldtype1(){}
    public void testMember$fieldlabel1(){}
    public void testMember$optionassigment1(){}
    public void testMember$optionlist1(){}
    public void testMember$optionrefseq1(){}

    //reference
    public void testReference$customoption1(){}
    public void testReference$customtype1(){}
    public void testReference$import1(){}
    public void testReference$package1(){}

}

