package org.utils;

import org.json.JSONObject;

/**
 *
 * @author erokshark
 */
public class GoogleSearch {
    
    private static final String GOOGLESEARCH = "https://www.googleapis.com/customsearch/v1?key=%s&cx=013036536707430787589:_pqjad5hr1a&fields=items(title,link)&q=%s&alt=json&num=1";
    
    public static String getDataFromGoogle(String query, String api) {
        try {
            JSONObject json = new JSONObject(UrlParser.readUrl(String.format(GOOGLESEARCH, api, query.replace(" ", "+"))));
            return String.format("%s | %s", json.getJSONArray("items").getJSONObject(0).getString("link"), json.getJSONArray("items").getJSONObject(0).getString("title"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return String.format("Your search - %s - did not match any documents.", query);
    }
}
