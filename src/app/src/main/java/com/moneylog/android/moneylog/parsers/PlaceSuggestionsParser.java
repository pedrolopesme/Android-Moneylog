package com.moneylog.android.moneylog.parsers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Place Suggestions Parser
 */

public class PlaceSuggestionsParser {

    static final String JSON_ROOT = "predictions";
    static final String JSON_STRUCTURED_FORMATING = "structured_formatting";
    static final String JSON_MAIN_TEXT = "main_text";

    /**
     * Parsers a list of Suggestions.
     *
     * @param placeSuggestions json string with Place Suggestions
     * @return List<String>
     */
    public static List<String> parseList(final String placeSuggestions) throws Exception {
        List<String> suggestions = new ArrayList<>();
        JSONObject rootJson = new JSONObject(placeSuggestions);
        JSONArray results = rootJson.getJSONArray(JSON_ROOT);
        if (results != null) {
            for (int c = 0; c < results.length(); c++) {
                JSONObject suggestionJson = (JSONObject) results.get(c);
                String suggestion = suggestionJson
                        .getJSONObject(JSON_STRUCTURED_FORMATING)
                        .getString(JSON_MAIN_TEXT);
                if (suggestion != null) {
                    suggestions.add(suggestion);
                }
            }
        }
        return suggestions;
    }


}
