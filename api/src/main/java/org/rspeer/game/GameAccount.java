package org.rspeer.game;

import jag.game.RSClient;

public class GameAccount {

    private final String username, password, pin;

    public GameAccount(String username, String password, String pin) {
        this.username = username;
        this.password = password;
        this.pin = pin;
    }

    public GameAccount(String username, String password) {
        this(username, password, "");
    }

    public static GameAccount predefined(String pin) {
        RSClient client = Game.getClient();
        return new GameAccount(client.getUsername(), client.getPassword(), pin);
    }

    public static GameAccount predefined() {
        return predefined("");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPin() {
        return pin;
    }

    public boolean isPinPresent() {
        return pin != null && pin.length() == 4;
    }
}
