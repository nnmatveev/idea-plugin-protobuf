package protobuf.formatter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.IncorrectOperationException;
import junit.framework.Assert;
import protobuf.file.ProtobufFileType;
import protobuf.util.PbTestUtil;
import protobuf.util.TestPath;

import java.io.IOException;

/**
 * author: Nikolay Matveev
 */

public class PbReformatTestCase extends PbFormatterTestCase {
    @Override
    protected String getBasePath() {
        return super.getBasePath() + "reformat/";
    }

    void doTest(){
        doTest(getTestName(true).replace('$', '/') + ".test");
    }

    protected void doTest(String fileName) {
        try {
            final Pair<String, String> testMaterial = PbTestUtil.getSimpleTestMaterialsFromFile(getBasePath() + fileName,true);
            final PsiFile psiFile = PbTestUtil.createPseudoProtoFile(getProject(), fileName, testMaterial.getFirst());
            final TextRange myTextRange = psiFile.getTextRange();
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                public void run() {
                    CodeStyleManager.getInstance(psiFile.getProject()).reformatText(psiFile, myTextRange.getStartOffset(), myTextRange.getEndOffset());
                }
            });
            System.out.println(DebugUtil.psiTreeToString(psiFile,true));
            assertEquals(StringUtil.convertLineSeparators(testMaterial.getSecond()), psiFile.getText());
        } catch (Exception e) {
            assertTrue("exception",false);
            e.printStackTrace();
        }
    }


    public void testField1() {doTest();}
    public void testFilespacing1() {doTest();}
    public void testFilespacing2() {doTest();}
    public void testBlockspacing1() {doTest();}
    public void testBlockspacing2() {doTest();}
    public void testOptionlist1() {doTest();}
    public void testOption1() {doTest();}

}
