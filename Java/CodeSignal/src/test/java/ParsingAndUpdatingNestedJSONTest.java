package test.java;

import java.util.Map;
import main.java.ParsingAndUpdatingNestedJSON;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParsingAndUpdatingNestedJSONTest {

    public static final String KEY2 = "key2";
    public static final String KEY4 = "key4";

    @Test
    public void test_solution_oneSimpleKeyValue () {
        ParsingAndUpdatingNestedJSON parser = new ParsingAndUpdatingNestedJSON();
        String input = "{\"key4\": \"value4\"}";
        String newValueForKey4 = "newValue";

        Map<String, Map<String, String>> output = parser.solution(input, newValueForKey4);

        assertNotNull (output);
        assertEquals(1, output.size());
        assertTrue(output.containsKey(KEY4));
        assertEquals(1, output.get(KEY4).size());
        assertEquals(newValueForKey4, output.get(KEY4).entrySet().iterator().next().getKey());
    }

    // {\"key1\":\"value1\", \"key2\":{\"key3\":\"value3\", \"key4\":\"key4\"}, \"key5\":\"value5\"}
    @Test
    public void test_solution_oneSubValue () {
        ParsingAndUpdatingNestedJSON parser = new ParsingAndUpdatingNestedJSON();
        String input = "{\"key1\":\"value1\", \"key2\":{\"key3\":\"value3\", \"key4\":\"value4\"}, \"key5\":\"value5\"}";
        String newValueForKey4 = "newValue";

        Map<String, Map<String, String>> output = parser.solution(input, newValueForKey4);

        assertNotNull (output);
        assertEquals(3, output.size());
        assertFalse(output.containsKey(KEY4));
        assertTrue(output.containsKey(KEY2));
        assertEquals(2, output.get(KEY2).size());
        assertTrue(output.get(KEY2).containsKey(KEY4));
        assertEquals(newValueForKey4, output.get(KEY2).get(KEY4));
    }


    // {\"key1\":{\"key2\":\"value3\"}, \"key4\":\"key4\"}
    @Test
    public void test_solution_twoSubValues () {
        ParsingAndUpdatingNestedJSON parser = new ParsingAndUpdatingNestedJSON();
        String input = "{\"key1\":{\"key2\":\"value3\"}, \"key4\":\"value4\"}";
        String newValueForKey4 = "newValue";

        Map<String, Map<String, String>> output = parser.solution(input, newValueForKey4);

        assertNotNull (output);
        assertEquals(2, output.size());
        assertTrue(output.containsKey(KEY4));
        assertEquals(1, output.get(KEY4).size());
        assertEquals(newValueForKey4, output.get(KEY4).entrySet().iterator().next().getKey());
    }

    // {"key1": "value1", "key2": {"key3": "value3", "key4": "value4"}}
    @Test
    public void test_solution_oneSubValue2 () {
        ParsingAndUpdatingNestedJSON parser = new ParsingAndUpdatingNestedJSON();
        String input = "{\"key1\"\"value1\", \"key2\":{\"key3\":\"value3\", \"key4\":\"value4\"}}";
        String newValueForKey4 = "newValue";

        Map<String, Map<String, String>> output = parser.solution(input, newValueForKey4);

        assertNotNull (output);
        assertEquals(2, output.size());
        assertFalse(output.containsKey(KEY4));
        assertTrue(output.containsKey(KEY2));
        assertEquals(2, output.get(KEY2).size());
        assertEquals(newValueForKey4, output.get(KEY2).get(KEY4));
    }
}
