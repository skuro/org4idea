package tk.skuro.idea.orgmode.parser;

import tk.skuro.idea.orgmode.OrgLanguage;
import tk.skuro.idea.orgmode.psi.OrgFileImpl;
import tk.skuro.idea.orgmode.psi.OrgPsiElementImpl;

import org.jetbrains.annotations.NotNull;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.EmptyLexer;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * @author Carlo Sciolla
 * @since 0.1
 */
public class OrgParserDefinition implements ParserDefinition {

    /**
     * Get the lexer for lexing files in the specified project.
     *
     * @param project the project to which the lexer is connected.
     * @return an {@link EmptyLexer} instance.
     */
    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new OrgLexer();
    }

    /**
     * Get the parser for parsing files in the specified project.
     *
     * @param project the project to which the parser is connected.
     * @return an {@link OrgParser} instance.
     */
    @Override
    public PsiParser createParser(Project project) {
        return new OrgParser();
    }

    /**
     * Get the element type of the node describing a Markdown file.
     *
     * @return A {@link IStubFileElementType} using {@link OrgLanguage#INSTANCE}
     */
    @Override
    public IFileElementType getFileNodeType() {
        return new IStubFileElementType(OrgLanguage.INSTANCE);
    }

    /**
     * Get the set of token types which are treated as whitespace by the PSI builder.
     *
     * @return The whitespaces token set
     */
    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return OrgTokenTypes.WHITESPACES;
    }

    /**
     * Get the set of token types which are treated as comments by the PSI builder.
     *
     * @return {@link TokenSet#EMPTY}
     */
    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return OrgTokenTypes.COMMENTS;
    }

    /**
     * Get the set of element types which are treated as string literals.
     *
     * @return {@link TokenSet#EMPTY}
     */
    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    /**
     * Create a PSI element for the specified AST node.
     *
     * @param astNode the AST node.
     * @return the PSI element matching the element type of the AST node.
     */
    @NotNull
    @Override
    public PsiElement createElement(ASTNode astNode) {
        return new OrgPsiElementImpl(astNode);
    }

    /**
     * Create a PSI element for the specified virtual file.
     *
     * @param fileViewProvider virtual file.
     * @return the PSI file element.
     */
    @Override
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return new OrgFileImpl(fileViewProvider);
    }

    /**
     * Check if the specified two token types need to be separated by a space according to the language grammar.
     *
     * @param left  the first token to check.
     * @param right the second token to check.
     * @return {@link SpaceRequirements#MAY}
     */
    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
