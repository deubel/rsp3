package org.rspeer.ui.debug;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.scene.PathingEntity;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.scene.Players;
import org.rspeer.game.scene.Scene;

import java.awt.*;

public class GameDebug extends Debug {

    private static final Font DEFAULT = Font.getFont(Font.DIALOG);

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        g.setRenderingHints(rh);
        if (DEFAULT != null) {
            g.setFont(DEFAULT);
        }

        drawString("Game state: " + Game.getState());

        Player local = Players.getLocal();
        if (local == null) {
            return;
        }

        drawString("Position: " + local.getPosition());
        drawString("Scene base: " + Scene.getBase());
        drawString("Animation: " + local.getAnimation());
        drawString("Stance: " + local.getStance());
        drawString("Effect: " + local.getEffect());

        PathingEntity<?> target = local.getTarget();
        if (target != null) {
            drawString("Target: " + target.getName() + " (index=" + target.getIndex() + ")");
        }
    }
}
