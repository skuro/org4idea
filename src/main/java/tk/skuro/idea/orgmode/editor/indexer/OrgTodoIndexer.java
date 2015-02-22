package tk.skuro.idea.orgmode.editor.indexer;

import com.intellij.lexer.Lexer;
import com.intellij.psi.impl.cache.impl.OccurrenceConsumer;
import com.intellij.psi.impl.cache.impl.todo.LexerBasedTodoIndexer;

/**
 * {@link LexerBasedTodoIndexer} for Orgmode.
 *
 * @author Adriean Khisbe <adriean.khisbe@live.fr>
 * @since 0.2
 */
public class OrgTodoIndexer extends LexerBasedTodoIndexer {

    @Override
    public Lexer createLexer(OccurrenceConsumer occurrenceConsumer) {
        return OrgIdIndexer.createIndexingLexer(occurrenceConsumer);
    }
}
