package org.gruppe2.frontend;



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.gruppe2.frontend.Card.Suit;
/**
 * Current main gui class, and also the mainClass
 * The game loop is in PokerGame
 * @author Håkon
 *
 */
public class GUI extends Application {
	// Position variables
	static int x;
	static int x_max;
	static int x_min;
	static int y;
	static int y_max;
	static int y_min;
	
	private static int scale = 100;
	private int step;
	//booleans
	private boolean paused;
	
	
	// Scene objects
	private Painter mainFrame;
	Pane creations, statusBar;
	PokerGame pokerGame;
	// Light, timer, mousehandler
	private AnimationTimer timer;
	
	
	// Cell lists for optimization
	int numberOfcellLists;
	
	
	public GUI() {}

	@Override
	/*
	 * Pseudo constructor init
	 * 
	 * @see javafx.application.Application#init()
	 */
	public void init() {

		setWindowSize(800, 600);
		setStep(0);
	}
	/**
	 * This class creates all the scene objects, read about scenes javafx for more.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Create stage
		primaryStage.setTitle("PokerPro 2016");
		Group root = new Group();
		Scene scene = new Scene(root, x, y);
		
		// Canvas creation
		Canvas canvas = new Canvas(x_max, y_max);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();	

		// Create nodes
		setMainFrame(new Painter(pokerGame, this));
		
		creations = new Pane();
		statusBar = new Pane();
		BorderPane border = new BorderPane();

		// Create node buttons
		MakeButtons buttons = new MakeButtons();
		buttons.makeButton(border, this, root);

		setInitialChildrenToRoot(border, canvas, root);

		startShow(root, scene, primaryStage, gc);
		
	    mainFrame.createCardImage(new Card(2, Suit.CLUBS));
		
		pokerGame = new PokerGame(this);
		
		Thread th = new Thread(pokerGame);
		th.start();

	}
	
	/**
	 * This event is launched for each round of the game, it simulates the GUI round, it checks the root's children and draws them, if 
	 * any new children.
	 * @param gc
	 */
	public void launchAnimation(GraphicsContext gc) {
		setTimer(new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				
				
				getMainFrame().paint();
				
				setStep(getStep() + 1);
			}
		});
		getTimer().start();
		setPaused(false);
		
	}
	
	/**
	 * This method connects the scene with its children.
	 */
	public void setInitialChildrenToRoot(BorderPane border, Canvas canvas, Group root) {
		getMainFrame().getChildren().add(canvas);
		border.setCenter(getMainFrame());
		root.getChildren().add(border);
	}

	public void startShow(Group root, Scene scene, Stage primaryStage, GraphicsContext gc) {
		// start show
		primaryStage.setScene(scene);
		primaryStage.show();
		launchAnimation(gc);
	}
	/**
	 * Sets size of window
	 * @param x
	 * @param y
	 */
	@SuppressWarnings("static-access")
	public void setWindowSize(int x, int y) {
		// Width
		this.x = x;
		x_max = x - (getScale() * 2);
		x_min = getScale();
		// Height
		this.y = y;
		y_max = y - (getScale() * 2);
		y_min = getScale();
	}
	/**
	 * Sets x and y:
	 * @param a, length of window.
	 * @param b, height of window.
	 */
	public void setXY(int a, int b) {
		x = a;
		y = b;
	}

	/**
	 * Checks if game is paused.
	 * @return
	 */
	public boolean isPaused() {
		return paused;
	}
	/**
	 * Pauses the game.
	 * @param paused
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	/**
	 * Gets current step(number of redraws)
	 * @return
	 */
	public int getStep() {
		return step;
	}
	/**
	 * Sets the reDrawStep, only used to reset to 0.
	 * @param step
	 */
	public void setStep(int step) {
		this.step = step;
	}
	/**
	 * Restart of mainFrame, occurs if table is reset.
	 */
	public void restart() {
	
	}
	/**
	 * Gets the timer that controlls gui refresh rate
	 */
	public AnimationTimer getTimer() {
		return timer;
	}
	/**
	 * Sets the timer that controlls gui refresh rate
	 *
	 */
	public void setTimer(AnimationTimer timer) {
		this.timer = timer;
	}
	/**
	 * Gets the frame that paints the board, players, cards etc.
	 * @return
	 */
	public Painter getMainFrame() {
		return mainFrame;
	}
	/**
	 * Sets the frame that paints the board, players, cards etc.
	 */
	public void setMainFrame(Painter mainFrame) {
		this.mainFrame = mainFrame;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	/**
	 * Gets the scale that gives the ratio between window size and size of board picture.
	 * The smaller it is, the less of the window will be the board
	 * @return
	 */
	public static int getScale() {
		return scale;
	}
	/**
	 * Sets the scale that gives the ratio between window size and size of board picture.
	 * The smaller it is, the less of the window will be the board
	 * @return
	 */
	public static void setScale(int scale) {
		GUI.scale = scale;
	}
	
	/**
	 * Main method, calls PokerGame as a new thread in .start().
	 * @param args
	 */
	public static void main(String[] args) {
    	Application.launch();
		
	}
}
