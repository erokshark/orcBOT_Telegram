package org.orcbot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 *
 * @author erokshark
 */
public class MainOrcTelegramBOT {
    
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new OrcTelegramBOT());            
        } catch (TelegramApiRequestException e) {
            System.out.println(e.getMessage());
        }
    }        
}
