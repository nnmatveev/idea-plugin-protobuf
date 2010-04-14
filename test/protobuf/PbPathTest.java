package protobuf;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import junit.framework.Assert;
import junit.framework.TestCase;
import protobuf.util.PbTestUtil;

import java.io.File;
import java.io.IOException;

/**
 * author: Nikolay Matveev
 */

public class PbPathTest extends LightCodeInsightFixtureTestCase {

    public void testTestDataPath() throws IOException {        
        Assert.assertTrue("error with determining your test data path. Please, check your system settings.",PbTestUtil.getTestDataPath() != null);        
        Assert.assertTrue("path to testdata folder is not existed",new File(PbTestUtil.getTestDataPath()).exists());
    }
}
