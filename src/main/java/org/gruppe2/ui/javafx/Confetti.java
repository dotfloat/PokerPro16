package org.gruppe2.ui.javafx;

import javafx.animation.*;
import javafx.beans.NamedArg;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.gruppe2.ui.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petter on 18/04/2016. This class creates falling confetti! Oh joy.
 */
public class Confetti extends Pane {

    public Confetti(@NamedArg("size") int size) {
        Resources.loadFXML(this);
        getChildren().addAll(fallingNodes(size));
    }

    private List<Node> fallingNodes(int size) {
        ArrayList<Node> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Node node = drawRectangle();
            fallingAnimation(node);
            setFlipAnimation(node);
            list.add(node);
        }
        setRotateAnimation(list);
        return list;
    }

    private void fallingAnimation(Node node) {
        double startX = Math.random() * PokerApplication.getWidth();
        double startY = -Math.random() * PokerApplication.getHeight();
        Path path = new Path(new MoveTo(startX, startY), new LineTo(startX, PokerApplication.getHeight()));
        PathTransition pathTransition = new PathTransition(Duration.millis(Math.random() * 5000 + 2000), path, node);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
    }

    private void setRotateAnimation(List<Node> nodes) {
        for (Node node : nodes) {
            RotateTransition rt = new RotateTransition(Duration.millis(Math.random() * 3000 + 2000), node);
            if (Math.random() > 0.5) rt.setByAngle(-360);
            else rt.setByAngle(360);
            rt.setCycleCount(Animation.INDEFINITE);
            rt.setDelay(Duration.ZERO);
            rt.play();
        }
    }

    private void setFlipAnimation(Node node){
        RotateTransition rotator = new RotateTransition(Duration.millis(500), node);
        rotator.setAxis(Rotate.X_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(Timeline.INDEFINITE);
        rotator.play();
    }

    private Rectangle drawRectangle() {
        double opacity = Math.random();
        Color color = new Color(Math.random(), Math.random(), Math.random(), opacity);
        Rectangle rectangle = new Rectangle(opacity * 30, opacity * 10, color);
        return rectangle;
    }
}
