package org.gruppe2.ui.javafx.menu;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.gruppe2.ui.UIResources;

import java.util.ArrayList;

/**
 * Created by Åsmund on 28/04/2016.
 */
public class Credits extends StackPane{
    public static ArrayList<Node> nodes;
    public static SequentialTransition sequence;

    public Credits(){
        nodes  = new ArrayList<Node>();
        sequence = new SequentialTransition();
        UIResources.loadFXML(this);
        playCredits();
    }

    private void playCredits() {

        SequentialTransition sequence = new SequentialTransition();
        sequence.setCycleCount(Animation.INDEFINITE);


        nodes.add(new Label("Donald Trump"));
        nodes.add(new Label("Bernie Sanders"));

        addObjectsToCreditSequence(sequence, nodes);
        sequence.play();

    }

    private void addObjectsToCreditSequence(SequentialTransition sequence, ArrayList<Node> nodes){
        for(Node node: nodes){
            addObject(sequence, node);
        }
    }

    private void addObject(SequentialTransition sequence, Node node){

        Path path = new Path(new MoveTo(-2000, 0), new LineTo(60, 0));
        Path path1 = new Path(new MoveTo(60, 0), new LineTo(2000, 0));

        PathTransition transition = createPathTransition(path, node);
        PathTransition transition1 = createPathTransition(path1, node);

        sequence.getChildren().add(transition);
        sequence.getChildren().add(new PauseTransition(Duration.millis(2000)));
        sequence.getChildren().add(transition1);

        getChildren().add(node);


    }

    private PathTransition createPathTransition(Path path, Node node){
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setCycleCount(1);

        pathTransition.setPath(path);

        pathTransition.setNode(node);

        return pathTransition;
    }

    public static void stop(){
        sequence.stop();
        nodes.clear();
    }
}
