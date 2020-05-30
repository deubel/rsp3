package org.rspeer.game.component;

import org.rspeer.commons.Time;
import org.rspeer.game.Keyboard;
import org.rspeer.game.adapter.component.InterfaceComponent;

import java.awt.event.KeyEvent;

//TODO hook the input packet and send that, or set the varc string, probably the former
public class EnterInput {

    private static final InterfaceAddress INPUT_ADDRESS = new InterfaceAddress(() ->
            Interfaces.query(InterfaceComposite.CHATBOX)
                    .types(InterfaceComponent.Type.LABEL)
                    .foregrounds(128)
                    .results().first()
    );

    public static boolean isOpen() {
        InterfaceComponent input = INPUT_ADDRESS.resolve();
        return input != null && input.isVisible();
    }

    public static String getEntry() {
        InterfaceComponent component = Interfaces.lookup(INPUT_ADDRESS);
        if (component != null && component.isVisible()) {
            return component.getText().replace("*", "");
        }
        return "";
    }

    public static boolean initiate(int entry) {
        return initiate(String.valueOf(entry));
    }

    public static boolean initiate(String entry) {
        if (!isOpen()) {
            return false;
        }

        InterfaceComponent component = Interfaces.lookup(INPUT_ADDRESS);
        if (component == null) {
            return false;
        }

        String inputText = component.getText().replace("*", "");
        if (!inputText.equals(entry) && inputText.trim().length() > 0) {
            for (int i = 0; i < inputText.length(); i++) {
                Keyboard.pressEventKey(KeyEvent.VK_BACK_SPACE);
            }
        }

        if (!inputText.equals(entry)) {
            Keyboard.sendText(String.valueOf(entry), true);
        }
        return Time.sleepUntil(() -> !isOpen(), 1200);
    }

    public enum Type {

        //Unknown: CS2063, CS750, CS550, CS110, CS108, CS109
        //109 is related to joining houses but not limited to it? has the Last name: xxx shit. idk what to name this 1

        JOIN_CHANNEL(10),
        ADD_BEFRIENDED_PLAYER(2),
        ADD_IGNORED_PLAYER(4),
        DELETE_IGNORED_PLAYER(5),
        DELETE_BEFRIENDED_PLAYER(3),
        MESSAGE_BEFRIENDED_PLAYER(6),
        SET_A_NAME(13), //"You must set a name before you can chat."
        BANK_ITEM_SEARCH(11),
        PUBLIC_CHAT_FILTER(548)
        ;

        private final int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
