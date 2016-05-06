/**
 * Setting up the stage, and default start scene
 * May implement alt start...
 */

package org.gruppe2.ui.javafx;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import org.gruppe2.Main;
import org.gruppe2.ui.javafx.ingame.Game;
import org.gruppe2.ui.javafx.ingame.GameScene;
import org.gruppe2.ui.javafx.menu.Intro;

public class PokerApplication extends Application {
    private final static int width = 1280;
    private final static int height = 768;
    private static StackPane root = new StackPane(); // Setting global root. Will only change scenes.

    private static ObjectProperty<Font> font ;

    /**
     * Controllers will need to get current root to change scenes
     *
     * @return root
     */
    public static StackPane getRoot() {
        return root;
    }

    public static Font getFont() {
        return font.get();
    }

    public static ObjectProperty<Font> fontProperty() {
        return font;
    }

    public static void setFont(Font font) {
        PokerApplication.font.set(font);
    }

    public static int getHeight() {
        return height;
    }

    @Override
    public void start(Stage stage) throws Exception {

        startValues(stage);
        setStartScene(stage);
    }

    private void startValues(Stage stage) {
        stage.setTitle("PokerPro16");
        stage.getIcons().add(new Image("/images/ui/icon.png"));
        stage.setOnCloseRequest(e -> System.exit(1));
    }

    /**
     * Set up scene and stage Starts the intro No global stylesheet in javaFX 8
     * stage, only on every scene
     */
    private void setStartScene(Stage stage) {
        if (Main.isAutostart()) {
            Game.autostart();
            root.getChildren().add(new GameScene());
        } else {
            root.getChildren().add(new Intro());
        }

        Scene scene = new Scene(root, width, height, false, SceneAntialiasing.BALANCED);
        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm());
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.setScene(scene);
        stage.show();
    }

    public static void launch(String []args) {
        Application.launch(PokerApplication.class, args);
    }
}
