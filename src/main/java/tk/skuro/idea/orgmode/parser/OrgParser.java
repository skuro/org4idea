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
        } else if(tokenType == OrgTokenTypes.DRAWER_DELIMITER) {
            parseDrawer(builder);
        }
    }

    private void parseBlock(PsiBuilder builder) {
        parseBlockElement(builder, OrgTokenTypes.BLOCK_DELIMITER, OrgTokenTypes.BLOCK);
    }

    private void parseDrawer(PsiBuilder builder) {
        parseBlockElement(builder, OrgTokenTypes.DRAWER_DELIMITER, OrgTokenTypes.DRAWER);
    }

    private void parseBlockElement(final PsiBuilder builder, final IElementType delimiter, final IElementType type) {
        final PsiBuilder.Marker drawerMarker = builder.mark();
        do {
            builder.advanceLexer();
        }
        while(builder.getTokenType() != delimiter && !builder.eof());

        if (!builder.eof()) builder.advanceLexer();
        drawerMarker.done(type);
    }
}
