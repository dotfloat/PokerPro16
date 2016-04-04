package org.gruppe2.ui.old_javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
* This will be the intro screen.
*/
public class IntroScreen extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void setIntroScreen(GUI gui, Stage primaryStage, Group root) {
        setIntroBackground(gui, primaryStage, root);
        setAnimation(gui, primaryStage, root);
    }

    private void setAnimation(GUI gui, Stage primaryStage, Group root) {

    }

    private void setIntroBackground(GUI gui, Stage primaryStage, Group root) {
        Canvas canvas = new Canvas(gui.getWidth(), gui.getHeight());
        root.getChildren().add(canvas);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Color.WHITE);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
