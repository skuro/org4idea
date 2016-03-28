package tk.skuro.idea.orgmode.editor.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.skuro.idea.orgmode.parser.OrgTokenTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Enables blocks folding in org files, e.g. for code blocks, drawers, etc.
 *
 * @author Carlo Sciolla
 * @since 0.3.0
 */
public class OrgFoldingBuilder implements FoldingBuilder {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode astNode, @NotNull Document document) {
        final List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        collectBlocks(astNode, descriptors);
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    protected void collectBlocks(final ASTNode node, final List<FoldingDescriptor> descriptors) {
        final IElementType token = node.getElementType();
        if(token == OrgTokenTypes.BLOCK) {
            final FoldingDescriptor descriptor = new FoldingDescriptor(node, node.getTextRange());
            descriptors.add(descriptor);
        }

        for(ASTNode child : node.getChildren(null)) {
            collectBlocks(child, descriptors);
        }
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode astNode) {
        final String firstLine = astNode.getText().split("\n")[0];
        return firstLine + "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
        return false;
    }
}
