package tk.skuro.idea.orgmode.parser;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import tk.skuro.idea.orgmode.OrgLanguage;

/**
 * Created by skuro on 08/08/16.
 */
public class OrgElementType extends IElementType {
    public OrgElementType(@NotNull @NonNls String debugName) {
        super(debugName, OrgLanguage.INSTANCE);
    }
}
