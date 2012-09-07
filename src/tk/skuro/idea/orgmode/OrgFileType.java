package tk.skuro.idea.orgmode;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgFileType extends LanguageFileType {

    public final static OrgFileType INSTANCE = new OrgFileType();

    public final static String ORG_DEFAULT_EXTENSION = "org";

    protected OrgFileType() {
        super(OrgLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "OrgMode";
    }

    @NotNull
    @Override
    public String getDescription() {
        return MessageBundle.message("org.file.type.description");
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return OrgFileType.ORG_DEFAULT_EXTENSION;
    }

    @Override
    public Icon getIcon() {
        return OrgIcons.ORG_ICON;
    }
}
