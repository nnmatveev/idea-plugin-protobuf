import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import protobuf.formatter.PbFormatterTestCase;
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
        PbFormatterTestCase.class})
public class PbTestSuite {
}
