package org.gruppe2.ui.javafx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import org.gruppe2.ui.Resources;

public class Pot extends Label {

	static ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
	
	public Pot() {
		Resources.loadFXML(this);
		
		setBindings();
	}
	private void setBindings() {
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
}
