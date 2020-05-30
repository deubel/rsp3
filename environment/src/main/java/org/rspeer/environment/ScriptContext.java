package org.rspeer.environment;

import org.rspeer.game.script.ScriptController;

public class ScriptContext {

    private final ScriptController controller;

    public ScriptContext() {
        controller = new ScriptController();
    }

    public ScriptController getController() {
        return controller;
    }

    //unless we add more shit here this class is redundant
    //or we can expose specific things in here without exposing the controller itself
}
