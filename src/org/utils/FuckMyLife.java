package org.utils;

import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author erokshark
 */
public class FuckMyLife {
    
    private static final String FUCKMYLIFE = "http://api.betacie.com/view/random/nocomment/?key=1&language=en";
    
    public static String getRandomFml() {
        try {          
            JSONObject json = XML.toJSONObject(UrlParser.readUrl(FUCKMYLIFE));
            return String.format("[fml #%s] %s", json.getJSONObject("root").getJSONObject("items").getJSONObject("item").getInt("id"), json.getJSONObject("root").getJSONObject("items").getJSONObject("item").getString("text"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;        
    }
}
