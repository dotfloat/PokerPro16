package org.gruppe2.ui.javafx;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.gruppe2.game.old.Card;
import org.gruppe2.ui.Resources;

public class CommunityCards extends HBox {
	private int width = PokerApplication.getWidth();
	private ArrayList<ImageView> communityImageCards;
	@SuppressWarnings("unused")
	private List<Card> communityCards;
	
	public CommunityCards() {
		Resources.loadFXML(this);

	}

	/**
	 * This is just test method for proof of conecpt, change this when backend
	 * is ready with playerCards.
	 */
	public void setCommunityCards(List<Card> communalCards) {
		communityCards = communalCards;
		if (communalCards == null || communalCards.size() == 0)
			return;
		if (communalCards.size() == 3){
			communityImageCards = new ArrayList<ImageView>();
			setCommunityCardLoop(communalCards);
		}
		else if (communalCards.size() == 4){
			List<Card> turn = communalCards.subList(3, 4);
			setCommunityCardLoop(turn);
		}
		else if (communalCards.size() == 5){
			List<Card> river = communalCards.subList(4, 5);
			setCommunityCardLoop(river);
		}
		
		this.setAlignment(Pos.CENTER);
	}
	
	private void setCommunityCardLoop(List<Card> communalCards){
		for (Card card : communalCards) {
			
			ImageView cardImage = createCardImage(card);
			this.getChildren().add(cardImage);
			
			cardImage.setPreserveRatio(true);
			cardImage.setFitWidth(width * 0.05);
			cardImage.fitWidthProperty().bind(
					PokerApplication.getRoot().widthProperty().multiply(0.05));
			communityImageCards.add(cardImage);
		}
	}

	public void clearCommunityCards() {
		if (communityImageCards != null) {
			if (communityImageCards.size() == 0)
				return;
			else{
				this.getChildren().removeAll(communityImageCards);
				communityImageCards = new ArrayList<ImageView>();
				System.out.println("hello remove");
			}

		}
	}
	/**
     * Gets the specific card as an ImageView
     *
     * @param card
     * @return
     */
    public ImageView createCardImage(Card card) {
        String name = "/images/cards/" + getCardName(card) + ".png";

        Image image = new Image(getClass().getResourceAsStream(name), 200, 0, true, true);

        ImageView cardPic = new ImageView(image);

        return cardPic;
    }

	/**
	 * Method so GUI can find card easily
	 *
	 * @return
	 */
	public String getCardName(Card card) {
		String finalName = String.valueOf(card.getSuit().toString()
				.toLowerCase().charAt(0));

		if (card.getFaceValue() > 9)
			finalName += card.getFaceValue();
		else
			finalName += "0" + card.getFaceValue();

		return finalName;
	}
	
	public List<Card> getCommunityCards(){
		return communityCards;	
	}

}
