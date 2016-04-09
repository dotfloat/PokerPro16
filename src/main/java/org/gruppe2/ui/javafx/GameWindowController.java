package org.gruppe2.ui.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import org.gruppe2.game.old.Action;
import org.gruppe2.game.old.Player;
import org.gruppe2.game.old.PossibleActions;
import org.gruppe2.ui.old_javafx.GUIClient;

/**
 * Created by kjors on 04.04.2016.
 */
public class GameWindowController implements Initializable {
	private int width = PokerApplication.getWidth();
	private int height = PokerApplication.getHeight();


	// MVC. should we keep objects and shit in the models (backend)?
	GUIClient client;
	PossibleActions pa;
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

		pokerTable.fitWidthProperty().bind(PokerApplication.getRoot().widthProperty().multiply(0.7));
		
		
		setPlayerCards();
		setCommunityCards();
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
            cardImage.setFitHeight(width * 0.07);
		}
		communityCardsBox.setLayoutX(width);
		communityCardsBox.setLayoutY(height);
		
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
