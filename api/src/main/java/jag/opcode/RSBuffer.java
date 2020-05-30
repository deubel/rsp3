package jag.opcode;

import jag.RSNode;

public interface RSBuffer extends RSNode {

    int getCaret();

    byte[] getPayload();

}