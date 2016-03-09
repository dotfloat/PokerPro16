package org.gruppe2.frontend;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

public class MouseHandler {
	double mouseAnchorX;
	double mouseAnchorY;
	Cam cam;
	int mouseOldX = 0;
	int mouseOldY = 0;
	int mousePosX = 0;
	int mousePosY = 0;
	int mouseDeltaX;
	int mouseDeltaY;

	DoubleProperty zoom = new SimpleDoubleProperty(400);

	public MouseHandler(Painter mainFrame, Cam cam) {
		this.cam = cam;
		scrollHandler(mainFrame);
		// mouse(mainFrame);

	}

	public void scrollHandler(Painter mainFrame) {
		mainFrame.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (event.getDeltaY() > 0)
					mainFrame.setScale(mainFrame.getScale() + 1);

				else if (event.getDeltaY() < 0) {
					if (mainFrame.getScale() > 1) // Avoids divide by zero
						mainFrame.setScale(mainFrame.getScale() - 1);
				}

			}
		});
	}
	// public void clickHandler(Painter mainFrame){
	// mainFrame.setOnMouseClicked(new EventHandler<MouseEvent>(){
	// @Override
	// public void handle (MouseEvent event){
	// if(event.isPrimaryButtonDown())
	// mainFrame.scale += 1;
	//
	// else if(event.isSecondaryButtonDown())
	// mainFrame.scale -= 1;
	//
	//
	// }
	// });
	// }
	// public void mouse(Painter mainFrame){
	//
	//
	// mainFrame.setOnMouseDragged(new EventHandler<MouseEvent>() {
	// public void handle(MouseEvent me) {
	// mouseOldX = mousePosX;
	// mouseOldY = mousePosY;
	// mousePosX = (int) me.getX();
	// mousePosY = (int) me.getY();
	// mouseDeltaX = mousePosX - mouseOldX;
	// mouseDeltaY = mousePosY - mouseOldY;
	// if (me.isAltDown() && me.isShiftDown() && me.isPrimaryButtonDown()) {
	// cam.rz.setAngle(cam.rz.getAngle() - mouseDeltaX);
	// }
	// else if (me.isAltDown() && me.isPrimaryButtonDown()) {
	// cam.ry.setAngle(cam.ry.getAngle() - mouseDeltaX);
	// cam.rx.setAngle(cam.rx.getAngle() + mouseDeltaY);
	// }
	// else if (me.isAltDown() && me.isSecondaryButtonDown()) {
	// double scale = cam.s.getX();
	// double newScale = scale + mouseDeltaX*0.01;
	// cam.s.setX(newScale); cam.s.setY(newScale); cam.s.setZ(newScale);
	// }
	// else if (me.isAltDown() && me.isMiddleButtonDown()) {
	// cam.t.setX(cam.t.getX() + mouseDeltaX);
	// cam.t.setY(cam.t.getY() + mouseDeltaY);
	// }
	// }
	// });
	// }

}
