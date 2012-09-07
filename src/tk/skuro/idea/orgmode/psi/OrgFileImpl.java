package tk.skuro.idea.orgmode.psi;

import tk.skuro.idea.orgmode.OrgFileType;
import tk.skuro.idea.orgmode.OrgLanguage;

import org.jetbrains.annotations.NotNull;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgFileImpl extends PsiFileBase {

    public OrgFileImpl(@NotNull FileViewProvider fileViewProvider) {
        super(fileViewProvider, OrgLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return OrgFileType.INSTANCE;
    }
}
