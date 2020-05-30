package org.rspeer.game.scene;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.NodeDeque;
import org.rspeer.game.adapter.scene.Pickable;
import org.rspeer.game.position.Position;
import org.rspeer.game.query.scene.PickableQuery;
import jag.RSNodeDeque;
import jag.game.scene.entity.RSPickable;

import java.util.ArrayList;
import java.util.List;

public class Pickables {

    public static PickableQuery query() {
        return new PickableQuery(Pickables::getLoaded);
    }

    /**
     * For internal use only
     */
    public static List<Pickable> getAt(Position position) {
        List<Pickable> items = new ArrayList<>();
        RSNodeDeque<RSPickable>[][][] all = Game.getClient().getPickableNodeDeques();
        if (all == null) {
            return items;
        }

        Position relative = position.toScene();
        if (relative.isInScene()) {
            RSNodeDeque<RSPickable> deque = all[relative.getFloorLevel()][relative.getX()][relative.getY()];
            if (deque == null) {
                return items;
            }

            NodeDeque<RSPickable> wrapped = new NodeDeque<>(deque);
            for (RSPickable pickable : wrapped) {
                items.add(new Pickable(pickable, position));
            }
        }
        return items;
    }

    private static List<Pickable> getLoaded() {
        List<Pickable> loaded = new ArrayList<>();
        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                loaded.addAll(getAt(Position.fromRelative(x, y, Scene.getFloorLevel())));
            }
        }
        return loaded;
    }
}
