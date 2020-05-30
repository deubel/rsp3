package org.rspeer.game.action.tree;

import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Players;

public class PlayerAction extends PathingEntityAction<Player> {

    public PlayerAction(ActionOpcode opcode, int index) {
        super(opcode, index);
    }

    public PlayerAction(int opcode, int index) {
        super(opcode, index);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString())
                .append("[index=")
                .append(getIndex());
        Player player = getSource();
        if (player != null) {
            Position position = player.getPosition();
            builder.append(",name=")
                    .append(player.getName())
                    .append(",index=")
                    .append(player.getId())
                    .append(",x=")
                    .append(position.getX())
                    .append(",y=")
                    .append(position.getY());
        }
        return builder.append("]").toString();
    }

    @Override
    public Player getSource() {
        return Players.getAt(getIndex());
    }
}
