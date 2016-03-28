package tk.skuro.idea.orgmode.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgParser implements PsiParser {

    @NotNull
    @Override
    public ASTNode parse(final @NotNull IElementType iElementType, final @NotNull PsiBuilder builder) {

        final PsiBuilder.Marker rootMarker = builder.mark();

        while (!builder.eof()) {
            parseElement(builder);
            builder.advanceLexer();
        }

        rootMarker.done(iElementType);
        return builder.getTreeBuilt();
    }

    private void parseElement(PsiBuilder builder) {
        final IElementType tokenType = builder.getTokenType();
        if(tokenType == OrgTokenTypes.BLOCK_DELIMITER) {
            parseBlock(builder);
        }
    }

    private void parseBlock(PsiBuilder builder) {
        final PsiBuilder.Marker blockMark = builder.mark();
        do {
            builder.advanceLexer();
        }
        while(builder.getTokenType() != OrgTokenTypes.BLOCK_DELIMITER && !builder.eof());

        if (!builder.eof()) builder.advanceLexer();
        blockMark.done(OrgTokenTypes.BLOCK);
    }
}
