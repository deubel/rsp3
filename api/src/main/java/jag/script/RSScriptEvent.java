package jag.script;

import jag.RSNode;
import jag.game.RSInterfaceComponent;

public interface RSScriptEvent extends RSNode {

    Object[] getArgs();

    RSInterfaceComponent getSource();

    void setArgs(Object[] args);

    default int getScriptId() {
        Object[] args = getArgs();
        return args != null && args.length > 0 && args[0] instanceof Integer ? (int) args[0] : -1;
    }
}