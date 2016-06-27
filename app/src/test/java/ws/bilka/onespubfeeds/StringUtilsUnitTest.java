package ws.bilka.onespubfeeds;

import org.junit.Test;

import ws.bilka.onespubfeeds.utils.StringUtils;

import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class StringUtilsUnitTest {

    @Test
    public void cut_with_short_text_isCorrect() {
        String shortText = "foo";
        String cutText = StringUtils.cut(shortText, 100);
        assertTrue(cutText.equals(shortText));
    }

    @Test
    public void cut_and_add_dots_text_isCorrect(){
        String text = "Unit tests run on a local JVM on your development machine.";
        String cutText = StringUtils.cut(text, 4);
        assertTrue(cutText.equals("Unit..."));
    }

    @Test
    public void cut_with_empty_text_isCorrect() {
        String emptyText = "";
        String cutText = StringUtils.cut(emptyText, 10);
        assertTrue(cutText.equals(emptyText));
    }

    @Test
    public void cut_with_incorrect_value_text_isCorrect(){
        String text = "Squirrel";
        String cutText = StringUtils.cut(text, -2);
        assertTrue(cutText.equals(text));
    }

    @Test
    public void cut_with_not_value_text_isCorrect(){
        String text = "";
        String cutText = StringUtils.cut(text, 0);
        assertTrue(cutText.equals(text));
    }

}