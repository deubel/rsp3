package jag.audio;

import jag.RSProvider;

public interface RSAudioRunnable extends RSProvider {

    RSAudioSystem[] getSystems();

    void run();

}