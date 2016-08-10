package tk.skuro.idea.orgmode.parser;

import com.intellij.testFramework.ParsingTestCase;

public class ParserTest extends ParsingTestCase {

    public ParserTest() {
        super("", "org", new OrgParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/parsing";
    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }


}
