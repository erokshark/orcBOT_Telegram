package org.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author lucas.h
 */
public class playroleta {

    private static final String URL = "http://www2.correios.com.br/sistemas/rastreamento/resultado_semcontent.cfm";

    public static void main(String[] args) {
        try {
            System.out.println(Zalgo.goZalgo("To invoke the hive-mind representing chaos.\n" +
                  "Invoking the feeling of chaos.\n" +
                  "With out order.\n" +
                  "The Nezperdian hive-mind of chaos. Zalgo.    \n" +
                  "He who Waits Behind The Wall.\n" +
                  "ZALGO!", true, false, true, true, true));
            /*
             Desktop.getDesktop().browse(new URI("https://www.google.com/#q=memes"));
            /*
            String cmd = SysUtil.execCmdWindows("cmd /c dir");
            byte[] cmd2 = cmd.getBytes("UTF-8");            
            //System.out.println(new String(cmd2));
            System.out.println(String.format("Memory Usage: %s/%sMB", SysUtil.getMaxRamWindows() - SysUtil.getFreeRamWindows(), SysUtil.getMaxRamWindows()));
            /*
            for (int i = 0; i < 10; i++) {
              System.out.println("Free: "+ getFreeRamWindows()+" MB / Total: "+ getMaxRamWindows()+" MB / Used: "+(getMaxRamWindows() - getFreeRamWindows())+" MB");     
              Thread.sleep(1000);
            }*/
 /*//AudioInputStream audio = AudioSystem.getAudioInputStream(new ByteArrayInputStream(UrlParser.readUrl("https://watson-api-explorer.mybluemix.net/text-to-speech/api/v1/synthesize?accept=audio%2Fogg%3Bcodecs%3Dopus&voice=pt-BR_IsabelaVoice&text=memes").getBytes()));
            
            FileOutputStream stream = new FileOutputStream(new File("meme.ogg"));
            byte[] meme = UrlParser.readUrl("https://watson-api-explorer.mybluemix.net/text-to-speech/api/v1/synthesize?accept=audio%2Fogg%3Bcodecs%3Dopus&voice=pt-BR_IsabelaVoice&text=memes").getBytes();
            try {
                stream.write(meme);
            } finally {
                stream.close();
            }*/
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        /*RussianRoulette roleta = new RussianRoulette();
        roleta.setPlayer(0, new Player(1, "andy"));
        roleta.setPlayer(1, new Player(1, "orc"));
        System.out.println(String.format("Starting the duel!\n@%s starts.", roleta.getCurrentPlayer().getName()));
        for (;;) {
            if (roleta.ready()) {
                System.out.println(String.format("@%s pulls the trigger...", roleta.getCurrentPlayer().getName()));
                if (roleta.pullTrigger(roleta.getCurrentPlayer())) {
                    System.out.println(String.format("\t\t BANG!!! Player @%s is dead!", roleta.getCurrentPlayer().getName()));
                    roleta.resetGame();
                    System.exit(0);
                } else {
                    System.out.println("\t\t...nothing happens.");
                }
                roleta.changePlayer();
            }
        }*/
    }
        }
