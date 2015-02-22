package tk.skuro.idea.orgmode.editor;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.Nullable;

/**
 * {@link com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider} for Orgmode.
 *
 * @author Adriean Khisbe <adriean.khisbe@live.fr>
 * @since 0.2
 */
public class OrgLiveTemplatesProvider implements DefaultLiveTemplatesProvider {

    public static final String[] TEMPLATE_FILES = new String[]{"liveTemplates/Org"};

    @Override
    public String[] getDefaultLiveTemplateFiles() {
        return TEMPLATE_FILES;
    }

    @Nullable
    @Override
    public String[] getHiddenLiveTemplateFiles() {
        return ArrayUtil.EMPTY_STRING_ARRAY;
    }
}
