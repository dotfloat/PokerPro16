package org.gruppe2.frontend;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/**
 * Class that paints the objects.
 * @author htj063
 *
 */
public class Painter extends Pane {

	TestSimulator simulator;
	//Used to make sure the drawing are correct size compared to screen. 
	//ig. 120 vdw = 12 vdw on screen if scale = 10
	private int scale = 10;
	GUI gui;
	Image backGround;
	Canvas canvas;
	public Painter(TestSimulator simulator, GUI gui) {
		super();
		this.simulator = simulator;
		this.gui = gui;
		setBackGround("tableAndBackGround.png");
	}

	/**
	 * Paints the updatet objects in scene.
	 */
	public void paint() {
		
		doTasks(simulator.getList());
		
	}

	public void setBackGround(String name) {
		backGround = new Image(getClass().getResourceAsStream(name));
		ImageView bg = new ImageView(backGround);
		bg.setFitWidth(gui.getX());
		bg.setFitHeight(gui.getY());
		this.getChildren().add(bg);
	}

	public Image getBackGround() {
		return backGround;
	}

	/**
	 * Single threaded version
	 * 
	 * @param currentAtoms
	 */
	public void doTasks(ArrayList<Card> listOfCards) {
		// Update painting
//		getChildren().clear();	

	}
	
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	



}
