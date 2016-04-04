package org.gruppe2.ui.old_javafx;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Makes top game controll buttons, currently not used.
 *
 * @author htj063
 */
public class MakeButtons {
    Button restart, pause, rotate, exit;
    GridPane grid = new GridPane();

    public void makeButton(BorderPane border, GUI gui, Group root) {


        // Buttons
        restart = new Button("Restart");
        restart.setOnAction(e -> {
            gui.getScene().getStylesheets().clear();
            gui.getScene().getStylesheets().add("/css/style.css");
        });

        pause = new Button("Pause");
        pause.setOnAction(e -> {
            if (!gui.isPaused()) {
                pause.setText("fortsett");
                gui.getTimer().stop();
                gui.setPaused(true);
            } else {
                pause.setText("Pause");
                gui.getTimer().start();
                gui.setPaused(false);
            }
        });


        exit = new Button("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Exit");
                System.exit(0);
            }
        });

        // Add to parrent scene
        grid.add(restart, 1, 1);
        grid.add(pause, 2, 1);
        grid.add(exit, 4, 1);
        border.setTop(grid);

    }


}
