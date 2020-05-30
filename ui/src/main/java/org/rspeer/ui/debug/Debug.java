package org.rspeer.ui.debug;

import org.rspeer.commons.AWTUtil;
import org.rspeer.game.event.RenderEvent;
import org.rspeer.game.event.listener.RenderListener;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class Debug implements RenderListener {

    private int y = 15;
    private List<String> entries = new LinkedList<>();

    @Override
    public final void notify(RenderEvent e) {
        Graphics2D g = (Graphics2D) e.getSource();
        render(g);

        for (String entry : entries) {
            AWTUtil.drawBoldedString(g, entry, 20, y += 15);
        }

        y = 15;
        entries = new LinkedList<>();
    }


    public final void drawString(String entry) {
        entries.add(entry);
    }

    public abstract void render(Graphics2D g);
}
