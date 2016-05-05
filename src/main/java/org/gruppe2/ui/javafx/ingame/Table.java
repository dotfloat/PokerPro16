package org.gruppe2.ui.javafx.ingame;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.gruppe2.game.Player;
import org.gruppe2.game.event.PlayerJoinEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;

import java.util.ArrayList;
import java.util.List;

public class Table extends Pane {
    @Helper
    private GameHelper game;

    private final double ratio = 2464.0 / 1368.0;
    private final double logicalWidth = 308;
    private final double logicalHeight = 171;
    private final double tableScale = 0.6;

    private ObjectProperty<Font> font = new SimpleObjectProperty<>();

    private DoubleProperty scale = new SimpleDoubleProperty();

    private DoubleProperty fitWidth = new SimpleDoubleProperty();
    private DoubleProperty fitHeight = new SimpleDoubleProperty();

    private List<PlayerInfoBox> players = new ArrayList<>();

	public Table() {
        Game.setAnnotated(this);

        fitWidth.addListener((o, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            double height = fitHeight.get();

            if (width / ratio < height) {
                setMaxWidth(width);
                setMaxHeight(width / ratio);
            } else {
                setMaxWidth(height * ratio);
                setMaxHeight(height);
            }
        });

        fitHeight.addListener((o, oldVal, newVal) -> {
            double width = fitWidth.get();
            double height = newVal.doubleValue();

            if (height * ratio < width) {
                setMaxWidth(height * ratio);
                setMaxHeight(height);
            } else {
                setMaxWidth(width);
                setMaxHeight(width / ratio);
            }
        });

        scale.addListener((o, oldVal, newVal) -> font.setValue(Font.font(newVal.doubleValue() * 4.0)));

        scale.bind(maxWidthProperty().divide(logicalWidth));

        ImageView tableImage = new ImageView("/images/ui/PokertableWithLogo.png");
        tableImage.fitWidthProperty().bind(maxWidthProperty().multiply(tableScale));
        tableImage.fitHeightProperty().bind(maxHeightProperty().multiply(tableScale));
        tableImage.layoutXProperty().bind(translateX(tableImage.fitWidthProperty(), 0));
        tableImage.layoutYProperty().bind(translateY(tableImage.fitHeightProperty(), 0));
        getChildren().add(tableImage);

        for (int i = 0; i < game.getModel().getMaxPlayers(); i++) {
            double n = game.getModel().getMaxPlayers();

            PlayerInfoBox p = new PlayerInfoBox();
            p.fontProperty().bind(font);
            p.maxWidthProperty().bind(scale.multiply(45));
            p.maxHeightProperty().bind(scale.multiply(15));
            setPositionAroundTable(p, p.widthProperty(), p.heightProperty(), (i + 1.0) / (n + 1.0), 1.2);
            getChildren().add(p);
            players.add(p);
        }
	}

    @Handler
    public void onPlayerJoin(PlayerJoinEvent event) {
        List<Player> gamePlayers;

        synchronized (gamePlayers = game.getPlayers()) {
            for (int i = 0; i < gamePlayers.size(); i++) {
                if (gamePlayers.get(i).getUUID().equals(event.getPlayer().getUUID())) {
                    players.get(i).setPlayerUUID(event.getPlayer().getUUID());

                    return;
                }
            }
        }
    }

    public double getFitWidth() {
        return fitWidth.get();
    }

    public DoubleProperty fitWidthProperty() {
        return fitWidth;
    }

    public void setFitWidth(double fitWidth) {
        this.fitWidth.set(fitWidth);
    }

    public double getFitHeight() {
        return fitHeight.get();
    }

    public DoubleProperty fitHeightProperty() {
        return fitHeight;
    }

    public void setFitHeight(double fitHeight) {
        this.fitHeight.set(fitHeight);
    }

    private DoubleBinding translateX(ReadOnlyDoubleProperty width, double x) {
        return scale.multiply(tableScale * x).add(widthProperty().divide(2)).subtract(width.divide(2));
    }

    private DoubleBinding translateY(ReadOnlyDoubleProperty height, double y) {
        return scale.multiply(tableScale * y).add(heightProperty().divide(2)).subtract(height.divide(2));
    }

    private void setPositionAroundTable(Node node, ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height, double x, double dist) {
        final double rectWidth = (logicalWidth - logicalHeight);
        final double rectHeight = logicalHeight;
        final double theta = Math.PI;

        dist *= rectHeight / 2.0;

        double arcLength = theta * dist;
        double circumference = rectWidth + 2.0 * arcLength;

        double position = x * circumference;

        if (position < arcLength) {
            double angle = (position / arcLength) * theta - theta;

            node.layoutXProperty().bind(translateX(width, Math.sin(angle) * dist - rectWidth / 2.0));
            node.layoutYProperty().bind(translateY(height, -Math.cos(angle) * dist));
        } else if (position > rectWidth + arcLength) {
            double angle = (position - rectWidth) / arcLength * theta - theta;

            node.layoutXProperty().bind(translateX(width, Math.sin(angle) * dist + rectWidth / 2.0));
            node.layoutYProperty().bind(translateY(height, -Math.cos(angle) * dist));
        } else {
            double offsetX = position - arcLength;

            node.layoutXProperty().bind(translateX(width, offsetX - rectWidth / 2.0));
            node.layoutYProperty().bind(translateY(height, -dist));
        }
    }
}
