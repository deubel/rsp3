package jag.opcode;

public interface RSPacketBuffer extends RSBuffer {

    RSIsaacCipher getCipher();

    int getBitCaret();

}