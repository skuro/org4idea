package tk.skuro.idea.orgmode.editor;

import com.intellij.codeInsight.template.FileTypeBasedContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import tk.skuro.idea.orgmode.OrgFileType;
import tk.skuro.idea.orgmode.OrgLanguage;

/**
 * {@link com.intellij.codeInsight.template.FileTypeBasedContextType} for Orgmode.
 *
 * @author Adriean Khisbe <adriean.khisbe@live.fr>
 * @since 0.2
 */
public class OrgContextType extends FileTypeBasedContextType {

    protected OrgContextType() {
        super("Org", "Org", OrgFileType.INSTANCE);
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        return file.getLanguage().isKindOf(OrgLanguage.INSTANCE);
    }
}
