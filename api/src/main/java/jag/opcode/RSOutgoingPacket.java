package jag.opcode;

import jag.RSNode;

public interface RSOutgoingPacket extends RSNode {

    int getSize();

    int getPayloadSize();

    RSOutgoingPacketMeta getMeta();

    RSPacketBuffer getBuffer();

}