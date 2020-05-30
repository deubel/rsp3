package org.rspeer.game.scene;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.query.scene.NpcQuery;
import jag.game.RSClient;
import jag.game.scene.entity.RSNpc;

import java.util.ArrayList;
import java.util.List;

public class Npcs {

    public static Npc getAt(int index) {
        RSNpc[] npcs = Game.getClient().getNpcs();
        if (npcs != null && index < npcs.length && index >= 0) {
            RSNpc npc = npcs[index];
            if (npc != null) {
                return new Npc(npc, index);
            }
        }
        return null;
    }

    public static NpcQuery query() {
        return new NpcQuery(Npcs::getLoaded);
    }

    private static List<Npc> getLoaded() {
        RSClient client = Game.getClient();
        List<Npc> npcs = new ArrayList<>();
        int[] indices = client.getNpcIndices();
        RSNpc[] raw = client.getNpcs();
        if (indices == null || raw == null) {
            return npcs;
        }

        for (int i = 0; i < client.getNpcCount(); i++) {
            int index = indices[i];
            RSNpc npc = raw[index];
            if (npc != null) {
                npcs.add(new Npc(npc, index));
            }
        }
        return npcs;
    }
}
