package org.gruppe2.frontend;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
	public Painter(PokerGame game, GUI gui) {
		super();
		this.game = game;
		this.gui = gui;
		setBackGround("tableAndBackGround.png");
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
	
	public ImageView createCardImage(Card card){
		String name = "Pictures/"+card.toStringGUI()+".png";
		
		Image image = new Image(getClass().getResourceAsStream(name),30,80,true,true);
		
		ImageView cardPic = new ImageView(image);
		
		return cardPic;

	}
	
	



}
