package jag.audio;

import jag.RSProvider;

public interface RSAudioEnvelope extends RSProvider {

    int getAmplitude();

    int getMax();

    int getTicks();

    int getStart();

    int getPhaseIndex();

    int getStep();

    int getEnd();

    int[] getPhases();

}