package tk.skuro.idea.orgmode.editor.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import tk.skuro.idea.orgmode.parser.OrgTokenTypes;
import tk.skuro.idea.orgmode.psi.OrgOutlineBlock;
import tk.skuro.idea.orgmode.psi.OrgPsiElementImpl;

/**
 * Create a new outline at the same level as the current one
 *
 * @author Carlo Sciolla
 * @since 0.4.0
 */
public class NewOutlineSameLevel extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final PsiElement element = e.getData(LangDataKeys.PSI_ELEMENT);
        final Editor editor = e.getData(LangDataKeys.EDITOR);

        if (element != null && editor != null) {
            addOutline(element, editor.getDocument());
        }
    }

    private void addOutline(@NotNull PsiElement element, @NotNull Document document) {
        // first search if the caret is itself within an outline text
        PsiElement currentOutline = getParentOutlineOrSelf(element);
        if (currentOutline != null) {
            document.insertString(currentOutline.getTextRange().getEndOffset(), createOutlineSameDepthAs(currentOutline));
        } else {
            // the text is not part of an outline, maybe we're in the body of one
            currentOutline = findPreviousOutline(element);
            if (currentOutline != null) {
                document.insertString(currentOutline.getTextRange().getEndOffset(), createOutlineSameDepthAs(currentOutline));
            } else {
                // there's no outline occurring before the caret, maybe there's one in the following text
                currentOutline = findNextOutline(element);
                if (currentOutline != null) {
                    document.insertString(currentOutline.getTextRange().getStartOffset(), createOutlineSameDepthAs(currentOutline));
                } else {
                    // there's no outline in the full text
                    document.insertString(document.getText().length(), "* ");
                }
            }
        }
    }

    private PsiElement findNextOutline(PsiElement element) {
        final PsiElement topmostParent = PsiTreeUtil.getTopmostParentOfType(element, PsiElement.class);
        if(topmostParent != null) {
            PsiElement candidate = topmostParent.getNextSibling();
            while(candidate != null && !isOutlineBlock(candidate)) {
                candidate = candidate.getNextSibling();
            }

            return candidate;
        }

        return null;
    }

    private String createOutlineSameDepthAs(PsiElement currentOutline) {
        return currentOutline.getText().split("\\s")[0] + " ";
    }

    private PsiElement findPreviousOutline(PsiElement element) {
        while (element.getParent() != null) {
            element = element.getParent();
        }
        return PsiTreeUtil.getPrevSiblingOfType(element, OrgOutlineBlock.class);
    }

    private PsiElement getParentOutlineOrSelf(@NotNull PsiElement element) {
        final OrgPsiElementImpl candidate = PsiTreeUtil.getTopmostParentOfType(element, OrgPsiElementImpl.class);
        return isOutlineBlock(candidate) ? candidate : null;
    }

    private boolean isOutlineBlock(PsiElement candidate) {
        return candidate != null && candidate.getNode().getElementType() == OrgTokenTypes.OUTLINE_BLOCK;
    }
}
