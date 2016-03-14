package org.gruppe2;

import org.gruppe2.console.ConsoleClient;
import org.gruppe2.frontend.GUI;

public class Main {
    private static boolean useGUI = true;

    public static void main(String[] args) {
        parseArgs(args);

        if (useGUI) {
            GUI.main(null);
        } else {
            ConsoleClient.main(null);
        }
    }

    private static void parseArgs(String[] args) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "--console":
                case "--nogui":
                case "-c":
                    useGUI = false;
                    break;

                default:
                    System.out.println("Unknown argument: " + arg);
                    break;
            }
        }
    }
}
