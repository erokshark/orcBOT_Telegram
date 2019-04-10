package org.utils;

import org.json.JSONObject;

/**
 *
 * @author erokshark
 */
public class UrbanDictionary {

    private static final String URBANDICTIONARY = "http://api.urbandictionary.com/v0/define?term=%s";

    public static String getDefinition(String query) {
        int charLimit;
        try {
            JSONObject json = new JSONObject(UrlParser.readUrl(String.format(URBANDICTIONARY, query.replace(" ", "+"))));
            charLimit = json.getJSONArray("list").getJSONObject(0).getString("definition").length() <= 500 ? json.getJSONArray("list").getJSONObject(0).getString("definition").length() : 500;
            return String.format("Urban|Dictionary: %s", json.getJSONArray("list").getJSONObject(0).getString("definition").substring(0, charLimit) 
                    + "...\nURL: " + json.getJSONArray("list").getJSONObject(0).getString("permalink"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Urban|Dictionary did not understand your input.";
    }
}
