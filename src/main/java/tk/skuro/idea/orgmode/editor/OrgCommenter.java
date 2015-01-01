package tk.skuro.idea.orgmode.editor;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 * {@link Commenter} for Orgmode.
 *
 * @author Adriean Khisbe <adriean.khisbe@live.fr>
 * @since 0.2
 */
public class OrgCommenter implements Commenter {


    @Nullable
    @Override
    public String getLineCommentPrefix() {
        return "# ";
    }

    @Nullable
    @Override
    public String getBlockCommentPrefix() {
        return null;
    }

    @Nullable
    @Override
    public String getBlockCommentSuffix() {
        return null;
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentSuffix() {
        return null;
    }
}
