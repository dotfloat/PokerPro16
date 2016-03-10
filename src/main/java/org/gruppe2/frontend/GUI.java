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
	TestSimulator simulator;
	// Light, timer, mousehandler
	private AnimationTimer timer;
	
	MouseHandler mouseActions;
	// Cell lists for optimization
	int numberOfcellLists;
	
	
	public GUI() {
	}

	@Override
	/*
	 * Pseudo constructor init
	 * 
	 * @see javafx.application.Application#init()
	 */
	public void init() {

		setWindowSize(800, 600);
		
		simulator = new TestSimulator(this);
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
		setMainFrame(new Painter(simulator, this));
		
		creations = new Pane();
		statusBar = new Pane();
		BorderPane border = new BorderPane();

		// Create node buttons
		MakeButtons buttons = new MakeButtons();
		buttons.makeButton(border, simulator, this, root);

		setInitialChildrenToRoot(border, canvas, root);

		startShow(root, scene, primaryStage, gc);

	}
	/**
	 * This event is launched for each round of the game, it simulates the round, paints the update.
	 * @param gc
	 */
	public void launchAnimation(GraphicsContext gc) {
		setTimer(new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				
				
				simulator.playRound();
				
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
		Cam cam = new Cam(getMainFrame(), root, this);
		scene.setCamera(cam);
		mouseActions = new MouseHandler(getMainFrame(), cam);
		primaryStage.setScene(scene);
		primaryStage.show();
		launchAnimation(gc);
	}

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

	public void setXY(int a, int b) {
		x = a;
		y = b;
	}

	public static void main(String[] args) {
		Application.launch();
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void restart() {
		simulator.restart(x_max, y_max, x_min, y_min);
	}

	public AnimationTimer getTimer() {
		return timer;
	}

	public void setTimer(AnimationTimer timer) {
		this.timer = timer;
	}

	public Painter getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(Painter mainFrame) {
		this.mainFrame = mainFrame;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static int getScale() {
		return scale;
	}

	public static void setScale(int scale) {
		GUI.scale = scale;
	}
	

}
