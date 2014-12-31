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
    // TODO obsolete, replace with new way to do.


    /**
     * Default style for outline
     */
    // TODO: different style for outline levels
    public static final TextAttributesKey OUTLINE_ATTR_KEY = createTextAttributesKey("ORG.OUTLINE", KEYWORD);

    /**
     * Default style of comments
     */
    public static final TextAttributesKey COMMENTS_ATTR_KEY = createTextAttributesKey("ORG.COMMENT", LINE_COMMENT);

    /**
     * Default style of underline text
     */
    public static final TextAttributesKey UNDERLINE_ATTR_KEY = createTextAttributesKey("ORG.UNDERLINE", STRING);


}
