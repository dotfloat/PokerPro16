package org.gruppe2.ui.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import org.gruppe2.game.objects.Player;
import org.gruppe2.ui.old_javafx.GUIClient;
import org.gruppe2.ui.javafx.PlayerInfoBox;

/**
 * This class will be split in several sub controllers, i.g Bottom Hbox with buttons must be one class, etc..
 */
public class GameWindowController implements Initializable {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();
	
	// MVC. should we keep objects and shit in the models (backend)?
	
	ArrayList<Player> players = new ArrayList<>();
	@FXML private BorderPane borderPane;
	@FXML private TextField chatField;
	@FXML private ImageView pokerTable;
	@FXML private Button FOLD;
	@FXML private Slider slider;
	@FXML private Label sliderValue;
	@FXML private Button BET;
	@FXML private HBox communityCardsBox;
	@FXML private ImageView playerCard1;
	@FXML private ImageView playerCard2;

	@FXML
	private void setSizes() {
		slider.prefWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.4));
        slider.setMinWidth(height * 0.15);
        slider.setMax(5000);
       
        sliderValue.setMinWidth(width * 0.09);
        sliderValue.setMaxWidth(height * 0.09);

		pokerTable.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.6));
		
		
		setPlayerCards();
		setCommunityCards();
		setUpPlayerBoxes();
	}
	
	/**
	 * This is just test method for proof of conecpt, change this when backend is ready with playerCards.
	 */
	private void setPlayerCards() {
		playerCard1.setImage(new Image(("/images/cards/" + "c02" + ".png")));
		playerCard2.setImage(new Image(("/images/cards/" + "c03" + ".png")));
		playerCard1.setLayoutX(width * 0.80);
        playerCard1.setLayoutY(height * 0.77);
        playerCard2.setLayoutX(width * 0.88);
        playerCard2.setLayoutY(height * 0.77);

        playerCard1.setFitWidth(width * 0.12);
        playerCard1.setPreserveRatio(true);
        playerCard1.setSmooth(true);
        playerCard2.setFitWidth(width * 0.12);
        playerCard2.setPreserveRatio(true);
        playerCard2.setSmooth(true);

        playerCard1.setRotate(350);
        playerCard2.setRotate(5);
		
	}
	/**
	 * This is just test method for proof of conecpt, change this when backend is ready with playerCards.
	 */
	private void setCommunityCards() {
		
		for(int i = 0;i<5;i++){
			ImageView cardImage = new ImageView(new Image(("/images/cards/" + "c02" + ".png")));
			communityCardsBox.getChildren().add(cardImage);
			cardImage.setPreserveRatio(true);
            cardImage.setFitWidth(width * 0.05);
            cardImage.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.05));
            
		}
		communityCardsBox.setAlignment(Pos.CENTER);
	}
	@FXML
	private void setEvents() {
		FOLD.setOnAction(e -> foldAction());
		BET.setOnAction(e -> betAction());
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                sliderValue.textProperty().setValue(checkMaxBid(slider));
            }
        });
	}

	/**
	 * This will become fxml
	 */
	private void foldAction(){
		System.out.println("FOLD NOT IMPLEMENTED");
	}
	/**
	 * This will become fxml
	 */
	private void betAction(){
		System.out.println("BET NOT IMPLEMENTED");
//		 if (pa.canCall() && pa.canRaise() && slider.getValue() > 1)
//             raise(client, slider, player);
//         else if (pa.canCall())
//             client.setAction(new Action.Call());
//         else if(pa.canCheck())
//             client.setAction(new Action.Check());
	}
	
	private void raise(GUIClient client, Slider raiseSlider, Player player) {
//        if (client.getSession().getPlayerOptions(player).getMinRaise() <= raiseSlider
//                .getValue()) {
//            if (client.getSession().getPlayerOptions(player).getMaxRaise() >= raiseSlider
//                    .getValue())
//                client.setAction(new Action.Raise((int) raiseSlider.getValue()));
//        }
    }
	/**
	 * This is for testing
	 */
	public void setUpPlayerBoxes(){
		List<Pane> playerInfoBoxes = new ArrayList<Pane>();
//		new PlayerInfoBox() 
		for(int i = 0; i<6;i++){
			players.add(new Player("Bot", i, null));
			playerInfoBoxes.add(i, new PlayerInfoBox());
		}
		paintAllPlayers(playerInfoBoxes);
	}
	
	public void paintAllPlayers(List<Pane> playerInfoBoxes) {
        double xStep = width / 15;
        double yStep = height / 11;
        double y = 10;
        int numberOfPlayers = playerInfoBoxes.size();
        if(numberOfPlayers > 3)
        	paintPlayerInfoBox(playerInfoBoxes.get(3), xStep * 2, y);
        if(numberOfPlayers > 4)
        	paintPlayerInfoBox(playerInfoBoxes.get(4), xStep * 6.5, y);
        if(numberOfPlayers > 5)
        	paintPlayerInfoBox(playerInfoBoxes.get(5), xStep * 11, y);
        if(numberOfPlayers >2)
        	paintPlayerInfoBox(playerInfoBoxes.get(2), xStep * 0.2, yStep * 2);
        if(numberOfPlayers > 6)
        	paintPlayerInfoBox(playerInfoBoxes.get(6), xStep * 12.5, yStep * 2);
        if(numberOfPlayers > 1)
        	paintPlayerInfoBox(playerInfoBoxes.get(1), xStep * 0.2, yStep * 4.2);
        if(numberOfPlayers > 7)
        	paintPlayerInfoBox(playerInfoBoxes.get(7), xStep * 12.5, yStep * 4.2);
        if(numberOfPlayers > 0)
        	paintPlayerInfoBox(playerInfoBoxes.get(0), xStep * 0.2, yStep * 6.4);
        if(numberOfPlayers > 8)
        	paintPlayerInfoBox(playerInfoBoxes.get(8), xStep * 12.5, yStep * 6.4);
    }
	public void paintPlayerInfoBox(Pane playerInfoBox, double x, double y) {
        playerInfoBox.setLayoutX(x);
        playerInfoBox.setLayoutY(y);
        borderPane.getChildren().add(playerInfoBox);
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert(slider != null):"slider is null";
		setSizes();
		setEvents();
	}
	/**
     * If you raise all you have, change text of raise button to ALL IN
     *
     * @param slider
     * @return
     */
    private String checkMaxBid(Slider slider) {
        if (slider.getValue() == slider.getMax()) BET.setText("ALL IN");
        else BET.setText("RAISE");
        return (int) slider.getValue() + " CHIPS";
    }
}
