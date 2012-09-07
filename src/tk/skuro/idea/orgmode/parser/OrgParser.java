package tk.skuro.idea.orgmode.parser;

import org.jetbrains.annotations.NotNull;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType iElementType, PsiBuilder builder) {

        PsiBuilder.Marker rootMarker = builder.mark();

        // TODO Actual parsing not implemented

        while (!builder.eof()) {
            builder.advanceLexer();
        }

        rootMarker.done(iElementType);
        return builder.getTreeBuilt();
    }
}
