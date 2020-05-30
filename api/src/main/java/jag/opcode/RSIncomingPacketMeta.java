package jag.opcode;

import jag.RSProvider;

public interface RSIncomingPacketMeta extends RSProvider {

    int getSize();

    int getOpcode();

}