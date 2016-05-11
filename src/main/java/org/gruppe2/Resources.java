package org.gruppe2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Resources {
    private final static String pokerProDir = "PokerPro16" + File.separator;

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

    public static String getUserDir(String dir) {
        String path = getUserDir() + dir + File.separator;

        new File(path).mkdirs();

        return path;
    }

    public static String[] listAvatars() {
        List<String> avatars = new ArrayList<>();

        Scanner dir = new Scanner(Resources.class.getResourceAsStream("/images/avatars/avatars.txt"));

        while (dir.hasNext()) {
            avatars.add(dir.next());
        }

        return avatars.toArray(new String[avatars.size()]);
    }

    /**
     *
     */
    public static File getProperties() throws IOException {
        createUserDirs();

        File file = new File(getUserDir() + "properties.cfg");

        if (!file.exists()) {
            Main.setFirstStart(true);
            file.createNewFile();
        }

        return file;
    }

    private static String getDefaultUserDir() {
        return System.getProperty("user.home") + File.separator + pokerProDir;
    }

    private static void createUserDirs() {
        new File(getUserDir()).mkdirs();
    }

    public static InputStream getDefaultProperties() {
        return Resources.class.getResourceAsStream("/default.properties");
    }
}
