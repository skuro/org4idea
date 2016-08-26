package tk.skuro.idea.orgmode.editor.actions;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.TestActionEvent;
import com.intellij.testFramework.TestDataProvider;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.junit.After;
import org.junit.Before;

public class NewOutlineSameLevelTest extends LightCodeInsightFixtureTestCase {

    private NewOutlineSameLevel action = new NewOutlineSameLevel();

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testNewOutlineInEmptyFile() {
        final PsiFile org = myFixture.configureByText("empty.org", "");
        final DataContext ctx = new TestDataProvider(org.getProject());
        final TestActionEvent event = new TestActionEvent(ctx);
        action.actionPerformed(event);
        assertCaretPosition("The caret was not placed after the newly created outline", ctx, 2);
    }

    private void assertCaretPosition(String errMessage, DataContext ctx, int expected) {
        final Editor data = LangDataKeys.EDITOR.getData(ctx);
        final int currentPosition = data.getCaretModel().getCurrentCaret().getOffset();
        assertEquals(errMessage, expected, currentPosition);
    }

}