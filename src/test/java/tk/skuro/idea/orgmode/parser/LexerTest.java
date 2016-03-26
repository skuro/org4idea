package tk.skuro.idea.orgmode.parser;

import com.intellij.lexer.LexerPosition;
import org.junit.Before;
import org.junit.Ignore;
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
        eatWhitespace();

        lexer.advance();
        assertEquals("Block end not properly parsed", OrgTokenTypes.BLOCK_DELIMITER, lexer.getTokenType());
    }

    @Test
    public void canReadKeyword(){
        final String keyword = "#+FOOBAR: foobar all the way down";

        lexer.start(keyword);
        assertEquals("Keyword not properly parsed", OrgTokenTypes.KEYWORD, lexer.getTokenType());
    }

    @Test
    public void canReadKeywordWithHeadingWhitespace(){
        final String keyword = "   #+FOOBAR: foobar all the way down";

        lexer.start(keyword);
        assertEquals("Keyword not properly parsed", OrgTokenTypes.KEYWORD, lexer.getTokenType());
    }

    @Test
    public void canReadUnderline(){
        final String underlined = "_Ima underline text_";

        lexer.start(underlined);
        assertEquals("Underline not properly parsed", OrgTokenTypes.UNDERLINE, lexer.getTokenType());
        assertEquals("Underline not properly parsed", underlined, lexer.getTokenText());
    }

    @Ignore("Still need to regenerate the lexer")
    @Test
    public void canReadBold(){
        final String underlined = "*Ima bold text*";

        lexer.start(underlined);
        assertEquals("Underline not properly parsed", OrgTokenTypes.UNDERLINE, lexer.getTokenType());
        assertEquals("Underline not properly parsed", underlined, lexer.getTokenText());
    }

    private void eatWhitespace() {
        lexer.advance();
    }

    /**
     * Eats all characters inside a block
     */
    private void eatBlockCharacters() {
        LexerPosition previous = lexer.getCurrentPosition();
        while(lexer.getTokenType() == OrgTokenTypes.BLOCK_CONTENT) {
            previous = lexer.getCurrentPosition();
            lexer.advance();
        }

        lexer.restore(previous); // do not eat the trailing token after the block content
    }

}
