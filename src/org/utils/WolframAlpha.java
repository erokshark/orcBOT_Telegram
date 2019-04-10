package org.utils;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author erokshark
 */
public class WolframAlpha {

    private static final String WOLFRAMALPHAIMG = "http://api.wolframalpha.com/v1/simple?appid=%s&i=%s";
    private static final String WOLFRAMALPHA = "http://api.wolframalpha.com/v1/result?i=%s&appid=%s";

    public static BufferedImage getImgResult(String query, String api) {
        try {
            return ImageIO.read(new URL(String.format(WOLFRAMALPHAIMG, api, query.replace(" ", "+"))));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String getStringResult(String query, String api) {
        try {
            return String.format("Wolfram|Alpha: %s", UrlParser.readUrl(String.format(WOLFRAMALPHA, query.replace(" ", "+"), api)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Wolfram|Alpha did not understand your input.";        
    }
}
