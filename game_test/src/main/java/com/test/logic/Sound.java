package com.test.logic;

import java.util.Map;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    private Map<String, Clip> backgroundMusic = new HashMap<>();

    public void play(String file) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    getClass().getResource("/sounds/" + file)
            );

            AudioFormat baseFormat = audio.getFormat();

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedAudio =
                    AudioSystem.getAudioInputStream(decodedFormat, audio);

            Clip clip = AudioSystem.getClip();
            clip.open(decodedAudio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playBackground(String file) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    getClass().getResource("/sounds/" + file)
            );

            AudioFormat baseFormat = audio.getFormat();

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedAudio =
                    AudioSystem.getAudioInputStream(decodedFormat, audio);

            Clip clip = AudioSystem.getClip();
            clip.open(decodedAudio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            backgroundMusic.put(file, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackground(String file) {
        backgroundMusic.remove(file);
    }

    public void stopAllBackground() {
        for (String key : backgroundMusic.keySet()) {
            Clip clip = backgroundMusic.get(key);
            clip.stop();
            clip.close();
        }

        backgroundMusic.clear();
    }
}