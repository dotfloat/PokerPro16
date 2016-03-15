package org.gruppe2.frontend;



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.gruppe2.ai.AIClient;
import org.gruppe2.backend.Card;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.Player;

/**
 * Current main gui class, and also the mainClass
 * The game loop is in PokerGame
 * @author H�kon
 *
 */
public class GUI extends Application {
	// Position variables
	static int width;
	static int width_max;
	static int width_min;
	static int height;
	static int height_max;
	static int height_min;

	private static int scale = 100;
	private int step;
	private GUIClient client;
	//booleans
	private boolean paused;
	
	
	// Scene objects
	private Painter mainFrame;
	Pane creations, statusBar;
	BorderPane border;
	Scene scene;
	// Light, timer, mousehandler
	private AnimationTimer timer;
	
	
	// Cell lists for optimization
	int numberOfcellLists;
	//Game
	GameSession gameSession;
	
	public GUI() {}

	
	/**
	 * Pseudo constructor init
	 * 
	 * @see javafx.application.Application#init()
	 */
	@Override
	public void init() {
		setWindowSize(1728, 972);
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
		scene = new Scene(root, width, height);
		scene.getStylesheets().add("/css/style.css");
		// Canvas creation
		Canvas canvas = new Canvas(width, height_max);
		GraphicsContext gc = canvas.getGraphicsContext2D();	
		border = new BorderPane();
		
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		
		setMainFrame(new Painter(this));
		
		// Create node buttons
		MakeButtons buttons = new MakeButtons();
		buttons.makeButton(border, this, root);
		
		setInitialChildrenToRoot(border, canvas, root);

		startShow(root, scene, primaryStage, gc);
		
	   

		gameSession = new GameSession();
		client = new GUIClient(gameSession, this);
		gameSession.addPlayer("CoolestPerson", client);
		gameSession.addPlayer("Anne", new AIClient(gameSession));
		gameSession.addPlayer("Bob", new AIClient(gameSession));
        gameSession.addPlayer("Chuck", new AIClient(gameSession));
        gameSession.addPlayer("Dennis", new AIClient(gameSession));
        gameSession.addPlayer("Emma", new AIClient(gameSession));
		Thread th = new Thread(client);
		th.start();
		// Create nodes
		
		ChoiceBar.showChoices(this, gameSession.getPlayers().get(0));

		Player testPlayer = new Player("Mr. Test", 2000, client);
		testPlayer.setBet(40);
		PlayerInfoBox playerInfoBox = new PlayerInfoBox(testPlayer);
		mainFrame.paintPlayerInfoBox(playerInfoBox);
		ImageView cardImage = mainFrame.createCardImage(new Card(3, Card.Suit.DIAMONDS));
		cardImage.setLayoutX(300);
		cardImage.setLayoutY(300);
		mainFrame.getChildren().add(cardImage);


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
		this.width = x;
		width_max = width - (getScale() * 2);
		width_min = getScale();
		// Height
		this.height = y;
		height_max = height - (getScale() * 2);
		height_min = getScale();
	}
	/**
	 * Sets width, height;
	 * @param a, length of window.
	 * @param b, height of window.
	 */
	public void setXY(int a, int b) {
		width = a;
		height = b;
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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

	public Scene getScene() {
		return scene;
		
	}

	public GUIClient getClient() {
		return client;
	}

	public BorderPane getBorder() {
		return border;
	}
}
