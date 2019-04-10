package org.utils;

import org.json.JSONObject;

/**
 *
 * @author erokshark
 */
public class OpenWeather {
    
    private static final String OPENWEATHERMAP = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s";
  
    public static String getWeather(String location, String api) {
        try {
            JSONObject json = new JSONObject(UrlParser.readUrl(String.format(OPENWEATHERMAP, location, api)));
            return String.format(":: %s, %s :: Conditions %s :: Temperature %sC :: Pressure %smb :: Humidity %s :: Last updated on %s UTC", 
                    json.getString("name"),
                    json.getJSONObject("sys").getString("country"),
                    json.getJSONArray("weather").getJSONObject(0).getString("description"),                    
                    json.getJSONObject("main").getLong("temp"),
                    json.getJSONObject("main").getInt("pressure"),
                    json.getJSONObject("main").getInt("humidity")+"%",
                    new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(json.getLong("dt") * 1000))
                    );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Invalid location.";
    }  
}
