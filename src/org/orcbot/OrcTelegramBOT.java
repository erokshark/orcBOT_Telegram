package org.orcbot;

import org.utils.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.telegram.telegrambots.api.methods.groupadministration.LeaveChat;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.utils.RussianRoulette.Player;

/**
 *
 * @author erokshark
 */
public final class OrcTelegramBOT extends TelegramLongPollingBot {

    private static String[] ANSWERLIST;
    private static String[] USERLIST;
    private static RussianRoulette roulette;
    private static String TOKEN;
    private static String USERNAME;
    private static String ADMIN;
    private static String COMMAND;
    private static boolean ADMINRIGHTS;
    private static Long MAINCHAT;
    private static String OPENWEATHERAPI;
    private static String WOLFRAMALPHAAPI;
    private static String BITLYAPI;
    private static String GOOGLESEARCHAPI;
    private static String YANDEXTRANSLATEAPI;
    private static String TRACK;
    private static final long TIMESTAMP = (System.currentTimeMillis() / 1000L);
    private static String COMMAND_PREFIX;
    private static final String CONFIG_FILE = "orcBOT.ini";

    public OrcTelegramBOT() {
        try {
            roulette = new RussianRoulette();
            loadProperties();
            botSendMessage(MAINCHAT,
                    String.format("%s has started. Admin: %s\n%s", USERNAME, ADMIN,
                            new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                    .format(new java.util.Date(TIMESTAMP * 1000))));
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File %s not found on directory: %s.\n", CONFIG_FILE, new File("").getAbsolutePath()) + e);
            System.exit(0);
        } catch (IOException e) {
            System.out.println(String.format("Failed to read from file %s. Check user permissions.\n", CONFIG_FILE) + e);
            System.exit(0);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if ((update.hasMessage() && update.getMessage().hasText())
                    && (update.getMessage().getDate() >= TIMESTAMP)) {
                botSendMessage(update.getMessage().getChatId(), parseMessage(update.getMessage()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String parseMessage(Message message) throws Exception {
        ADMINRIGHTS = message.getFrom().getUserName().equals(ADMIN);
        if (message.getText().startsWith(COMMAND_PREFIX)) {
            COMMAND = message.getText().substring(1).toLowerCase();
            if (ADMINRIGHTS) {
                if (COMMAND.equals("quit")) {
                    System.exit(0);
                }
                if (COMMAND.equals("leave")) {
                    super.leaveChat(new LeaveChat().setChatId(message.getChatId()));
                    return null;
                }
                if (COMMAND.startsWith("cmd")) {
                   return SysUtil.execCmdWindows(COMMAND.substring(3).trim());
                }
                
                if (COMMAND.startsWith("update")
                        && (COMMAND.substring(6).trim().equals(getBotUsername()))) {
                    botSendMessage(message.getChatId(), String.format("Updating %s!", getBotUsername()));
                    Runtime.getRuntime().exec(String.format("sh %s.sh > updatelog.txt 2>&1", getBotUsername()));
                    System.exit(0);                    
                }
            }
            if (COMMAND.startsWith("orcbot")) {
                return ANSWERLIST[getRandomNumber(ANSWERLIST.length)];
            }
            if (Arrays.asList("commands", "help").contains(COMMAND)) {
                return ".help | .commands = Displays available commands and their usage.\n"
                        + ".orcbot <question> = I will answer your question.\n"
                        + ".alt <alternative1> OR <alternative2>... = Returns an alternative.\n"
                        + ".inforoulette = Displays info about the russian roulette game.\n"
                        + ".ping = PONG!\n"
                        + ".g <search> = Search for something on Google.\n"
                        + ".yt <search> = Search for something on Youtube.\n"
                        + ".w <location> = Displays current weather conditions for a location.\n"
                        + ".ud <term> = Search for a definition on Urban Dictionary.\n"
                        + ".cc <expression> = Calculates an expression with Wolfram Alpha.\n"
                        + ".imgcc <expression> = Same as \".cc\", with image format.\n"
                        + ".t <from> <to> <text> = Translate text. Example: .t en fr hello\n"
                        + ".shorten <link> = Shortens a URL using bit.ly.\n"
                        + ".quote = Quote of the day.\n"
                        + ".fml = Random FML quote.\n"
                        + ".zalgo <text> = " + getZalgo("He comes.") + "\n"
                        + ".track <tracking code> = Track a package in Correios.\n"
                        + ".tracklast = Tracks last searched package in Correios.\n"
                        + ".memory = Shows computer memory usage.\n"
                        + ".quit = I will shut down.";
            }
            if (COMMAND.startsWith("user")
                    && (Arrays.asList(USERLIST).contains(message.getFrom().getUserName()))) {
                return String.format("@%s, xDDD.", USERLIST[getRandomNumber(USERLIST.length)]);
            }
            if (COMMAND.startsWith("alt")) {
                String[] aux2 = COMMAND.substring(3).split("or");
                if (aux2.length > 1) {
                    String aux;
                    aux = aux2[getRandomNumber(aux2.length)].trim();
                    aux = aux.substring(aux.contains(":") ? aux.indexOf(":") + 1 : 0).trim();
                    aux = aux.substring(0, 1).toUpperCase() + aux.substring(1).replace("?", "");
                    return !aux.trim().isEmpty() ? aux + '.' : null;
                }
                return null;
            }
            if (COMMAND.startsWith("g")) {
                return searchGoogle(COMMAND.substring(1).trim());
            }
            if (COMMAND.startsWith("w")) {
                return getWeather(COMMAND.substring(1).trim());
            }
            if (COMMAND.startsWith("shorten")) {
                return getLink(COMMAND.substring(7).trim());
            }
            if (COMMAND.startsWith("zalgo")) {
                return getZalgo(COMMAND.substring(5).trim());
            }
            if (COMMAND.startsWith("imgcc")) {
                try {
                    ImageIO.write(getImgResultWolfram(COMMAND.substring(5).trim()), "png", new File("wolframalpha.png"));
                    sendPhoto(new SendPhoto().setChatId(message.getChatId()).setNewPhoto(new File("wolframalpha.png")));
                } catch (IOException | TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
            if (COMMAND.equals("tracklast")) {
                try {
                    loadProperties();
                    ImageIO.write(getTrackingResult(TRACK), "png", new File("trackresult.png"));
                    sendPhoto(new SendPhoto().setChatId(message.getChatId()).setNewPhoto(new File("trackresult.png")));
                } catch (IOException | TelegramApiException e) {
                    System.out.println(e.getMessage());
                    return "No data found.";                    
                }
                return null;
            }
            if (COMMAND.startsWith("track")) {
                try {
                    writeProperties("track", COMMAND.substring(5).trim());
                    ImageIO.write(getTrackingResult(COMMAND.substring(5).trim()), "png", new File("trackresult.png"));
                    sendPhoto(new SendPhoto().setChatId(message.getChatId()).setNewPhoto(new File("trackresult.png")));
                } catch (IOException | TelegramApiException e) {
                    System.out.println(e.getMessage());
                    return "No data found.";
                }
                return null;
            }
            if (COMMAND.startsWith("cc")) {
                return getStringResultWolfram(COMMAND.substring(2).trim());
            }
            if (COMMAND.startsWith("ud")) {
                return getUrbanDefinition(COMMAND.substring(2).trim());
            }
            if (COMMAND.startsWith("t")) {
                return getTranslation(COMMAND.substring(1).trim());
            }
            if (COMMAND.startsWith("yt")) {
                return getYoutubeVideo(COMMAND.substring(2).trim());
            }
            if (COMMAND.startsWith("quote")) {
                return getQuote();
            }
            if (COMMAND.equals("ping")) {
                return "PONG!";
            }
            if (COMMAND.equals("fml")) {
                return FuckMyLife.getRandomFml();
            }            
            if (COMMAND.equals("memory")) {
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    return SysUtil.getMemoryUsageWindows();
                } 
                if (System.getProperty("os.name").toLowerCase().contains("nix")
                        || System.getProperty("os.name").toLowerCase().contains("nux")
                        || System.getProperty("os.name").toLowerCase().contains("aix")) {
                    return SysUtil.getMemoryUsageUnix();
                }
                return null;
            }
            if (COMMAND.startsWith("level")) {
                return String.format("%s level: %s", COMMAND.substring(5).trim(), getRandomNumber(10000));
            }
            if (COMMAND.equals("inforoulette")) {
                return ".r = Start a russian roulette game.\n"
                        + ".r <accept> = Begins the roulette game.\n"
                        + ".r <trigger> = Pulls the trigger.\n"
                        + ".r <spin> = Spins the cylinder. One spin per player.\n"
                        + ".r <cancel> = Cancels current game.";
            }
            if (COMMAND.startsWith("r")) {
                String rouletteOption = COMMAND.substring(1).trim();
                if (!getRoulette().ready()) {
                    if ("".equals(rouletteOption)) {
                        getRoulette().resetGame();
                        botSendMessage(message.getChatId(), String.format("@%s called someone to a russian roulette game!", message.getFrom().getUserName()));
                        getRoulette().setPlayer(0, new Player(message.getFrom().getId(), message.getFrom().getUserName()));
                    }
                    if ("accept".equalsIgnoreCase(rouletteOption)
                            && (getRoulette().getPlayer(0) != null /*    && !(getRoulette().getPlayer(1).getName().equals(message.getFrom().getUserName()))*/)) {
                        getRoulette().setPlayer(1, new Player(message.getFrom().getId(), message.getFrom().getUserName()));
                        getRoulette().randomizePlayerOrder(3);
                        botSendMessage(message.getChatId(), String.format("@%s accepted the duel!\n@%s starts.", message.getFrom().getUserName(), getRoulette().getCurrentPlayer().getName()));
                    }
                } else {
                    if (getRoulette().ready()
                            && (getRoulette().getCurrentPlayer().getName().equals(message.getFrom().getUserName()))) {
                        if ("trigger".equalsIgnoreCase(rouletteOption)) {
                            botSendMessage(message.getChatId(), String.format("@%s pulls the trigger...", getRoulette().getCurrentPlayer().getName()));
                            sleep(1500);
                            if (getRoulette().pullTrigger(getRoulette().getCurrentPlayer())) {
                                botSendMessage(message.getChatId(), String.format("\t\t BANG!!! Player @%s is dead!", getRoulette().getCurrentPlayer().getName()));
                                getRoulette().resetGame();
                            } else {
                                botSendMessage(message.getChatId(), "\t\t...nothing happens.");
                            }
                            getRoulette().changePlayer();
                        }
                        if ("spin".equalsIgnoreCase(rouletteOption)) {
                            if (getRoulette().getCurrentPlayer().getHasSpinned()) {
                                botSendMessage(message.getChatId(), String.format("@%s, you have already used your spin.", getRoulette().getCurrentPlayer().getName()));
                            } else {
                                botSendMessage(message.getChatId(), String.format("@%s spins the cylinder.", getRoulette().getCurrentPlayer().getName()));
                                getRoulette().getCurrentPlayer().setHasSpinned(true);
                                getRoulette().resetCylinder();
                            }
                        }
                    }
                }
                if ("cancel".equalsIgnoreCase(rouletteOption)
                        && (Arrays.asList(getRoulette().getPlayers()).contains(new Player(message.getFrom().getId(), message.getFrom().getUserName())) || ADMINRIGHTS)) {
                    botSendMessage(message.getChatId(), "Game canceled.");
                    getRoulette().resetGame();
                }                
                return null;
            }
            if (getRandomNumber(200) == 1) {
                return String.format("%s shut up fag", !message.getFrom().getUserName().isEmpty() ? "@"+message.getFrom().getUserName() : message.getFrom().getFirstName());
            }
        }
        return null;
    }

    public String searchGoogle(String args) {
        return GoogleSearch.getDataFromGoogle(args, GOOGLESEARCHAPI);
    }

    public String getWeather(String args) {
        return OpenWeather.getWeather(args, OPENWEATHERAPI);
    }

    public String getLink(String args) {
        return BitlyShortener.getLink(args, BITLYAPI);
    }

    public BufferedImage getImgResultWolfram(String args) {
        return WolframAlpha.getImgResult(args, WOLFRAMALPHAAPI);
    }
    
    public BufferedImage getTrackingResult(String args) {
        return TrackCorreios.getTrackResult(args);//TrimWhite.getCroppedImage(TrackCorreios.getTrackResult(args));
    }

    public String getStringResultWolfram(String args) {
        return WolframAlpha.getStringResult(args, WOLFRAMALPHAAPI);
    }

    public String getUrbanDefinition(String args) {
        return UrbanDictionary.getDefinition(args);
    }

    public String getTranslation(String args) {
        return YandexTranslate.getTranslation(YANDEXTRANSLATEAPI, args.substring(0, 2).trim(), args.substring(3, 5).trim(), args.substring(5).trim());
    }

    private String getYoutubeVideo(String args) {
        return YoutubeSearch.getVideoFromYoutube(args, GOOGLESEARCHAPI);
    }
    
    private String getZalgo(String args) {
        return Zalgo.goZalgo(args, true, false, true, true, true);
    }
    
    public String getQuote() {
        return BrainyQuote.getQuote();
    }
    
    public void loadProperties() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File(CONFIG_FILE)));
        TOKEN = prop.getProperty("token", "").trim();
        USERNAME = prop.getProperty("username", "").trim();
        ADMIN = prop.getProperty("admin", "").trim();
        MAINCHAT = Long.valueOf(prop.getProperty("mainchat", "").trim());
        USERLIST = prop.getProperty("userlist", "").trim().split(";");
        ANSWERLIST = prop.getProperty("answerlist", "").trim().split(";");
        OPENWEATHERAPI = prop.getProperty("openweatherapi", "").trim();
        WOLFRAMALPHAAPI = prop.getProperty("wolframalphaapi", "").trim();
        BITLYAPI = prop.getProperty("bitlyapi", "").trim();
        GOOGLESEARCHAPI = prop.getProperty("googlesearchapi", "").trim();
        YANDEXTRANSLATEAPI = prop.getProperty("yandextranslateapi", "").trim();
        COMMAND_PREFIX = prop.getProperty("command_prefix", "").trim();
        TRACK = prop.getProperty("track", "").trim();
    }
    
    public void writeProperties(String key, String value) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File(CONFIG_FILE)));
        prop.setProperty(key, value); 
        prop.store(new FileOutputStream(CONFIG_FILE), null);
    }
    
    public void botSendMessage(long chatId, String text) {
        if (!(text == null) && (!"".equals(text))) {
            try {
                sendMessage(new SendMessage().setChatId(chatId).setText(text));
            } catch (TelegramApiException ex) {
                System.out.println(ex.getMessage());
            }
        }
        callGarbageCollector();
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    public RussianRoulette getRoulette() {
        return roulette;
    }

    public Integer getRandomNumber(int maxVal) {
        return (int)Math.floor(Math.random() * maxVal);
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void callGarbageCollector() {
        System.gc();
    }
}
