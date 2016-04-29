package org.gruppe2.ui.javafx;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {
	
	public static void playIntroMusic(){
		playSound(SoundPlayer.class.getResource("/sound/jazzy_intro.mp3").toExternalForm());
	}
	
	public static void playSound(String musicFile){
		Media sound = new Media(musicFile);
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}

}
