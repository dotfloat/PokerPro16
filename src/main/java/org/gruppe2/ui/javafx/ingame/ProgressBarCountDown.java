package org.gruppe2.ui.javafx.ingame;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import org.gruppe2.ui.javafx.SoundPlayer;

public class ProgressBarCountDown extends HBox {
	
    ProgressBar progressBar = new ProgressBar(0);
    
    Timeline progressBarTimeLine;
    double progressDivisor = 30;
	private boolean countDownSoundStarted = false;
	private boolean progressBarRunning = false;

    public ProgressBarCountDown(){
    	setUpProgressBar();
    }
	
	private void setUpProgressBar(){
		progressBar.setProgress(0);
		progressBar.setVisible(false);
        getChildren().add(progressBar);
   }
   
	public void startProgressBarTimer(){
	progressBarRunning = true;
   	
   	
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
	if(progressBarRunning){
		SoundPlayer.stopCountDownTimerMusic();
		progressBar.setProgress(0);
		countDownSoundStarted = false;
	   	progressBar.setVisible(false);
	   	
	   	if(progressBarTimeLine.getStatus().equals(Status.RUNNING))
	   		progressBarTimeLine.stop();
	   }
   }
}
