package org.rspeer.ui.component.menu.script;

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.game.script.loader.ScriptBundle;
import org.rspeer.game.script.loader.ScriptSource;
import org.rspeer.game.script.loader.local.LocalScriptLoader;
import org.rspeer.ui.locale.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;

//TODO: Rework menu into actual script selector component
public class ScriptMenu extends JMenu {

    private static final int COUNT_BEFORE_SCRIPTS = 2;

    private final LocalScriptLoader loader;
    private final Environment environment;

    public ScriptMenu(Environment environment) {
        super(Message.SCRIPT_SELECTOR.getActive(environment.getPreferences()));
        this.environment = environment;
        this.loader = new LocalScriptLoader(Configuration.Paths.SCRIPTS_LOCATION);
        this.initializeReloadMenuItem();
    }

    private void initializeReloadMenuItem() {
        JMenuItem reload = new JMenuItem(Message.RELOAD.getActive(environment.getPreferences()));
        reload.addActionListener(this::onReload);
        add(reload);
        addSeparator();
    }

    private void onReload(ActionEvent evt) {
        environment.getScriptContext().getController().stop();

        ScriptBundle bundle = loader.load();
        bundle.addAll(loader.predefined());

        for (int i = COUNT_BEFORE_SCRIPTS; i < getItemCount(); i++) {
            remove(i);
        }

        for (ScriptSource src : bundle) {
            JMenuItem item = new JMenuItem(src.getName());
            item.addActionListener((itemAct) -> {
                environment.getScriptContext().getController().start(loader.define(src), src);
                addSeparator();
                add(createStopItem());
            });

            add(item);
        }
    }

    private JMenuItem createStopItem() {
        JMenuItem stop = new JMenuItem(Message.STOP.getActive(environment.getPreferences()));
        stop.addActionListener((act) -> {
            environment.getScriptContext().getController().stop();
            for (int i = getItemCount() - 1; i >= 0; i--) {
                JMenuItem item = getItem(i);
                remove(i);
                if (item.getLabel().equals("-")) {
                    break;
                }
            }
        });
        return stop;
    }
}
