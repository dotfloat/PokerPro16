package org.gruppe2.ui.javafx.ingame;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import org.gruppe2.ui.javafx.SoundPlayer;

public class CountDownBar extends HBox {
	
    ProgressBar progressBar = new ProgressBar(0);
    Timeline timeline;
    Timeline progressBarTimeLine;
    double progressDivisor = 30;
	private boolean countDownSoundStarted = false;

    public CountDownBar(){
    	setUpProgressBar();
    }
	
	private void setUpProgressBar(){
		progressBar.setProgress(0);
		progressBar.setVisible(false);
        getChildren().add(progressBar);
        
   }
   
	public void startProgressBarTimer(){
   	timeline = new Timeline(new KeyFrame(
   	        Duration.seconds(30),
   	        ae -> System.out.println("fold now! bitch!")));
   	timeline.play();
   	
   	progressBarTimeLine = new Timeline(new KeyFrame(
   	        Duration.seconds(1),
   	        ae -> updateProgressBar()));
   	progressBarTimeLine.setCycleCount((int) progressDivisor);
   	progressBarTimeLine.play();
   }
   
   public void updateProgressBar(){
   	double progress = progressBar.getProgress();
   	if(progress > 0.66 && !countDownSoundStarted){
   		progressBar.setVisible(true);
   		countDownSoundStarted = true;
   		SoundPlayer.playCountDownTimerMusic();
   	}
   	progressBar.setProgress(progress+(1/progressDivisor));
   }
   
   public void stopProgressBar(){
	SoundPlayer.stopCountDownTimerMusic();
	progressBar.setProgress(0);
	countDownSoundStarted = false;
   	progressBar.setVisible(false);
   	if(timeline.getStatus().equals(Status.RUNNING))
   		timeline.stop();
   	if(progressBarTimeLine.getStatus().equals(Status.RUNNING))
   		progressBarTimeLine.stop();
   }
}
