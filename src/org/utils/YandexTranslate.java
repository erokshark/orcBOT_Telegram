package org.utils;

import org.json.JSONObject;

/**
 *
 * @author erokshark
 */
public class YandexTranslate {
    
    private static final String YANDEXTRANSLATE = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s";

    public static String getTranslation(String api, String from, String to, String text) {
        try {
            JSONObject json = new JSONObject(UrlParser.readUrl(String.format(YANDEXTRANSLATE, api, text.replace(" ", "+"), from + '-' + to)));
            return String.format("Translation [%s -> %s]: %s", 
                    from,
                    to,
                    json.getJSONArray("text").getString(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Yandex.Translate : Invalid input.";
    }
}