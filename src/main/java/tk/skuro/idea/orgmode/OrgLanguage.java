package tk.skuro.idea.orgmode;

import com.intellij.lang.Language;

/**
 * Extension of a {@link Language} for org-mode
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public final class OrgLanguage extends Language {

    public static final String ORG_LANGUAGE_ID = "OrgMode";
    // see: http://lists.gnu.org/archive/html/emacs-diffs/2011-01/msg00290.html
    public static final String ORG_LANGUAGE_MIME_TYPE = "text/x-org";

    public static final OrgLanguage INSTANCE = new OrgLanguage();

    private OrgLanguage() {
        super(OrgLanguage.ORG_LANGUAGE_ID, OrgLanguage.ORG_LANGUAGE_MIME_TYPE);
    }
}
