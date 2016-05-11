package org.gruppe2.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.gruppe2.game.Card;
import org.gruppe2.ui.javafx.PokerApplication;

import java.io.IOException;
import java.util.*;

public class UIResources {
    private final static String uiPackageString = PokerApplication.class.getPackage().getName();

    private static List<Image> cards = null;
    private static Image cardBack = null;

    private static Map<String, Image> avatars = null;
    private static Map<String, Color> avatarColors = null;
    private static Image defaultAvatar = null;

    private static Map<String, Image> emotes = null;

    /**
     * Get the
     *
     * @param node
     */

    public static void loadFXML(Node node) {
        try {
            String path = node.getClass().getName().substring(uiPackageString.length()).replace('.', '/');

            FXMLLoader fxmlLoader = new FXMLLoader(UIResources.class.getResource("/views" + path + ".fxml"));

            fxmlLoader.setRoot(node);
            fxmlLoader.setController(node);

            fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getAvatar(String name) {
        loadAvatars();

        return avatars.getOrDefault(name, defaultAvatar);
    }

    public static Color getAvatarColor(String name) {
        loadAvatars();

        return avatarColors.getOrDefault(name, Color.WHITE);
    }

    public static String[] listAvatars() {
        loadAvatars();

        return avatars.keySet().toArray(new String[avatars.size()]);
    }

    private static void loadAvatars() {
        if (avatars != null)
            return;

        avatars = new HashMap<>();
        avatarColors = new HashMap<>();

        try {
            Scanner dir = new Scanner(UIResources.class.getResourceAsStream("/images/avatars/avatars.txt"));

            while (dir.hasNext()) {
                String name = dir.next();
                Image image = new Image(UIResources.class.getResourceAsStream("/images/avatars/" + name + ".png"));
                defaultAvatar = image;

                avatars.put(name, image);

                PixelReader pixelReader = image.getPixelReader();
                Color color = pixelReader.getColor(2,2);
                avatarColors.put(name, color);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static Image getCard(Card card) {
        loadCards();

        return cards.get(card.getSuit().ordinal() * 13 + (card.getFaceValue() - 2));
    }

    public static Image getCardBack() {
        if (cardBack == null) {
            cardBack = new Image(UIResources.class.getResourceAsStream("/images/cards/card_back.png"));
        }

        return cardBack;
    }

    private static void loadCards() {
        if (cards != null)
            return;

        cards = new ArrayList<>();

        char[] suitChars = { 'c', 'd', 'h', 's' };

        for (int face = 2; face <= 14; face++) {
            for (int suit = 0; suit < 4; suit++) {
                String path = String.format("/images/cards/%c%02d.png", suitChars[suit], face);

                cards.add(new Image(UIResources.class.getResourceAsStream(path)));
            }
        }
    }

    public static String[] listEmotes() {
        loadEmotes();

        return emotes.keySet().toArray(new String[emotes.size()]);
    }

    public static Image getEmote(String emote) {
        loadEmotes();

        return emotes.get(emote);
    }

    private static void loadEmotes() {
        if (emotes != null)
            return;

        try {
            String dir = "/images/emotes/";

            emotes = new HashMap<>();

            Properties emoteMap = new Properties();
            emoteMap.load(UIResources.class.getResourceAsStream("/images/emotes/emotes.properties"));

            for (Map.Entry<Object, Object> entry : emoteMap.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                String path = dir + value;

                emotes.put(key, new Image(UIResources.class.getResourceAsStream(path)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
