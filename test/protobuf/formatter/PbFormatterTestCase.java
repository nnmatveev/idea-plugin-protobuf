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

public class PbFormatterTestCase extends LightCodeInsightFixtureTestCase {
    protected CodeStyleSettings myTempSettings;

    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() + TestPath.FORMATTER_TEST_DIR;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Assert.assertNull(myTempSettings);
        CodeStyleSettings settings = CodeStyleSettingsManager.getSettings(getProject());
        myTempSettings = settings.clone();
        CodeStyleSettings.IndentOptions indentOptions = myTempSettings.getIndentOptions(ProtobufFileType.PROTOBUF_FILE_TYPE);
        Assert.assertNotSame(indentOptions, settings.OTHER_INDENT_OPTIONS);
        indentOptions.INDENT_SIZE = 4;
        indentOptions.TAB_SIZE = 4;
        indentOptions.CONTINUATION_INDENT_SIZE = 8;
        //myTempSettings.CLASS_COUNT_TO_USE_IMPORT_ON_DEMAND = 3;
        CodeStyleSettingsManager.getInstance(getProject()).setTemporarySettings(myTempSettings);


        doTest(getTestName(true).replace('$', '/') + ".test");
    }

    protected void doTest(String fileName) {
        try {
            final Pair<String, String> testMaterial = PbTestUtil.getSimpleTestMaterialsFromFile(getBasePath() + fileName);
            final PsiFile psiFile = PbTestUtil.createPseudoProtoFile(getProject(), fileName, testMaterial.getFirst());
            final TextRange myTextRange = psiFile.getTextRange();
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                public void run() {
                    CodeStyleManager.getInstance(psiFile.getProject()).reformatText(psiFile, myTextRange.getStartOffset(), myTextRange.getEndOffset());
                }
            });                                    
            assertEquals(StringUtil.convertLineSeparators(testMaterial.getSecond()), psiFile.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void tearDown() throws Exception {
        final CodeStyleSettingsManager manager = CodeStyleSettingsManager.getInstance(getProject());
        myTempSettings.getIndentOptions(ProtobufFileType.PROTOBUF_FILE_TYPE).INDENT_SIZE = 200;
        myTempSettings.getIndentOptions(ProtobufFileType.PROTOBUF_FILE_TYPE).CONTINUATION_INDENT_SIZE = 200;
        myTempSettings.getIndentOptions(ProtobufFileType.PROTOBUF_FILE_TYPE).TAB_SIZE = 200;
        //myTempSettings.CLASS_COUNT_TO_USE_IMPORT_ON_DEMAND = 5;
        manager.dropTemporarySettings();
        myTempSettings = null;

        super.tearDown();
    }


    /*public void testField1() {}        
    public void testFileSpacing1() {}
    public void testFileSpacing2() {}
    public void testBlockSpacing1() {}
    public void testBlockSpacing2() {}*/
    public void testOptionList1() {}

}
