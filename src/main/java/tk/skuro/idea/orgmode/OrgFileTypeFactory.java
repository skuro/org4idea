package tk.skuro.idea.orgmode;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

/**
 * An extension of {@link FileTypeFactory} for org-mode
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(OrgFileType.INSTANCE, OrgFileType.ORG_DEFAULT_EXTENSION);
    }
}
