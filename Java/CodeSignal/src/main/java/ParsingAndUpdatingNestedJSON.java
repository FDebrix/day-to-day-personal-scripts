package main.java;

import java.util.HashMap;
import java.util.Map;

// Mastering Task Decomposition in JAVA - Course 3 / Unit 3
// Lesson 4 - Parsing and Updating Nested HashMaps in Java
// Practice 1 - Parsing and Updating Nested JSON in Java
public class ParsingAndUpdatingNestedJSON {

    public static final String KEY4 = "key4";

    private static final boolean SOP = false; // set to true for logging
    private static final String BRACE_START = "{";
    private static final String BRACE_END = "}";
    private static final String COLONS = ":";
    private static final String DOUBLE_QUOTE = "\"";

    private int currentIndex = 0;
    private String jsonString;


    public Map<String, Map<String, String>> solution(String jsonString, String updateValue) {
        this.jsonString = jsonString;

        Map<String, Map<String, String>> parsedJson = parse ();
        updateValue(parsedJson, updateValue);
        return parsedJson;
    }

    private void updateValue(Map<String, Map<String, String>> parsedJson, String updateValue) {
        if(parsedJson.containsKey(KEY4)) {
            parsedJson.put(KEY4, Map.of(updateValue,""));
        }
        else {
            for(Map<String, String> intervalValue : parsedJson.values()) {
                if(intervalValue.containsKey(KEY4)) {
                    intervalValue.put(KEY4, updateValue);
                }
            }
        }
    }


    private Map<String, Map<String, String>> parse() {
        Map<String, Map<String, String>> output = new HashMap<>();

        while (indexOf(COLONS) != -1) {
            if(SOP) System.out.println("Read one main key value.\n\tParse the key");
            String key = getStringAndUpdateCurrentIndex();
            Map<String, String> value = getValue();
            output.put(key, value);
        }

        return output;
    }

    private Map<String, String> getValue() {
        int positionQuote = indexOf(DOUBLE_QUOTE);
        int positionBraceStart = indexOf(BRACE_START);

        Map<String, String> values = new HashMap<>();

        if(positionBraceStart == -1 || positionQuote < positionBraceStart) {
            if(SOP) System.out.println("\tParse the value");
            String value = getStringAndUpdateCurrentIndex();
            values.put(value, "");
        }
        else {
            if(SOP) System.out.println("\tThe value contains a sub key-value");
            while (stillHaveOneSubKeyValue()) {
                if(SOP) System.out.println("\tParse the key");
                String key = getStringAndUpdateCurrentIndex();
                if(SOP) System.out.println("\tParse the value");
                String value = getStringAndUpdateCurrentIndex();
                values.put(key, value);
            }
        }

        return values;
    }

    private boolean stillHaveOneSubKeyValue() {
        int indexBraceEnd = indexOf(BRACE_END);
        int indexColons = indexOf(COLONS);

        return (indexColons != -1 && indexColons < indexBraceEnd);
    }

    private String getStringAndUpdateCurrentIndex() {
        int firstQuoteIndex = indexOf(DOUBLE_QUOTE);
        int secondQuoteIndex = indexOf(DOUBLE_QUOTE, firstQuoteIndex + 1);
        String value = jsonString.substring(firstQuoteIndex + 1, secondQuoteIndex);
        currentIndex = secondQuoteIndex + 1;

        if(SOP) System.out.println("\t\t\tParse a string - firstQuoteIndex: "+ firstQuoteIndex + ", secondQuoteIndex: " + secondQuoteIndex + ", value: -" + value + "-");

        return value;
    }

    private int indexOf(String value, int indexStart) {
        return jsonString.indexOf(value, indexStart);
    }

    private int indexOf(String value) {
        return indexOf(value, currentIndex);
    }
}
