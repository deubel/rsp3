package jag.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

public interface RSAudioSystemImpl extends RSAudioSystem {

    SourceDataLine getSourceDataLine();

    AudioFormat getFormat();

    byte[] getBuffer();

    int getBufferSize();

}