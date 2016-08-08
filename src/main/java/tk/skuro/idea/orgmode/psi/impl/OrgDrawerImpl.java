// This is a generated file. Not intended for manual editing.
package tk.skuro.idea.orgmode.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static tk.skuro.idea.orgmode.parser.OrgTokenTypes.*;
import tk.skuro.idea.orgmode.psi.OrgPsiElementImpl;
import tk.skuro.idea.orgmode.psi.*;

public class OrgDrawerImpl extends OrgPsiElementImpl implements OrgDrawer {

  public OrgDrawerImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull OrgVisitor visitor) {
    visitor.visitDrawer(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof OrgVisitor) accept((OrgVisitor)visitor);
    else super.accept(visitor);
  }

}
