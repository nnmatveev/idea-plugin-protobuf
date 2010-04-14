import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import protobuf.PbPathTest;
import protobuf.findUsages.PbFindUsagesTestCase;
import protobuf.formatter.PbEnterActionTestCase;
import protobuf.formatter.PbReformatTestCase;
import protobuf.lang.lexer.PbLexerTestCase;
import protobuf.lang.parser.PbParserTestCase;
import protobuf.lang.resolve.PbResolveTestCase;
import protobuf.refactoring.rename.PbRenameTestCase;
import protobuf.util.PbTestUtil;

import java.io.File;
import java.io.IOException;

/**
 * author: Nikolay Matveev
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PbPathTest.class,
        PbLexerTestCase.class,
        PbParserTestCase.class,
        PbResolveTestCase.class,
        PbReformatTestCase.class,
        PbEnterActionTestCase.class,
        PbFindUsagesTestCase.class,
        PbRenameTestCase.class
})
public class PbTestSuite extends TestCase {
    /*public void testTestDataPath() throws IOException {
        Assert.assertTrue("error with determining your test data path. Please, check your system settings.", PbTestUtil.getTestDataPath() != null);
        Assert.assertTrue("path to testdata folder is not existed",new File(PbTestUtil.getTestDataPath()).exists());
    }
    */
}
