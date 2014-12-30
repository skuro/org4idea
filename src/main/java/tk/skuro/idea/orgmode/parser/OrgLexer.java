package tk.skuro.idea.orgmode.parser;

import java.io.Reader;

import com.intellij.lexer.FlexAdapter;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgLexer extends FlexAdapter {

    public OrgLexer() {
        super(new _OrgLexer((Reader)null));
    }
}
