package tk.skuro.idea.orgmode.parser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexerTest {

    private OrgLexer lexer;

    @Before
    public void setup() {
        lexer = new OrgLexer();
    }

    @Test
    public void canReadComments() {
        final String comment = "# I'm a comment";
        lexer.start(comment);

        assertEquals("Comment not properly parsed", OrgTokenTypes.COMMENT, lexer.getTokenType());
    }

    @Test
    public void canReadCommentsWithHeadingWhitespaces() {
        final String comment = "  # I'm a comment";
        lexer.start(comment);

        assertEquals("Comment not properly parsed", OrgTokenTypes.COMMENT, lexer.getTokenType());
    }

    @Test
    public void canReadOutlines() {
        final String outlines =
                "* I'm an outline\n" +
                "** I'm a second outline";
        lexer.start(outlines);
        assertEquals("Outline not properly parsed", OrgTokenTypes.OUTLINE, lexer.getTokenType());

        eatWhitespace();

        lexer.advance();
        assertEquals("Outline not properly parsed", OrgTokenTypes.OUTLINE, lexer.getTokenType());
    }

    @Test
    public void canReadBlocks() {
        final String block =
                "#+BEGIN_SRC\n" +
                "(defn foobar)\n" +
                "#+END_SRC";

        lexer.start(block);
        assertEquals("Block start not properly parsed", OrgTokenTypes.BLOCK_DELIMITER, lexer.getTokenType());

        eatWhitespace();

        lexer.advance();
        assertEquals("Block content not properly parsed", OrgTokenTypes.BLOCK_CONTENT, lexer.getTokenType());

        eatBlockCharacters();

        lexer.advance();
        assertEquals("Block end not properly parsed", OrgTokenTypes.BLOCK_DELIMITER, lexer.getTokenType());
    }

    private void eatWhitespace() {
        lexer.advance();
    }

    /**
     * Eats all characters inside a block, including one trailing whitespace
     */
    private void eatBlockCharacters() {
        while(lexer.getTokenType() == OrgTokenTypes.BLOCK_CONTENT) {
            lexer.advance();
        }
    }

}
