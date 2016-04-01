package org.gruppe2.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.gruppe2.backend.Card;
import org.gruppe2.backend.Player;
/**
 * Class that paints the objects.
 * @author htj063
 *
 */
public class Painter extends Pane {

	
	
	GUI gui;
	Image backGround;
	ImageView bg;
	
	Label totalPot;
	ArrayList<ImageView> communityImageCards;
	
	
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
		
		this.getStyleClass().add("background");
		backGround = new Image(getClass().getResourceAsStream(name));
		bg = new ImageView(backGround);
		bg.setPreserveRatio(true);
		updateBackGround();
		this.getChildren().add(bg);
	}
	
	public void updateBackGround(){
		bg.setFitWidth(gui.getWidth() * 0.8);
		bg.setLayoutX(gui.getWidth()*0.5 - bg.getFitWidth()*0.5);
		bg.setLayoutY(gui.getHeight()*0.08);
	}

	public Image getBackGround() {
		return backGround;
	}
	
	/**
	 * Get players card, and place on top (last) in scene
	 * 
	 */
	public void paintPocketCards() {
		Player player = gui.client.getSession().getPlayers().get(0);
		Card card1 = player.getCard1();
		Card card2 = player.getCard2();
		
		ImageView view1 = createCardImage(card1);
		ImageView view2 = createCardImage(card2);
		
		gui.root.getChildren().add(view1);
		gui.root.getChildren().add(view2);
		
		view1.setLayoutX(gui.getWidth() * 0.75);
		view1.setLayoutY(gui.getHeight() * 0.77);
		view2.setLayoutX(gui.getWidth() * 0.83);
		view2.setLayoutY(gui.getHeight() * 0.77);
		
		view1.setFitWidth(gui.getWidth() * 0.15);
		view1.setPreserveRatio(true);
		view1.setSmooth(true);
		view2.setFitWidth(gui.getWidth()*0.15);
		view2.setPreserveRatio(true);
		view2.setSmooth(true);

		view1.setRotate(350);
		view2.setRotate(5);
		
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
		if(communityCards == null || communityCards.size() == 0) return;
		if(communityCards.size() == 3)
			communityImageCards = new ArrayList<ImageView>();
		
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
		    	
		    	Double cardOffset = gui.getWidth()*0.05;
				int cardsToShow = communityCards.size();
				for (int i = 0; i < cardsToShow; i++) {
					
					Card c = (Card) communityCards.get(i);
					ImageView cardImage = createCardImage(c);
					cardImage.setPreserveRatio(true);
					cardImage.setFitHeight(gui.getWidth()*0.07);
					
					cardImage.setLayoutX(gui.getWidth()*0.4 + (cardOffset*i));
					cardImage.setLayoutY(gui.getHeight()*0.4);
					
					communityImageCards.add(cardImage);
					getChildren().add(cardImage);

				}
		    }
		});
	}
	public void clearCommunityCards(){
		Platform.runLater(new Runnable(){
		    @Override
		    public void run() {
				if(communityImageCards != null){
					if(communityImageCards.size() == 0) return;
					else
						gui.getMainFrame().getChildren().removeAll(communityImageCards);
						communityImageCards = new ArrayList<ImageView>();
						    
				}
		    }
	    });
	}
	
	
	public void updateTablePot(){
		if(totalPot != null)
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

				Label wonText = new Label("Player: "+player.toString() +" won the game!");

				Button ok = new Button("Ok");

				grid.addRow(1, wonText);
				
				grid.addRow(2, ok);
				
				root.getChildren().add(grid);
				
		    	dialogStage.showAndWait();    
		    }           
		});
	}
	public void playerWons(Player player){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Label wonText = new Label(player.toString().toUpperCase() +" WON THE ROUND!\nTotal win");
				HBox statusBox = new HBox();
				statusBox.getChildren().add(wonText);
				statusBox.getStyleClass().add("pane");
				statusBox.setLayoutX(getWidth()*0.5 - statusBox.getWidth());
				statusBox.setLayoutY(getHeight()*0.5 - statusBox.getHeight());
				getChildren().add(statusBox);
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
		paintPlayerInfoBox(playerInfoBoxes.get(3), xStep*2, y);
		paintPlayerInfoBox(playerInfoBoxes.get(4), xStep*6.5, y);
		paintPlayerInfoBox(playerInfoBoxes.get(5), xStep*11, y);
		paintPlayerInfoBox(playerInfoBoxes.get(2), xStep*0.2, yStep*2);
		paintPlayerInfoBox(playerInfoBoxes.get(6), xStep*12.5, yStep*2);
		paintPlayerInfoBox(playerInfoBoxes.get(1), xStep*0.2, yStep*4.2);
		paintPlayerInfoBox(playerInfoBoxes.get(7), xStep*12.5, yStep*4.2);
		paintPlayerInfoBox(playerInfoBoxes.get(0), xStep*0.2, yStep*6.4);
		paintPlayerInfoBox(playerInfoBoxes.get(8), xStep*12.5, yStep*6.4);
	}
	/**
	 * This is the drawing of the pot on the scene.
	 */
	public void drawPot() {
		totalPot = new Label("POT: "+gui.getClient().getSession().getTable().getPot()+" CH");
		totalPot.setLayoutX(gui.getWidth()/2);
		totalPot.setLayoutY(gui.getHeight()*0.6);
		
//		ImageView potImage = new ImageView(new Image(getClass().getResourceAsStream("/pot.png")));
//		getChildren().add(potImage);
		
		getChildren().add(totalPot);
	}
}
