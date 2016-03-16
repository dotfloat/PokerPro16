package org.gruppe2.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Card.Suit;
import org.gruppe2.backend.Player;
import org.gruppe2.backend.PokerGame;
/**
 * Class that paints the objects.
 * @author htj063
 *
 */
public class Painter extends Pane {

	
	//Used to make sure the drawing are correct size compared to screen. 
	//ig. 120 vdw = 12 vdw on screen if scale = 10
	private int scale = 10;
	GUI gui;
	Image backGround;
	
	ImageView bg;
	//Player positions
	Label playerPosition0;
	Label playerPosition1;
	Label playerPosition2;
	Label playerPosition3;
	Label playerPosition4;
	Label playerPosition5;
	
	//Total pot
	Label totalPot;
	
	ArrayList<Card> communityCards;
	
	
	public Painter(GUI gui) {
		super();
		this.gui = gui;
		setBackGround("/PokertableWithLogo.png");
	}

	/**
	 * Paints the updatet objects in scene.
	 */
	public void paint() {
		
//		doTasks(game.getTable());
		
	}

	public void setBackGround(String name) {
		
		this.setStyle("-fx-background-color: #662200");
		backGround = new Image(getClass().getResourceAsStream(name),600,0,true,true);
		bg = new ImageView(backGround);
		updateBackGround();
		this.getChildren().add(bg);
	}
	
	public void updateBackGround(){
		bg.setFitWidth(gui.getWidth()-gui.getWidth()*0.3);
		bg.setFitHeight(gui.getHeight()-gui.getHeight()*0.3);
		bg.setLayoutX(gui.getWidth()/2-bg.getFitWidth()/2);
		bg.setLayoutY(gui.getHeight()/2-bg.getFitHeight()/1.55);
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
	
	
	/**
	 * Get players card, and place on top (last) in scene
	 * 
	 */
	public void paintPocketCards() {
		Card card1 = new Card(6, Suit.HEARTS);
		Card card2 = new Card(5, Suit.HEARTS);
		
		ImageView view1 = createCardImage(card1);
		ImageView view2 = createCardImage(card2);
		
		gui.root.getChildren().add(view1);
		gui.root.getChildren().add(view2);
		
		view1.setLayoutX(gui.getWidth()*0.85);
		view1.setLayoutY(gui.getHeight()*0.7);
		view2.setLayoutX(gui.getWidth()*0.86);
		view2.setLayoutY(gui.getHeight()*0.7);
		
		view1.setFitWidth(gui.getWidth()*0.1);
		view1.setFitHeight(gui.getWidth()*0.1);
		view2.setFitWidth(gui.getWidth()*0.1);
		view2.setFitHeight(gui.getWidth()*0.1);
		
		view1.setRotate(340);
		view2.setRotate(0);
		
	}
	
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	/**
	 * Gets the specific card as an ImageView
	 * @param card
	 * @return
	 */
	public ImageView createCardImage(Card card){
		String name = "/" + getCardName(card)+".png";
		
		Image image = new Image(getClass().getResourceAsStream(name),200,0,true,true);
		
		ImageView cardPic = new ImageView(image);
		
		return cardPic;
	}


	public void showCommunityCards(ArrayList<Card> communityCards) {
		if(communityCards == null) return;
		this.communityCards = communityCards;
		if(communityCards.size() == 0) return;
		
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		    	
		    	Double cardOffset = gui.getWidth()*0.05;
				int cardsToShow = communityCards.size();
				for (int i = 0; i < cardsToShow; i++) {
					
					Card c = (Card) communityCards.get(i);
					ImageView cardImage = createCardImage(c);
					cardImage.setPreserveRatio(true);
					cardImage.setFitHeight(gui.getWidth()*0.045);
					
					cardImage.setLayoutX(gui.getWidth()*0.4 + (cardOffset*i));
					cardImage.setLayoutY(gui.getHeight()*0.4);
					
					getChildren().add(cardImage);
				}
		    }
		});
	}
	public void clearCommunityCards(){
		if(communityCards != null){
			if(communityCards.size() == 0) return;
			
			else
				Platform.runLater(new Runnable(){
				    @Override
				    public void run() {
				    	getChildren().removeAll(communityCards);
				    }});
		}
	}
	
	
	public void updateTablePot(){
		totalPot.setText("POT:"+gui.getClient().getSession().getTable().getPot()+" CH");
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
	
	
	
	 /**
     * Method so GUI can find card easily
     * @return
     */
    public String getCardName(Card card) {
    	String finalName = String.valueOf(card.getSuit().toString().toLowerCase().charAt(0));
    	
    	if(card.getFaceValue() > 9)
    		finalName += card.getFaceValue();
    	else 
    		finalName += "0"+card.getFaceValue();
    	
        return finalName;
    }

	public void paintPlayerInfoBox(PlayerInfoBox playerInfoBox, double x, double y){
		playerInfoBox.setLayoutX(x);
		playerInfoBox.setLayoutY(y);
		this.getChildren().add(playerInfoBox);
	}

	public void paintAllPlayers(List<PlayerInfoBox> playerInfoBoxes){
		double xStep = gui.getWidth()/15;
		double yStep = gui.getHeight()/11;
		double y = 10;
		paintPlayerInfoBox(playerInfoBoxes.get(0), xStep*2, y);
		paintPlayerInfoBox(playerInfoBoxes.get(1), xStep*6.5, y);
		paintPlayerInfoBox(playerInfoBoxes.get(2), xStep*11, y);
		paintPlayerInfoBox(playerInfoBoxes.get(3), xStep*0.5, yStep*2);
		paintPlayerInfoBox(playerInfoBoxes.get(4), xStep*12.5, yStep*2);
		paintPlayerInfoBox(playerInfoBoxes.get(5), xStep*0.5, yStep*4);
		paintPlayerInfoBox(playerInfoBoxes.get(6), xStep*12.5, yStep*4);
		paintPlayerInfoBox(playerInfoBoxes.get(7), xStep, yStep*6);
		paintPlayerInfoBox(playerInfoBoxes.get(8), xStep*12.1, yStep*6);
	}
	/**
	 * This is the drawing of the pot on the scene.
	 */
	public void drawPot() {
		totalPot = new Label("POT:"+gui.getClient().getSession().getTable().getPot()+" CH");
		totalPot.setLayoutX(gui.getWidth()/2);
		totalPot.setLayoutY(gui.getHeight()*0.6);
		
//		ImageView potImage = new ImageView(new Image(getClass().getResourceAsStream("/pot.png")));
//		getChildren().add(potImage);
		
		getChildren().add(totalPot);
	}
}
