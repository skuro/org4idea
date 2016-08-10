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

import java.util.*;

/**
 * Enables blocks folding in org files, e.g. for code blocks, drawers, etc.
 *
 * @author Carlo Sciolla
 * @since 0.3.0
 */
public class OrgFoldingBuilder implements FoldingBuilder {

    private final static Set<IElementType> BLOCK_ELEMENTS = new HashSet<IElementType>(Arrays.asList(
            OrgTokenTypes.BLOCK,
            OrgTokenTypes.DRAWER));

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode astNode, @NotNull Document document) {
        final List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        collectBlocks(astNode, descriptors);
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    /**
     *
     * @param node
     * @param descriptors
     */
    protected void collectBlocks(final ASTNode node, final List<FoldingDescriptor> descriptors) {
        final IElementType token = node.getElementType();

        if(isBlock(token)) {
            foldBlock(node, descriptors);
        } else if (isOutline(token)) {
            foldOutline(node, descriptors);
        }

        for(ASTNode child : node.getChildren(null)) {
            collectBlocks(child, descriptors);
        }
    }

    private void foldOutline(ASTNode node, List<FoldingDescriptor> descriptors) {
        final ASTNode nextSibling = findNextOutline(node);
        final TextRange textRange;
        if(nextSibling != null) {
             textRange = TextRange.create(node.getStartOffset(), nextSibling.getStartOffset() - 1);
        } else {
            textRange = TextRange.create(node.getStartOffset(), getLastNode(node).getStartOffset());
        }
        final FoldingDescriptor descriptor = new FoldingDescriptor(node, textRange);
        descriptors.add(descriptor);
    }

    private ASTNode getLastNode(ASTNode node) {
        return node.getTreeParent().getLastChildNode();
    }

    /**
     * Find the next outline after the given node which has a depth equal or higher (-> less stars) than the current outline depth
     * Also stops
     * @param node The current outline node
     * @return The node representing the next outline with a depth equal or higher (-> less stars) than the current one
     */
    private ASTNode findNextOutline(ASTNode node) {
        final int depth = outlineDepth(node.getText());

        ASTNode next = null;
        for(ASTNode candidate = node.getTreeNext(); candidate != null && next == null; candidate = candidate.getTreeNext()) {
            if(isPeerOutline(depth, candidate)) {
                next = candidate;
            }
        }

        return next;
    }

    private boolean isPeerOutline(int depth, ASTNode candidate) {
        return isOutline(candidate.getElementType()) && outlineDepth(candidate.getText()) <= depth;
    }

    /**
     * Count how many stars are used in an outline, indicating its depth
     *
     * @param text The text of the outline
     * @return The number of initial stars in the outline
     */
    private int outlineDepth(String text) {
        if(text.startsWith("*")) {
            return text.split("[^*]")[0].length();
        } else {
            return 0;
        }
    }

    private void foldBlock(ASTNode node, List<FoldingDescriptor> descriptors) {
        final FoldingDescriptor descriptor = new FoldingDescriptor(node, node.getTextRange());
        descriptors.add(descriptor);
    }

    private boolean isOutline(IElementType token) {
        return OrgTokenTypes.OUTLINE.equals(token);
    }

    private boolean isBlock(IElementType token) {
        return BLOCK_ELEMENTS.contains(token);
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
