package tk.skuro.idea.orgmode;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import com.intellij.CommonBundle;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class MessageBundle {
    private static Reference<ResourceBundle> reference;

    @NonNls
    protected static final String PATH_TO_BUNDLE = "messages.MessageBundle";

    private MessageBundle() {
    }

    /**
     * Retrieve Message from bundle
     * @param key message key
     * @param params optinal value for placeholders
     * @return Message associated to key
     */
    public static String message(@PropertyKey(resourceBundle = PATH_TO_BUNDLE)String key, Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = null;
        if (reference != null) bundle = reference.get();
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(PATH_TO_BUNDLE);
            reference = new SoftReference<ResourceBundle>(bundle);
        }
        return bundle;
    }
}
