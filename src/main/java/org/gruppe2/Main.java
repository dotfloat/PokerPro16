package org.gruppe2;

import javafx.application.Application;
import org.gruppe2.ui.console.ConsoleClient;
import org.gruppe2.ui.javafx.PokerApplication;
import org.gruppe2.ui.old_javafx.GUI;

public class Main {
    private enum EntryPoint {
        CONSOLE, OLD_JAVAFX, JAVAFX
    }

    private static EntryPoint entryPoint = EntryPoint.JAVAFX;

    public static void main(String[] args) {
        parseArgs(args);

        switch (entryPoint) {
            case CONSOLE:
                ConsoleClient.launch();
                break;
            case OLD_JAVAFX:
                Application.launch(GUI.class, args);
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

                case "--old-javafx":
                case "--old-gui":
                case "-o":
                    entryPoint = EntryPoint.OLD_JAVAFX;
                    break;

                default:
                    System.out.println("Unknown argument: " + arg);
                    break;
            }
        }
    }
}
