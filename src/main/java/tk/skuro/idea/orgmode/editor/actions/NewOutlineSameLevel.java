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
 * Create a new outline at the same level as the current one. The new outline is created at a different position
 * depending on the shape of the text:
 *
 * - as a next sibling outline before any following outlines of the same or lower depth
 * - at the beginning of the file if the file is empty
 * - at the end of the file if it doesn't contain any outline
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
            } else {
                addText(editor, file, 0, "* ");
            }
        }
    }

    private void addOutline(@NotNull PsiElement element, @NotNull Editor editor, @NotNull PsiFile file) {
        final Document document = editor.getDocument();

        // first search if the caret is itself within an outline text
        PsiElement currentOutline = getParentOutlineOrSelf(element);
        if (currentOutline != null) {
            final int offset = endOfOutline(currentOutline);
            final String text = createOutlineSameDepthAs(currentOutline);
            addText(editor, file, offset, text);
        } else {
            // the caret is not within an outline text, maybe we're in the body of one
            currentOutline = findPreviousOutline(element);
            if (currentOutline != null) {
                final int offset = endOfOutline(currentOutline);
                final String outlineText = createOutlineSameDepthAs(currentOutline);
                if(isEOF(offset, file)) {
                    addText(editor, file, offset, outlineText);
                }
                else {
                    addTextAndGoBackOne(editor, file, offset, outlineText + "\n");
                }
            } else {
                // there's no outline occurring before the caret, maybe there's one in the following text
                currentOutline = findNextOutline(element);
                if (currentOutline != null) {
                    final int offset = currentOutline.getTextRange().getStartOffset();
                    final String text = "* \n";
                    addTextAndGoBackOne(editor, file, offset, text);
                } else {
                    // there's no outline in the full text
                    final int documentEnd = document.getText().length();
                    addText(editor, file, documentEnd, "* ");
                }
            }
        }
    }

    private boolean isEOF(int offset, PsiFile file) {
        return offset == file.getTextRange().getEndOffset();
    }

    private int endOfOutline(PsiElement currentOutline) {
        PsiElement candidate = findNextOutlineSameDepthOrHigher(currentOutline);
        return candidate == null? currentOutline.getContainingFile().getTextRange().getEndOffset() :
                candidate.getTextOffset();
    }

    private void addText(@NotNull final Editor editor, @NotNull final PsiFile file, final int offset, @NotNull final String text) {
        new WriteCommandAction.Simple<String>(editor.getProject(), file) {
            private boolean isNewlineBefore(final int offset) {
                final String text = editor.getDocument().getText();
                return offset == 0 || offset > 0 && offset <= text.length() && '\n' == text.charAt(offset - 1);
            }

            @Override
            protected void run() throws Throwable {
                final Document document = editor.getDocument();
                final String textToEnter = isNewlineBefore(offset) ? text : "\n" + text;
                document.insertString(offset, textToEnter);
                editor.getCaretModel().getCurrentCaret().moveToOffset(offset + textToEnter.length());
            }
        }.execute();
    }

    private void addTextAndGoBackOne(@NotNull final Editor editor, @NotNull final PsiFile file, final int offset, @NotNull final String text) {
        new WriteCommandAction.Simple<String>(editor.getProject(), file) {
            private boolean isNewlineBefore(final int offset) {
                final String text = editor.getDocument().getText();
                return offset > 0 && offset <= text.length() && '\n' == text.charAt(offset - 1);
            }

            @Override
            protected void run() throws Throwable {
                final Document document = editor.getDocument();
                final String textToEnter = isNewlineBefore(offset) ? text : "\n" + text;
                document.insertString(offset, textToEnter);
                editor.getCaretModel().getCurrentCaret().moveToOffset(offset + textToEnter.length() - 1);
            }
        }.execute();
    }

    private PsiElement findNextOutlineSameDepthOrHigher(PsiElement element) {
        PsiElement candidate = element.getNextSibling();
        int depth = outlineDepth(element);
        while(candidate != null && !isOutlineBlock(candidate) && outlineDepth(candidate) <= depth) {
            candidate = candidate.getNextSibling();
        }

        return candidate;
    }

    private PsiElement findNextOutline(PsiElement element) {
        PsiElement candidate = findRootLevel(element);
        while(candidate != null && !isOutlineBlock(candidate)) {
            candidate = candidate.getNextSibling();
        }

        return candidate;
    }

    private int outlineDepth(final PsiElement element) {
        if(isOutlineBlock(element)) {
            return element.getText().split("\\s")[0].length();
        }

        final PsiElement previousOutline = findPreviousOutline(element);
        return previousOutline == null?  0 : outlineDepth(previousOutline);
    }

    private String createOutlineSameDepthAs(PsiElement currentOutline) {
        return currentOutline.getText().split("\\s")[0] + " ";
    }

    private PsiElement findPreviousOutline(PsiElement element) {
        PsiElement candidate = findRootLevel(element);
        while (candidate != null &&
                !candidate.getNode().getElementType().equals(OrgTokenTypes.OUTLINE_BLOCK)) {
            candidate = candidate.getPrevSibling();
        }
        return candidate;
    }

    private PsiElement findRootLevel(PsiElement element) {
        PsiElement candidate = element;
        while(!(candidate.getParent() instanceof PsiFile)) {
            candidate = candidate.getParent();
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
