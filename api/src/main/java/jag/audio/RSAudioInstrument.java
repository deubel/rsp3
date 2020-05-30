package jag.audio;

import jag.RSProvider;

public interface RSAudioInstrument extends RSProvider {

    RSAudioEnvelope getPitchEnvelope();

    int[] getOscillatorPitch();

}