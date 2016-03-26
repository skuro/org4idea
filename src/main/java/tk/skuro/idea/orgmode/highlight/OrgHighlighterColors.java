package tk.skuro.idea.orgmode.highlight;

import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.DefaultLanguageHighlighterColors.*;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface OrgHighlighterColors {

    /**
     * Default style for outline
     */
    // TODO: different style for outline levels
    TextAttributesKey OUTLINE_ATTR_KEY = createTextAttributesKey("ORG.OUTLINE", KEYWORD);

    /**
     * Default style of comments
     */
    TextAttributesKey COMMENTS_ATTR_KEY = createTextAttributesKey("ORG.COMMENT", LINE_COMMENT);

    /**
     * Default style of comment keyword {@code #+TITLE}
     */
    TextAttributesKey KEYWORD_ATTR_KEY = createTextAttributesKey("ORG.KEYWORD", LINE_COMMENT);

    /**
     * Default style of block delimiter
     */
    TextAttributesKey BLOCK_DELIM_ATTR_KEY = createTextAttributesKey("ORG.BLOCK_DELIMITER",
            KEYWORD_ATTR_KEY);

    /**
     * Default style of code
     */
    TextAttributesKey CODE_ATTR_KEY = createTextAttributesKey("ORG.CODE", TEMPLATE_LANGUAGE_COLOR);

    /**
     * Default style of block content
     */
    TextAttributesKey BLOCK_CONTENT_ATTR_KEY = createTextAttributesKey("ORG.BLOCK_CONTENT",
            CODE_ATTR_KEY);

    /**
     * Default style of Bold text
     */
    TextAttributesKey BOLD_ATTR_KEY = createTextAttributesKey("ORG.BOLD", STRING);

    /**
     * Default style of verbatim text
     */
    TextAttributesKey VERBATIM_ATTR_KEY = createTextAttributesKey("ORG.VERBATIM", STRING);

    /**
     * Default style of strokethrought text
     */
    TextAttributesKey STRIKETHROUGH_ATTR_KEY = createTextAttributesKey("ORG.STRIKETHROUGH", STRING);
    /**
     * Default style of underline text
     */
    TextAttributesKey UNDERLINE_ATTR_KEY = createTextAttributesKey("ORG.UNDERLINE", STRING);


}
