package tk.skuro.idea.orgmode.parser;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * Org Tokens for lexing
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface OrgTokenTypes {
    // TODO: package rego
    // maybe make a single declaration

    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    IElementType KEYWORD = new OrgElementType("ORG_KEYWORD");
    IElementType COMMENT = new OrgElementType("ORG_COMMENT");
    IElementType OUTLINE = new OrgElementType("ORG_OUTLINE");
    IElementType BLOCK_DELIMITER = new OrgElementType("ORG_BLOCK_DELIMITER");
    IElementType BLOCK_CONTENT = new OrgElementType("ORG_BLOCK_CONTENT");
    IElementType TEXT = new OrgElementType("ORG_TEXT");
    IElementType BOLD = new OrgElementType("ORG_BOLD");
    IElementType CODE = new OrgElementType("ORG_CODE");
    IElementType ITALIC = new OrgElementType("ORG_ITALIC");
    IElementType UNDERLINE = new OrgElementType("ORG_UNDERLINE");

    // see: tokenset from element
    TokenSet WHITESPACES = TokenSet.create(WHITE_SPACE);
    TokenSet COMMENTS = TokenSet.create(COMMENT);
    TokenSet OUTLINES = TokenSet.create(OUTLINE);
    TokenSet KEYWORDS = TokenSet.create(KEYWORD);
    TokenSet UNDERLINES = TokenSet.create(UNDERLINE);
    TokenSet CODES = TokenSet.create(CODE);
    TokenSet BLOCK_DELIMITERS = TokenSet.create(BLOCK_DELIMITER);
    TokenSet BLOCK_CONTENTS = TokenSet.create(BLOCK_CONTENT);
}
