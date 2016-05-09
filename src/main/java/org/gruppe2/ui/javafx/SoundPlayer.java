package org.gruppe2.ui.javafx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import org.gruppe2.game.event.PlayerWonEvent;
import org.gruppe2.game.session.Handler;

public class SoundPlayer {

    public static void playIntroMusic() {
        playSound(SoundPlayer.class.getResource("/sound/jazzy_intro.mp3").toExternalForm());
    }

    @Handler
    public static void playVictoryMusic(PlayerWonEvent playerWonEvent) {
        playSound(SoundPlayer.class.getResource("/sound/victory.mp3").toExternalForm());
    }


    public static void playSound(String musicFile) {
        try {
            Media sound = new Media(musicFile);
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

}
