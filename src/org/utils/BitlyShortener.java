package org.utils;

/**
 *
 * @author erokshark
 */
public class BitlyShortener {
    
    private static final String BITLY = "https://api-ssl.bitly.com/v3/shorten?access_token=%s&longUrl=%s&format=txt";

    public static String getLink(String url, String api) {
        try {
            url = (url.startsWith("http") ? url : "http://" + url);
            return UrlParser.readUrl(String.format(BITLY, api, url));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Invalid URL.";
    }
}
