package org.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;

/**
 *
 * @author erokshark
 */
public class TrackCorreios {
    
    private static final String TRACKURL = "http://www2.correios.com.br/sistemas/rastreamento/resultado_semcontent.cfm";

    public static BufferedImage getTrackResult(String trackingCode) {
        try {
            String html = UrlParser.readUrlPost(TRACKURL, "Objetos=" + trackingCode, "ISO-8859-1", 5000).replaceAll("[<](/)?img[^>]*[>]", "").replaceAll("[<](/)?script[^>]*[>]", "");
            JLabel label = new JLabel(html);
            System.out.println(html);
            label.setSize(550, 600);
            
            BufferedImage image = new BufferedImage(
                    label.getWidth(), label.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics g = image.getGraphics();
            g.setColor(java.awt.Color.WHITE);
            label.paint(g); 
            g.dispose();

            return image;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
