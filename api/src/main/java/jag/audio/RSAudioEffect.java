package jag.audio;

import jag.RSProvider;

public interface RSAudioEffect extends RSProvider {

    RSAudioInstrument[] getInstruments();

    int getStart();

    int getEnd();

}