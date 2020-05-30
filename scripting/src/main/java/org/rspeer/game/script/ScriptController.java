package org.rspeer.game.script;

import org.rspeer.game.script.loader.ScriptSource;

public class ScriptController {

    private Script current;
    private Thread scriptThread;

    public void start(Script script, ScriptSource src) {
        if (current != null) {
            throw new IllegalStateException("A script is already running");
        }

        current = script;
        current.setState(Script.State.STARTING);
        current.setState(Script.State.RUNNING);

        scriptThread = new Thread(current);
        scriptThread.start();
    }

    public void stop() {
        if (current != null) {
            current.setState(Script.State.STOPPED);
            current = null;
        }

        scriptThread = null;
    }
}
