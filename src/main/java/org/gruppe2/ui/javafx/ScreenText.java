package org.gruppe2.ui.javafx;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

import org.gruppe2.game.old.Player;
import org.gruppe2.ui.Resources;

public class ScreenText extends Label {
	
	 public ScreenText() {
	        Resources.loadFXML(this);
	    }
	 
	 public void setAnimationRoundWon(Player player){
		this.setText(player+" won the round!");

		FadeTransition fader = createFader(this);
		playAnimation(fader);
		
	 }
	 
    private void playAnimation(FadeTransition fader) {
    	SequentialTransition blinkThenFade = new SequentialTransition(
		        this,
		        fader
		);
		blinkThenFade.play();
	}

	private FadeTransition createFader(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(3), node);
        fade.setFromValue(3);
        fade.setToValue(0);

        return fade;
    }

	@SuppressWarnings("static-access")
	public void setAnimationGameWon(Player player) {
		this.setText(player+" won the game!");
		PokerApplication.inGame = false;
		FadeTransition fader = createFader(this);
		playAnimation(fader);
		
		
		if (((GameWindow) this.getParent().getParent()).getThread() != null) {
			try {
				((GameWindow) this.getParent().getParent()).getThread().sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			((GameWindow) this.getParent().getParent()).getThread().interrupt();
		}
		SceneController.setScene(new MainMenu());
	}
}
