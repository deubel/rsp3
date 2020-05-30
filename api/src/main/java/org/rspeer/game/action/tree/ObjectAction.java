package org.rspeer.game.action.tree;

import org.rspeer.game.Game;
import org.rspeer.game.action.Action;
import org.rspeer.game.action.ActionOpcode;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.position.Position;
import org.rspeer.game.scene.Scene;
import jag.game.scene.RSSceneGraph;
import jag.game.scene.RSTile;
import jag.game.scene.entity.RSSceneObject;

import java.util.List;

public class ObjectAction extends Action {

    private final Position position;

    public ObjectAction(int opcode, int id, int sceneX, int sceneY) {
        super(opcode, id, sceneX, sceneY);
        position = Position.fromRelative(sceneX, sceneY, Scene.getFloorLevel());
    }

    public ObjectAction(ActionOpcode opcode, int id, int sceneX, int sceneY) {
        super(opcode, id, sceneX, sceneY);
        position = Position.fromRelative(sceneX, sceneY, Scene.getFloorLevel());
    }

    public ObjectAction(ActionOpcode opcode, int id, Position position) {
        super(opcode, id, position.toScene().getX(), position.toScene().getY());
        this.position = position;
    }

    private static long compileUid(int x, int y, int type, boolean uninteractive, int id) {
        long uid = (long) ((x & 0x7f) | (y & 0x7f) << 7 | (type & 0x3) << 14) | ((long) id & 0xffffffffL) << 17;
        if (uninteractive) {
            uid |= 0x10000L;
        }
        return uid;
    }

    private static RSSceneObject lookup(long uid) {
        int regionX = (int) (uid & 0x7f);
        int regionY = (int) (uid >> 7 & 0x7f);
        if (regionX >= 104 || regionX < 0 || regionY >= 104 || regionY < 0) {
            return null;
        }

        RSSceneGraph graph = Game.getClient().getSceneGraph();
        RSTile[][][] tiles = graph.getTiles();
        RSTile tile = tiles[Scene.getFloorLevel()][regionX][regionY];
        if (tile != null) {
            List<RSSceneObject> raw = tile.getObjects();
            for (RSSceneObject obj : raw) {
                if (obj.getUid() == uid) {
                    return obj;
                }
            }
        }
        return null;
    }

    public int getId() {
        return primary;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString())
                .append("[x=")
                .append(position.getX())
                .append(",y=")
                .append(position.getY());
        SceneObject obj = getSource();
        if (obj != null) {
            builder.append(",id=")
                    .append(obj.getId());
        }
        return builder.append("]").toString();
    }

    public SceneObject getSource() {
        long uid = compileUid(secondary, tertiary, 2, false, primary);
        RSSceneObject raw = lookup(uid);
        return raw != null ? new SceneObject(raw) : null;
    }
}
