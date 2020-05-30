package org.rspeer.game;

import java.awt.*;
import java.awt.event.KeyEvent;

@Deprecated //replace everything that needs input with cs2 or any other approach
public class Keyboard {

    public static void sendText(String text, boolean pressEnter) {
        for (char c : text.toCharArray()) {
            sendKey(c);
        }

        if (pressEnter) {
            pressEnter();
        }
    }

    public static void pressEnter() {
        pressEventKey(KeyEvent.VK_ENTER);
    }

    public static void sendKey(char key) {
        Game.getCanvas().dispatchEvent(generateEvent(key, KeyEvent.KEY_TYPED));
    }

    public static void pressEventKey(int eventKey) {
        Game.getCanvas().dispatchEvent(generateEvent(eventKey, KeyEvent.KEY_PRESSED));
        Game.getCanvas().dispatchEvent(generateEvent(eventKey, KeyEvent.KEY_RELEASED));
    }

    private static KeyEvent generateEvent(char key, int event) {
        AWTKeyStroke stroke = AWTKeyStroke.getAWTKeyStroke(key);
        return new KeyEvent(Game.getCanvas(),
                event,
                System.currentTimeMillis(), stroke.getModifiers(), stroke.getKeyCode(),
                stroke.getKeyChar());
    }

    private static KeyEvent generateEvent(int key, int event) {
        return new KeyEvent(Game.getCanvas(), event, System.currentTimeMillis(), 0, key,
                (char) key, KeyEvent.KEY_LOCATION_STANDARD);
    }
}
