package org.rspeer.ui;

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.ui.component.menu.BotMenuBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BotFrame extends JFrame {

    private final Environment environment;

    public BotFrame(Environment environment) {
        super(Configuration.getApplicationTitle());
        this.environment = environment;
        applyComponents();
    }

    private void applyComponents() {
        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(Game.getClient().asApplet(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(new BotMenuBar(environment));
        environment.getBotContext().setFrame(this);
        pack();
    }
}
