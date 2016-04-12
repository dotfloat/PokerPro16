package org.gruppe2;

import javafx.application.Application;
import org.gruppe2.ui.console.ConsoleApplication;
import org.gruppe2.ui.console.ConsoleClient;
import org.gruppe2.ui.javafx.PokerApplication;

public class Main {
    private enum EntryPoint {
        CONSOLE, NEW_CONSOLE, JAVAFX
    }

    private static EntryPoint entryPoint = EntryPoint.JAVAFX;
    private static boolean autostart = false;

    public static void main(String[] args) {
        parseArgs(args);

        switch (entryPoint) {
            case CONSOLE:
                ConsoleClient.launch();
                break;

            case NEW_CONSOLE:
                new ConsoleApplication().run();
                break;

            case JAVAFX:
                Application.launch(PokerApplication.class, args);
                break;
        }
    }

    private static void parseArgs(String[] args) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "--console":
                case "--nogui":
                case "-c":
                    entryPoint = EntryPoint.CONSOLE;
                    break;

                case "--autostart":
                    PokerApplication.degbugAutoStart = true;
                    break;

                case "--new-console":
                    entryPoint = EntryPoint.NEW_CONSOLE;
                    break;

                default:
                    System.out.println("Unknown argument: " + arg);
                    break;
            }
        }
    }

    public static boolean isAutostart() {
        return autostart;
    }
}
