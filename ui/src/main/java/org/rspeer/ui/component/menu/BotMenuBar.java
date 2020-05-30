package org.rspeer.ui.component.menu;

import org.rspeer.environment.Environment;
import org.rspeer.ui.debug.explorer.itf.InterfaceExplorer;
import org.rspeer.ui.locale.Message;
import org.rspeer.ui.component.menu.script.ScriptMenu;

import javax.swing.*;

public class BotMenuBar extends JMenuBar {

    private final Environment environment;

    public BotMenuBar(Environment environment) {
        this.environment = environment;
        add(createFileMenu());
        add(createDebugMenu());
        add(createOptionsMenu());
        ScriptMenu sm = new ScriptMenu(environment);
        add(sm);
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu(Message.FILE.getActive(environment.getPreferences()));
        //TODO: stuff
        return file;
    }

    private JMenu createOptionsMenu() {
        JMenu options = new JMenu(Message.WINDOW.getActive(environment.getPreferences()));

        JCheckBoxMenuItem onTop = new JCheckBoxMenuItem(Message.ALWAYS_ON_TOP.getActive(environment.getPreferences()));
        onTop.addItemListener(act -> {
            environment.getBotContext().getFrame().setAlwaysOnTop(onTop.getState());
        });

        options.add(onTop);
        return options;
    }

    private JMenu createDebugMenu() {
        JMenu debug = new JMenu(Message.DEBUG.getActive(environment.getPreferences()));

        JMenuItem itfs = new JMenuItem("Interfaces");
        itfs.addActionListener(act -> new InterfaceExplorer(environment));

        debug.add(itfs);

        return debug;
    }
}
