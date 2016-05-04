package org.gruppe2.ui.javafx.ingame;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import org.gruppe2.game.event.PlayerPostActionEvent;
import org.gruppe2.game.helper.GameHelper;
import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Handler;
import org.gruppe2.game.session.Helper;
import org.gruppe2.ui.Resources;
import org.gruppe2.ui.javafx.PokerApplication;

public class Pot extends Label {

	static ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
	@Helper
    private GameHelper gameHelper;
    @Helper
    private RoundHelper roundHelper;
    
	public Pot() {
		Resources.loadFXML(this);
		Game.setAnnotated(this);
		setBindings();
	}
	private void setBindings() {
		minHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.075));
		maxHeightProperty().bind(PokerApplication.getRoot().heightProperty().multiply(0.075));
		this.maxWidthProperty().bind(
				PokerApplication.getRoot().widthProperty().multiply(0.2));
		this.fontProperty().bind(fontTracking);
		this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
                fontTracking.set(Font.font(newWidth.doubleValue() / 12));
            }
        });
	
}
	public void updatePot(int value){
		this.setText("Total Pot: "+String.valueOf(value)+" CHIPS");
	}
	
	@Handler
	public void getPotHander(PlayerPostActionEvent playerPostActionEvent){
		updatePot(roundHelper.getModel().getPot());
	}
}
