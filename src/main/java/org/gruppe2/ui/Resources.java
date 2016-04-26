package org.gruppe2.ui;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class Resources {
    private final static String pokerProDir = "PokerPro16" + File.pathSeparator;

    /**
     * Get the user-writable directory for PokerPro16
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
     * Get the
     * @param node
     */

    public static void loadFXML(Node node) {
        try {
            String name = "/views/" + node.getClass().getSimpleName() + ".fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(Resources.class.getResource(name));

            fxmlLoader.setRoot(node);
            fxmlLoader.setController(node);

            fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String getDefaultUserDir() {
        return System.getProperty("user.home") + File.pathSeparator + pokerProDir;
    }
}
