package org.rspeer.game.scene;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.query.scene.PlayerQuery;
import jag.game.RSClient;
import jag.game.scene.entity.RSPlayer;

import java.util.ArrayList;
import java.util.List;

public class Players {

    public static Player getAt(int index) {
        RSPlayer[] players = Game.getClient().getPlayers();
        if (players != null && index < players.length && index >= 0) {
            RSPlayer player = players[index];
            if (player != null) {
                return new Player(player, index);
            }
        }
        return null;
    }

    public static Player getLocal() {
        RSPlayer local = Game.getClient().getPlayer();
        if (local != null) {
            return new Player(local, Game.getClient().getPlayerIndex());
        }
        return null;
    }

    public static PlayerQuery query() {
        return new PlayerQuery(Players::getLoaded);
    }

    private static List<Player> getLoaded() {
        RSClient client = Game.getClient();
        List<Player> players = new ArrayList<>();
        int[] indices = client.getPlayerIndices();
        RSPlayer[] raw = client.getPlayers();
        if (indices == null || raw == null) {
            return players;
        }

        for (int i = 0; i < client.getPlayerCount(); i++) {
            int index = indices[i];
            RSPlayer player = raw[index];
            if (player != null) {
                players.add(new Player(player, index));
            }
        }
        return players;
    }
}
