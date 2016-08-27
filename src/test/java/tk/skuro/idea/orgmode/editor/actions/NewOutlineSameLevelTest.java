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
        addOutlineAndAssertCaret("", "* ".length());
    }

    public void testNewOutlineInFileWithNoOutlines() {
        final String orgText =
                "#+BEGIN_SRC\n" +
                "I'm code!\n" +
                "#+END_SRC";
        addOutlineAndAssertCaret(orgText, orgText.length() + "\n* ".length());
    }

    public void testNewOutlineFromBeginningOfOutline() {
        final String orgText =
                "* I'm an outline\n" +
                "I'm the body of the outline";
        addOutlineAndAssertCaret(orgText, orgText.length() + "\n* ".length());
    }

    public void testNewOutlineFromOutlineBody() {
        final String orgText =
                "* I'm an outline\n" +
                "I'm the body of the outline";
        moveCaretAddOutlineAndAssertCaret(orgText.length() - 10, orgText, orgText.length() + "\n* ".length());
    }

    public void testNewOutlineFromInnerOutlineBody(){
        final String orgText =
                "* I'm an outer outline\n" +
                        "I'm the body of the outline\n" +
                        "** I'm the inner outline\n" +
                        "And here's some text";
        moveCaretAddOutlineAndAssertCaret(orgText.length() - 10, orgText, orgText.length() + "\n** ".length());
    }

    private void addOutlineAndAssertCaret(final String text, final int position) {
        moveCaretAddOutlineAndAssertCaret(0, text, position);
    }

    private void moveCaretAddOutlineAndAssertCaret(final int moveTo, final String text, final int position) {
        final PsiFile org = myFixture.configureByText("test.org", text);
        final DataContext ctx = new TestDataProvider(org.getProject());
        if(moveTo > 0) {
            moveCaret(ctx, moveTo);
        }
        final TestActionEvent event = new TestActionEvent(ctx);
        action.actionPerformed(event);
        assertCaretPosition("The caret was not placed after the newly created outline", ctx, position);
    }

    private void moveCaret(final DataContext ctx, final int moveTo) {
        final Editor editor = LangDataKeys.EDITOR.getData(ctx);
        editor.getCaretModel().getCurrentCaret().moveToOffset(moveTo);
    }

    private void assertCaretPosition(String errMessage, DataContext ctx, int expected) {
        final Editor data = LangDataKeys.EDITOR.getData(ctx);
        assert data != null;
        final int currentPosition = data.getCaretModel().getCurrentCaret().getOffset();
        assertEquals(errMessage, expected, currentPosition);
    }

}