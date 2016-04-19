package org.gruppe2.ui.javafx;

import javafx.animation.*;
import javafx.beans.NamedArg;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Petter on 18/04/2016. This class creates falling confetti! Oh joy.
 */
public class ConfettiOrMoney extends Pane {

    public ConfettiOrMoney(@NamedArg("size") int size, @NamedArg("isConfetti") boolean value) {
        Resources.loadFXML(this);
        if (value) getChildren().addAll(fallingNodes(size));
        else getChildren().addAll(fallingMoney(size));
    }


    private List<Node> fallingMoney(int size) {
        ArrayList<Node> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ImageView money = createMoney();
            fallingAnimation(money);
            setFlipAnimation(money);
            list.add(money);
        }
        return list;
    }

    private List<Node> fallingNodes(int size) {
        ArrayList<Node> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Node node = drawRectangle();
            fallingAnimation(node);
            list.add(node);
        }
        setRotateAnimation(list);
        return list;
    }

    private void fallingAnimation(Node node) {
        double startX = Math.random() * 2000;
        double startY = -Math.random() * PokerApplication.getHeight();
        Path path = new Path(new MoveTo(startX, startY), new LineTo(startX, PokerApplication.getHeight()*2));
        PathTransition pathTransition = new PathTransition(Duration.millis(Math.random() * 5000 + 2000), path, node);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        if (node instanceof Rectangle) {
            pathTransition.setCycleCount(1);
            pathTransition.setOnFinished(finishHim -> {
                getChildren().remove(node);
            });
        } else pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.play();
    }

    private void setRotateAnimation(List<Node> nodes) {
        for (Node node : nodes) {
            RotateTransition rt = new RotateTransition(Duration.millis(Math.random() * 3000 + 2000), node);
            if (Math.random() > 0.5) rt.setByAngle(-360);
            else rt.setByAngle(360);
            rt.setDelay(Duration.ZERO);
            rt.setCycleCount(Animation.INDEFINITE);
            rt.play();
        }
    }

    private void setFlipAnimation(Node node) {
        RotateTransition rotator = new RotateTransition(Duration.millis(node.opacityProperty().get() * 3700 + 300), node);
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
        System.out.println(rectangle.getOpacity());
        rectangle.widthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05 * opacity));
        rectangle.heightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.02 * opacity));
        return rectangle;
    }

    private ImageView createMoney() {
        double opacity = ThreadLocalRandom.current().nextDouble(0.2, 1);
        if (Math.random() > 0.98) opacity = 0.99;
        ImageView money = new ImageView(new Image("/images/ui/moneyS.png"));
        money.opacityProperty().setValue(opacity + 0.2);
        money.preserveRatioProperty().setValue(true);
        money.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.1 * opacity));
        return money;
    }
}
