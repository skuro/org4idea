package tk.skuro.idea.orgmode.parser;

import tk.skuro.idea.orgmode.OrgLanguage;

import org.jetbrains.annotations.NonNls;

import com.intellij.psi.tree.IElementType;

/**
 * Class to represent an OrgElement token type for parsing org-mode text
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgTokenType extends IElementType {

    public OrgTokenType(@NonNls String debugId) {
        super(debugId, OrgLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "OrgTokenType." + super.toString();
    }
}
