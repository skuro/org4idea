package tk.skuro.idea.orgmode.parser;

import tk.skuro.idea.orgmode.OrgLanguage;

import org.jetbrains.annotations.NonNls;

import com.intellij.psi.tree.IElementType;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgElementType extends IElementType {
    public OrgElementType(@NonNls String debugId) {
        super(debugId, OrgLanguage.INSTANCE);
    }
}
