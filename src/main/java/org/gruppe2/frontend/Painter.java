package org.gruppe2.frontend;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Class that paints the objects.
 * @author htj063
 *
 */
public class Painter extends Pane {

	PokerGame game;
	//Used to make sure the drawing are correct size compared to screen. 
	//ig. 120 vdw = 12 vdw on screen if scale = 10
	private int scale = 10;
	GUI gui;
	Image backGround;
	Canvas canvas;
	
	//Player positions
	Label playerPosition0;
	Label playerPosition1;
	Label playerPosition2;
	Label playerPosition3;
	Label playerPosition4;
	Label playerPosition5;
	
	//Total pot
	Label totalPot;
	
	
	public Painter(PokerGame game, GUI gui) {
		super();
		this.game = game;
		this.gui = gui;
		setBackGround("/tableAndBackGround.png");
	}

	/**
	 * Paints the updatet objects in scene.
	 */
	public void paint() {
		
//		doTasks(game.getTable());
		
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
	
	public void showCardsOnHand(ArrayList<Player> listOfPlayers){
		int playerNumber = 0;
		for(Player player : listOfPlayers){
			ImageView card1 = createCardImage(player.getCard1());
			ImageView card2 = createCardImage(player.getCard2());
			
			if(playerNumber == 0){
				card1.setLayoutX(150);
				card1.setLayoutY(290);
				card2.setLayoutX(150);
				card2.setLayoutY(320);
			}
			else if(playerNumber == 1){
				card1.setLayoutX(250);
				card1.setLayoutY(130);
				card2.setLayoutX(250);
				card2.setLayoutY(160);
			}
			else if(playerNumber == 2){
				card1.setLayoutX(430);
				card1.setLayoutY(130);
				card2.setLayoutX(430);
				card2.setLayoutY(160);	
			}
			else if(playerNumber == 3){
				card1.setLayoutX(560);
				card1.setLayoutY(290);
				card2.setLayoutX(560);
				card2.setLayoutY(320);
			}
			else if(playerNumber == 4){
				card1.setLayoutX(430);
				card1.setLayoutY(380);
				card2.setLayoutX(430);
				card2.setLayoutY(410);
			}
			else if(playerNumber == 5){
				card1.setLayoutX(250);
				card1.setLayoutY(380);
				card2.setLayoutX(250);
				card2.setLayoutY(410);
			}
			this.getChildren().addAll(card1,card2);
			playerNumber++;
		}
	}
	
	public void reDraw(){
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
				
				getChildren().remove(playerPosition0);
				getChildren().remove(playerPosition1);
				getChildren().remove(playerPosition2);
				getChildren().remove(playerPosition3);
				getChildren().remove(playerPosition4);
				getChildren().remove(playerPosition5);
				
				setPlayersToTable(gui.pokerGame, gui);
		    }
		});
	}
	public void setPlayersToTable(PokerGame pokerGame, GUI gui) {
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		
		int playerNumber = 0;
		for (Player player : pokerGame.getPlayers()) {
			Label playerPosition = new Label(player.getName()+ " "
					+ player.getChips());
			if (playerNumber == 0) {
				playerPosition.setLayoutX(15);
				playerPosition.setLayoutY(300);
				playerPosition0 = playerPosition;
				
			}
			else if (playerNumber == 1) {
				playerPosition.setLayoutX(250);
				playerPosition.setLayoutY(40);
				playerPosition1 = playerPosition;
			}
			else if (playerNumber == 2) {
				playerPosition.setLayoutX(430);
				playerPosition.setLayoutY(40);
				playerPosition2 = playerPosition;
			}
			else if (playerNumber == 3) {
				playerPosition.setLayoutX(700);
				playerPosition.setLayoutY(300);
				playerPosition3 = playerPosition;
			}
			else if (playerNumber == 4) {
				playerPosition.setLayoutX(430);
				playerPosition.setLayoutY(500);
				playerPosition4 = playerPosition;
			}
			else if (playerNumber == 5) {
				playerPosition.setLayoutX(250);
				playerPosition.setLayoutY(500);
				playerPosition5 = playerPosition;
			}
			playerPosition.setTextFill(Color.HOTPINK);
			playerPosition.setFont(new Font(15));
			getChildren().add(playerPosition);
			playerNumber++;
			}
		}
		});

	}
	
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public ImageView createCardImage(Card card){
		String name = "/"+card.toStringGUI()+".png";
		
		Image image = new Image(getClass().getResourceAsStream(name),30,80,true,true);
		
		ImageView cardPic = new ImageView(image);
		
		return cardPic;

	}

	public void showCommunityCards(ArrayList<Card> communityCards, int low, int top) {
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
				
				for(int currentCard = low; currentCard <= top; currentCard++ ){
					Card card = communityCards.get(currentCard);
					ImageView cardImage = createCardImage(card);
					
					if(currentCard == 0){
						cardImage.setLayoutX(300);
						cardImage.setLayoutY(300);
					}
					if(currentCard == 1){
						cardImage.setLayoutX(350);
						cardImage.setLayoutY(300);
					}
					if(currentCard == 2){
						cardImage.setLayoutX(400);
						cardImage.setLayoutY(300);
					}
					if(currentCard == 3){
						cardImage.setLayoutX(450);
						cardImage.setLayoutY(300);
					}
					if(currentCard == 4){
						cardImage.setLayoutX(500);
						cardImage.setLayoutY(300);
					}
					
					getChildren().add(cardImage);
				}
		    }
		});
		
	}
	
	public void updateTablePot(PokerGame pokerGame){
		getChildren().remove(totalPot);
		totalPot = new Label("Total pot:\n"+pokerGame.getTable().pot);
		totalPot.setLayoutX(400);
		totalPot.setLayoutY(100);
		
		getChildren().add(totalPot);
	}
	
	public void playerWon(Player player){
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		    	Group root = new Group();
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Declare Winner window");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(root);
				dialogStage.setScene(scene);

				GridPane grid = new GridPane();

				Label wonText = new Label("Player: "+player.toString() +" Won the game!");

				Button ok = new Button("Ok");
	
				grid.addRow(1, wonText);
				
				grid.addRow(2, ok);
				
				root.getChildren().add(grid);
				
		    	dialogStage.showAndWait();    
		    }           
		});
	}

	
	
	



}
