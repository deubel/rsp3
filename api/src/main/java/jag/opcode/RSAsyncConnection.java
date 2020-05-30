package jag.opcode;

import java.net.Socket;

public interface RSAsyncConnection extends RSConnection {

    RSAsyncOutputStream getOutput();

    RSAsyncInputStream getInput();

    Socket getSocket();

    void finalize0();

}