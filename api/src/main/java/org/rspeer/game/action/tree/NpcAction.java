package org.rspeer.game.action.tree;

import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Npcs;

public class NpcAction extends PathingEntityAction<Npc> {

    public NpcAction(ActionOpcode opcode, int index) {
        super(opcode, index);
    }

    public NpcAction(int opcode, int index) {
        super(opcode, index);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString())
                .append("[index=")
                .append(getIndex());
        Npc npc = getSource();
        if (npc != null) {
            Position position = npc.getPosition();
            builder.append(",name=")
                    .append(npc.getName())
                    .append(",id=")
                    .append(npc.getId())
                    .append(",x=")
                    .append(position.getX())
                    .append(",y=")
                    .append(position.getY());
        }
        return builder.append("]").toString();
    }

    @Override
    public Npc getSource() {
        return Npcs.getAt(getIndex());
    }
}
