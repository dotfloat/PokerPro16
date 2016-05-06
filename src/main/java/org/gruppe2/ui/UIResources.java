package org.gruppe2.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import org.gruppe2.game.Card;
import org.gruppe2.game.Cards;
import org.gruppe2.ui.javafx.PokerApplication;

import java.io.IOException;
import java.util.*;

public class UIResources {
    private final static String uiPackageString = PokerApplication.class.getPackage().getName();

    private static List<Image> cards = null;

    private static Map<String, Image> avatars = null;
    private static Image defaultAvatar = null;

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

            long time = System.currentTimeMillis();
            fxmlLoader.load();
            System.out.printf("FXML load %s: %dms\n", node.getClass().getSimpleName(), System.currentTimeMillis() - time);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getAvatar(String name) {
        loadAvatars();

        return avatars.getOrDefault(name, defaultAvatar);
    }

    public static String[] listAvatars() {
        loadAvatars();

        return avatars.keySet().toArray(new String[avatars.size()]);
    }

    private static void loadAvatars() {
        if (avatars != null)
            return;

        avatars = new HashMap<>();

        try {
            Scanner dir = new Scanner(UIResources.class.getResourceAsStream("/images/avatars/avatars.txt"));

            while (dir.hasNext()) {
                String name = dir.next();
                Image image = new Image(UIResources.class.getResourceAsStream("/images/avatars/" + name + ".png"));
                defaultAvatar = image;

                avatars.put(name, image);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static Image getCard(Card card) {
        loadCards();

        return cards.get(card.getSuit().ordinal() * 13 + (card.getFaceValue() - 2));
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
}
