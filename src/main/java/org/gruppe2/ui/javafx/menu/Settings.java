package org.gruppe2.ui.javafx.menu;

import javafx.scene.layout.VBox;

import org.gruppe2.ui.UIResources;
import org.gruppe2.ui.javafx.Modal;

public class Settings extends VBox {
	
	public Settings(){
		UIResources.loadFXML(this);
	}

	public static void show() {
		Modal modal = new Modal();
		modal.setPercentSize(0.5, 0.5);
		modal.setContent(new Settings());
		modal.show();
	}
}
