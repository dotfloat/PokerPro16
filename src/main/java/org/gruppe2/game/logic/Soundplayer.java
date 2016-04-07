package org.gruppe2.game.logic;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kjors on 06.04.2016.
 */
public class Soundplayer {

    String fileName;

    public Soundplayer(String fileName) {
        this.fileName = fileName;
    }

    public void play() {

        URL midiUrl = getClass().getResource("/sound/" + fileName);
        File file = new File(midiUrl.getFile());

        if (file != null) {

            // Check if file-extension is valid
            String extension = "";
            int i = file.getName().indexOf('.');
            if (i > 0)
                extension = file.getName().substring(i+1).toLowerCase();

            switch ( extension ) {
                case "mid": playMidi( file );
                    break;
                default: break;
            }
        }
    }

    private static void playMidi(File file) {

        InputStream stream = null;

        try {
            stream = new BufferedInputStream(new FileInputStream( file ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(stream);
            sequencer.start();
        } catch (Exception me) {
            me.printStackTrace();
        }
    }
}
