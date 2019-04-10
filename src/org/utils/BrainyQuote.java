package org.utils;

/**
 *
 * @author erokshark
 */
public class BrainyQuote {
        
    private static final String BRAINYQUOTE = "https://www.brainyquote.com/link/quotebr.js";

    public static String getQuote() {
        try {
            return UrlParser.readUrl(BRAINYQUOTE).split(System.lineSeparator())[2].replace("br.writeln(\"", "").replace("<br>\");", "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "No quote available.";
    }
}
