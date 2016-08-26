package tk.skuro.idea.orgmode.editor.actions;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Created by skuro on 26/08/16.
 */
public class NewOutlineSameLevelTest extends LightCodeInsightFixtureTestCase {
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testFoo() {
        final PsiFile org = myFixture.configureByText("test.org", "* Foo");
        assertNotNull(org);
    }

}