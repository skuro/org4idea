package tk.skuro.idea.orgmode.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgPsiElementImpl extends ASTWrapperPsiElement {
    public OrgPsiElementImpl(@org.jetbrains.annotations.NotNull ASTNode astNode) {
        super(astNode);
    }
}
