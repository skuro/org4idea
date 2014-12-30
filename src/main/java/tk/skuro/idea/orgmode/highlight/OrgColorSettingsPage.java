package tk.skuro.idea.orgmode.highlight;

import tk.skuro.idea.orgmode.MessageBundle;
import tk.skuro.idea.orgmode.OrgIcons;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.io.FileUtil;

/**
 * Configuration for the color font page of Org language
 *
 * @author Carlo Sciolla
 */
public class OrgColorSettingsPage implements ColorSettingsPage {

    /**
     * The {@link Logger}.
     */
    private final static Logger LOGGER = Logger.getInstance(OrgColorSettingsPage.class.getName());

    private static final String SAMPLE_ORG_DOCUMENT_PATH = "/sample.org";

    private static final String SAMPLE_ORG_DOCUMENT = loadSampleOrgDocument();

    /**
     * The set of {@link AttributesDescriptor} defining the configurable options in the dialog.
     */
    protected final List<AttributesDescriptor> attributeDescriptors = new LinkedList<AttributesDescriptor>();

    public OrgColorSettingsPage() {
        // Populate attribute descriptors.
        attributeDescriptors.add(new AttributesDescriptor(
                MessageBundle.message("org.editor.colorsettingspage.comment"),
                OrgHighlighterColors.COMMENTS_ATTR_KEY)
        );
        attributeDescriptors.add(new AttributesDescriptor(
                MessageBundle.message("org.editor.colorsettingspage.outline"),
                OrgHighlighterColors.OUTLINE_ATTR_KEY)
        );
        attributeDescriptors.add(new AttributesDescriptor(
                MessageBundle.message("org.editor.colorsettingspage.underline"),
                OrgHighlighterColors.UNDERLINE_ATTR_KEY)
        );
    }

    /**
     * Load the sample text to be displayed in the preview pane.
     *
     * @return the text loaded from {@link #SAMPLE_ORG_DOCUMENT_PATH}
     * @see #getDemoText()
     * @see #SAMPLE_ORG_DOCUMENT_PATH
     * @see #SAMPLE_ORG_DOCUMENT
     */
    protected static String loadSampleOrgDocument() {
        try {
            return FileUtil.loadTextAndClose(new InputStreamReader(
                    OrgColorSettingsPage.class.getResourceAsStream(SAMPLE_ORG_DOCUMENT_PATH)));
        } catch (Exception e) {
            LOGGER.error("Failed loading sample Org document", e);
        }
        return MessageBundle.message("org.editor.colorsettingspage.sample-loading-error");
    }

    @Override
    public Icon getIcon() {
        return OrgIcons.ORG_ICON;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new OrgSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return SAMPLE_ORG_DOCUMENT;
    }

    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return attributeDescriptors.toArray(new AttributesDescriptor[attributeDescriptors.size()]);
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return MessageBundle.message("org.file.type");
    }
}
