package org.gruppe2.frontend;



import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class Cam extends PerspectiveCamera {
	Translate t = new Translate();
	Translate p = new Translate();
	Translate ip = new Translate();
	Rotate rx = new Rotate();

	{
		rx.setAxis(Rotate.X_AXIS);
	}

	Rotate ry = new Rotate();

	{
		ry.setAxis(Rotate.Y_AXIS);
	}

	Rotate rz = new Rotate();

	{
		rz.setAxis(Rotate.Z_AXIS);
	}

	Scale s = new Scale();

	public Cam(Painter mainFrame, Group root, GUI gui) {
		super();
		getTransforms().addAll(t, p, rx, rz, ry, s, ip);
		makeLights(root, gui);
	}

	public void makeLights(Group root, GUI gui) {
		PointLight light;
		// Lights
		light = new PointLight();
		light.setTranslateX(0);
		light.setTranslateY(0);
		light.setTranslateZ(-100000 / GUI.getScale());
		root.getChildren().add(light);
	}
}
