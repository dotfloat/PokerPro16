package org.gruppe2.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import org.gruppe2.ui.javafx.PokerApplication;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Resources {
    private final static String pokerProDir = "PokerPro16" + File.separator;
    private final static String uiPackageString = PokerApplication.class.getPackage().getName();

    private static Map<String, Image> avatars = null;
    private static Image defaultAvatar = null;

    /**
     * Get the user-writable directory for PokerPro16
     *
     * @return Path to the user directory with a trailing slash
     */
    public static String getUserDir() {
        if (System.getProperty("os.name").equals("Linux")) {
            // Use the XDG Standard when on Linux
            String home = System.getenv("XDG_DATA_HOME");

            if (home == null || home.isEmpty()) {
                home = System.getenv("HOME");

                if (home == null || home.isEmpty()) {
                    // we're screwed
                    return getDefaultUserDir();
                }

                home += "/.local/share";
            }

            return home + "/" + pokerProDir;
        }

        return getDefaultUserDir();
    }

    /**
     *
     */
    public static File getProperties() throws IOException {
        createUserDirs();

        File file = new File(getUserDir() + "properties.cfg");

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    /**
     * Get the
     *
     * @param node
     */

    public static void loadFXML(Node node) {
        try {
            String path = node.getClass().getName().substring(uiPackageString.length()).replace('.', '/');

            FXMLLoader fxmlLoader = new FXMLLoader(Resources.class.getResource("/views" + path + ".fxml"));

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
            Scanner dir = new Scanner(Resources.class.getResourceAsStream("/images/avatars/avatars.txt"));

            while (dir.hasNext()) {
                String name = dir.next();
                Image image = new Image(Resources.class.getResourceAsStream("/images/avatars/" + name + ".png"));
                defaultAvatar = image;

                avatars.put(name, image);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String getDefaultUserDir() {
        return System.getProperty("user.home") + File.separator + pokerProDir;
    }

    private static void createUserDirs() {
        new File(getUserDir()).mkdirs();
    }
}
