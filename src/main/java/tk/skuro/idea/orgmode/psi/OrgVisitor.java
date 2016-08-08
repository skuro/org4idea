// This is a generated file. Not intended for manual editing.
package tk.skuro.idea.orgmode.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class OrgVisitor extends PsiElementVisitor {

  public void visitBlock(@NotNull OrgBlock o) {
    visitPsiElement(o);
  }

  public void visitDrawer(@NotNull OrgDrawer o) {
    visitPsiElement(o);
  }

  public void visitTextElement(@NotNull OrgTextElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
