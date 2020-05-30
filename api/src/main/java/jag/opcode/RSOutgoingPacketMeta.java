package jag.opcode;

import jag.RSProvider;

public interface RSOutgoingPacketMeta extends RSProvider {

    int getSize();

    int getOpcode();

}