package org.uib112g2;

import javafx.application.Application;

public class MainClass {
    private static boolean noGUI = false;

    public static void main(String[] args) {
        parseArgs(args);

        if (noGUI) {
            System.out.println("Using nogui: Hello, world!");
        } else {
            Application.launch(JavaFXApp.class);
        }
    }

    public static void parseArgs(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "-nogui":
                    noGUI = true;
                    break;

                default:
                    break;
            }
        }
    }
}
