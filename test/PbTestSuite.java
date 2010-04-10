import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import protobuf.findUsages.PbFindUsagesTestCase;
import protobuf.formatter.PbEnterActionTestCase;
import protobuf.formatter.PbReformatTestCase;
import protobuf.lang.lexer.PbLexerTestCase;
import protobuf.lang.parser.PbParserTestCase;
import protobuf.lang.resolve.PbResolveTestCase;

/**
 * author: Nikolay Matveev
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PbLexerTestCase.class,
        PbParserTestCase.class,
        PbResolveTestCase.class,
        PbReformatTestCase.class,
        PbEnterActionTestCase.class,
        PbFindUsagesTestCase.class
})
public class PbTestSuite {
}
