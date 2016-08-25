package tk.skuro.idea.orgmode.editor.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import tk.skuro.idea.orgmode.parser.OrgTokenTypes;
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
        final PsiFile file = e.getData(LangDataKeys.PSI_FILE);
        final Editor editor = e.getData(LangDataKeys.EDITOR);
        if(editor!= null && file != null) {
            final CaretModel caretModel = editor.getCaretModel();
            final int offset = caretModel.getCurrentCaret().getOffset();
            final PsiElement element = file.findElementAt(offset);
            if(element != null) {
                addOutline(element, editor, file);
            }
        }
    }

    private void addOutline(@NotNull PsiElement element, @NotNull Editor editor, @NotNull PsiFile file) {
        final Document document = editor.getDocument();

        // first search if the caret is itself within an outline text
        PsiElement currentOutline = getParentOutlineOrSelf(element);
        if (currentOutline != null) {
            addText(editor, file, currentOutline.getTextRange().getEndOffset(), createOutlineSameDepthAs(currentOutline));
        } else {
            // the text is not part of an outline, maybe we're in the body of one
            currentOutline = findPreviousOutline(element);
            if (currentOutline != null) {
                addText(editor, file, currentOutline.getTextRange().getEndOffset(), createOutlineSameDepthAs(currentOutline));
            } else {
                // there's no outline occurring before the caret, maybe there's one in the following text
                currentOutline = findNextOutline(element);
                if (currentOutline != null) {
                    addText(editor, file, currentOutline.getTextRange().getStartOffset(), createOutlineSameDepthAs(currentOutline));
                } else {
                    // there's no outline in the full text
                    addText(editor, file, document.getText().length(), "\n* ");
                }
            }
        }
    }

    private void addText(@NotNull final Editor editor, @NotNull final PsiFile file, final int offset, @NotNull final String text) {
        new WriteCommandAction.Simple<String>(editor.getProject(), file) {
            @Override
            protected void run() throws Throwable {
                final Document document = editor.getDocument();
                document.insertString(offset, text);
                editor.getCaretModel().getCurrentCaret().moveToOffset(offset + text.length());
            }
        }.execute();
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
        return "\n" + currentOutline.getText().split("\\s")[0] + " ";
    }

    private PsiElement findPreviousOutline(PsiElement element) {
        PsiElement candidate = element;
        while (candidate != null &&
                !candidate.getNode().getElementType().equals(OrgTokenTypes.OUTLINE_BLOCK)) {
            candidate = candidate.getPrevSibling();
        }
        return candidate;
    }

    private PsiElement getParentOutlineOrSelf(@NotNull PsiElement element) {
        final OrgPsiElementImpl candidate = PsiTreeUtil.getTopmostParentOfType(element, OrgPsiElementImpl.class);
        return isOutlineBlock(candidate) ? candidate : null;
    }

    private boolean isOutlineBlock(PsiElement candidate) {
        return candidate != null && candidate.getNode().getElementType() == OrgTokenTypes.OUTLINE_BLOCK;
    }
}
