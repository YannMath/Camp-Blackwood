package com.test.logic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public void play(String file) {
        try {
            AudioInputStream audio =
                AudioSystem.getAudioInputStream(
                    getClass().getResource("/sounds/" + file)
                );

            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playBackground(String file) {
        
    }
}
