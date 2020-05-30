package jag.audio;

import jag.RSProvider;

public interface RSAudioSystem extends RSProvider {

    int[] getSamples();

}