package org.utils;

import org.json.JSONObject;

/**
 *
 * @author erokshark
 */
public class YoutubeSearch {
    
    private static final String YOUTUBEVIDEOURL = "https://www.youtube.com/watch?v=%s";
    private static final String YOUTUBESEARCH = "https://www.googleapis.com/youtube/v3/search?key=%s&part=snippet&q=%s&maxResults=1&type=video&safeSearch=none";
    
    public static String getVideoFromYoutube(String query, String api) {
        try {
            JSONObject json = new JSONObject(UrlParser.readUrl(String.format(YOUTUBESEARCH, api, query.replace(" ", "+"))));
            return String.format(":: YouTube: %s | %s ::", String.format(YOUTUBEVIDEOURL, json.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId")),
                    json.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return String.format("Youtube: No result found for: %s", query);
    }
}
