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
		this.setText(player+"won the round!");
		
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
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }

	public void setAnimationGameWon(Player player) {
		this.setText(player+"won the game!");
		
		FadeTransition fader = createFader(this);
		playAnimation(fader);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
