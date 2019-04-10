package org.utils;

import java.io.*;
import java.net.*;
/**
 *
 * @author erokshark
 */
public class UrlParser {

    static String readUrlWithUserAgent(String urlString, String charset, String userAgent, int timeout) throws Exception {
        StringBuilder buffer = new StringBuilder();
        URLConnection connection = new URL(urlString).openConnection();
        connection.setRequestProperty("User-Agent", userAgent);
        connection.setConnectTimeout(timeout);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            buffer.append(inputLine).append(System.lineSeparator());
        }
        return buffer.toString();
    }
    
    static String readUrlPost(String urlString, String params, String charset, int timeout) throws Exception {
            byte[] postData = params.getBytes(charset);
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", charset);
            connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
            connection.setUseCaches(false);
            connection.setConnectTimeout(timeout);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }
            String inputLine;
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            while ((inputLine = reader.readLine()) != null) {
                buffer.append(inputLine);
            }
            return buffer.toString();
    }

    static String readUrl(String urlString) throws Exception {
        return readUrlWithUserAgent(urlString, "UTF-8", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11", 5000);
    }

    static String readUrl(String urlString, String charset) throws Exception {
        return readUrlWithUserAgent(urlString, charset, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11", 5000);
    }

}
