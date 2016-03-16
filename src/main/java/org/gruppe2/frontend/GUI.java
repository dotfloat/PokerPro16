package org.gruppe2.frontend;



import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.gruppe2.ai.AIClient;
import org.gruppe2.backend.Card;
import org.gruppe2.backend.GameBuilder;
import org.gruppe2.backend.GameSession;
import org.gruppe2.backend.Player;

/**
 * Current main gui class, and also the mainClass
 * The game loop is in GameSession
 * @author H�kon Tjeldnes
 *
 */
public class GUI extends Application {
	// Position variables

	int width;
	int width_max;
	int width_min;
	int height;
	int height_max;
	int height_min;
	
	private static int scale = 100;
	private int step;
	
	//booleans
	private boolean paused;
	
	
	// Scene objects
	private Painter mainFrame;
	Pane creations, statusBar;
	ChoiceBar choiceBar;
	BorderPane border;
	Scene scene;
	Canvas canvas;
	Group root;
	// Light, timer, mousehandler
	private AnimationTimer timer;

	//Game
	GameSession gameSession = null;
	GUIClient client = new GUIClient(this);
	
	
	ArrayList<PlayerInfoBox> playerInfoBoxes;
	
	public GUI() {}

	
	/**
	 * Pseudo constructor init
	 * 
	 * @see javafx.application.Application#init()
	 */
	@Override
	public void init() {
		int a = (int) (1920*0.8);
		int b = (int) (1080*0.8);
		setWindowSize(a,b);
		setStep(0);
	}
	/**
	 * This class creates all the scene objects, read about scenes javafx for more.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Create stage
		primaryStage.setTitle("PokerPro 2016");
		root = new Group();
		scene = new Scene(root, width, height);
		scene.getStylesheets().add("/css/style.css");
		// Canvas creation
		canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();	
		startShow(root, scene, primaryStage, gc);
		
		newMainMenu(primaryStage,root);
		
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
				
				setStep(getStep() + 1);
			}
		});
		getTimer().start();
		setPaused(false);
	}
	
	public void startMainFrame(Stage primaryStage, Group root, Canvas canvas) {
	    canvas.setHeight(height_max);
		border = new BorderPane();
		setGUIEventHandlers(primaryStage, root);
		
		setMainFrame(new Painter(this));

		setInitialChildrenToRoot(border, canvas, root);

		testGame();

		getMainFrame().paintPocketCards();
	}




	private void testGame() {

		gameSession = new GameBuilder()
				.ai(5)
				.blinds(10, 5)
				.startMoney(100)
				.mainClient(client)
				.build();

		System.out.println("Now:"+gameSession.getPlayers().size());
		Thread th = new Thread(() -> gameSession.mainLoop());
		gameSession.addPlayer("me", client);
		gameSession.addPlayer("Anne", new AIClient(gameSession));
		gameSession.addPlayer("Bob", new AIClient(gameSession));
        gameSession.addPlayer("Chuck", new AIClient(gameSession));
        gameSession.addPlayer("Dennis", new AIClient(gameSession));
        gameSession.addPlayer("Emma", new AIClient(gameSession));

        setChoiceBar();
		Thread th = new Thread(client);
		th.start();
	
		mainFrame.drawPot();
		playerInfoBoxes = (ArrayList<PlayerInfoBox>) PlayerInfoBox.createPlayerInfoBoxes(client.getSession().getPlayers());
		mainFrame.paintAllPlayers(playerInfoBoxes);

		
	}




	private void setGUIEventHandlers(Stage primaryStage, Group root) {
		primaryStage.setOnCloseRequest(e -> System.exit(0));
//		GUI gui = this;
//		//Window resize listener
//		final ChangeListener<Number> widthListener = new ChangeListener<Number>()
//				{
//				  public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue)
//				  {
//					  
//			    	gui.setWidth(newValue.intValue());
//			    	gui.getMainFrame().updateBackGround();
//				  }
//				};
//		final ChangeListener<Number> heightListener = new ChangeListener<Number>()
//				{
//				  public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue)
//				  {
//					  
//			    	
//			    	gui.setHeight(newValue.intValue());
//			    	gui.getMainFrame().updateBackGround();
//				  }
//				};
//
//				// finally we have to register the listener
//				primaryStage.widthProperty().addListener(widthListener);
//				primaryStage.heightProperty().addListener(heightListener);
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
	public void newMainMenu(Stage primaryStage, Group root2) {
		MainMenu menu = new MainMenu();
	    menu.setMainMenu(primaryStage,root, this);
	}

	/**
	 * Sets size of window
	 * @param x
	 * @param y
	 */
	
	public void setWindowSize(int x, int y) {
		// Width
		width = x;
		width_max = width - (getScale() * 2);
		width_min = getScale();
		// Height
		height = y;
		height_max = height - (getScale() * 2);
		height_min = getScale();
	}
	/**
	 * Sets the bottom choiceBar to frame
	 */
	private void setChoiceBar() {
		choiceBar = new ChoiceBar();
		choiceBar.showChoices(this,client.getSession().getPlayers().get(0)); //Get me
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
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
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
	public void updateStageDimensions(){
		scene.getWindow().setWidth(getWidth());
		scene.getWindow().setHeight(getHeight());
		scene.getWindow().sizeToScene();
	}

	public BorderPane getBorder() {
		return border;
	}


	/**
	 * Updates gui each time a real players round is up.
	 * @param player
	 */
	public void updateGUI(Player player) {
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		    	for( PlayerInfoBox playerInfoBox : playerInfoBoxes){
		    		if(playerInfoBox.getPlayer() == player){
		    			playerInfoBox.updateInfoBox(player);
		    		}
		    	}	
				
				getMainFrame().updateTablePot();
				choiceBar.updatePossibleBarsToClick(player);
				//--->
		    }});
	}
}
