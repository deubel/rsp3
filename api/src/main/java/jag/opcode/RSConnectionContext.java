package jag.opcode;

import jag.RSLinkedList;
import jag.RSProvider;

public interface RSConnectionContext extends RSProvider {

    RSBuffer getOutgoing();

    int getIdleWritePulses();

    int getIdleReadPulses();

    void setIdleReadPulses(int idleReadPulses);

    RSIsaacCipher getEncryptor();

    int getBuffered();

    RSPacketBuffer getBuffer();

    RSLinkedList<RSOutgoingPacket> getPackets();

}