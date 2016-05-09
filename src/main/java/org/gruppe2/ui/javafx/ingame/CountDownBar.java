package org.gruppe2.ui.javafx.ingame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class CountDownBar extends HBox {
	
    ProgressBar progressBar = new ProgressBar(0);
    Timeline timeline;
    Timeline progressBarTimeLine;
    int progressDivisor = 3000;

    public CountDownBar(){
    	setUpProgressBar();
    }
	
	private void setUpProgressBar(){
   	 progressBar.setProgress(0);
        progressBar.setVisible(false);
        getChildren().add(progressBar);
        
   }
   
	public void startProgressBarTimer(){
   	progressBar.setVisible(true);
   	timeline = new Timeline(new KeyFrame(
   	        Duration.seconds(30),
   	        ae -> System.out.println("fold now! bitch!")));
   	timeline.play();
   	
   	progressBarTimeLine = new Timeline(new KeyFrame(
   	        Duration.seconds(1),
   	        ae -> updateProgressBar()));
   	progressBarTimeLine.setCycleCount(progressDivisor);
   	progressBarTimeLine.play();
   }
   
   public void updateProgressBar(){
   	System.out.println("updating");
   	double progress = progressBar.getProgress();
   	progressBar.setProgress(progress+(1/progressDivisor));
   }
   
   public void stopProgressBar(){
   	progressBar.setVisible(false);
   	timeline.stop();
   	progressBarTimeLine.stop();
   }
}
