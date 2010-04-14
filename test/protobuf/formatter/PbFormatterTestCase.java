package protobuf.formatter;

import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import junit.framework.Assert;
import protobuf.file.ProtobufFileType;
import protobuf.util.PbTestUtil;

/**
 * author: Nikolay Matveev
 */

public abstract class PbFormatterTestCase extends LightCodeInsightFixtureTestCase {

    protected CodeStyleSettings myTempSettings;

    @Override
    protected String getBasePath() {
        return PbTestUtil.getTestDataPath() + "formatter/";
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
        CodeStyleSettingsManager.getInstance(getProject()).setTemporarySettings(myTempSettings);
    }


    @Override
    protected void tearDown() throws Exception {
        final CodeStyleSettingsManager manager = CodeStyleSettingsManager.getInstance(getProject());
        myTempSettings.getIndentOptions(ProtobufFileType.PROTOBUF_FILE_TYPE).INDENT_SIZE = 200;
        myTempSettings.getIndentOptions(ProtobufFileType.PROTOBUF_FILE_TYPE).CONTINUATION_INDENT_SIZE = 200;
        myTempSettings.getIndentOptions(ProtobufFileType.PROTOBUF_FILE_TYPE).TAB_SIZE = 200;        
        manager.dropTemporarySettings();
        myTempSettings = null;

        super.tearDown();
    }
}
