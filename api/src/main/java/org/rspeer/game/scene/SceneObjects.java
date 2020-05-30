package org.rspeer.game.scene;

import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.position.Position;
import org.rspeer.game.position.RelativePosition;
import org.rspeer.game.query.scene.SceneObjectQuery;
import jag.game.scene.RSTile;
import jag.game.scene.entity.RSSceneObject;

import java.util.ArrayList;
import java.util.List;

public class SceneObjects {

    public static SceneObjectQuery query(boolean includeOtherFloors) {
        return new SceneObjectQuery(() -> SceneObjects.getLoaded(includeOtherFloors));
    }

    public static SceneObjectQuery query() {
        return query(false);
    }

    private static List<SceneObject> getLoaded(boolean includeOtherFloors) {
        List<SceneObject> objects = new ArrayList<>();
        int zMin = includeOtherFloors ? 0 : Scene.getFloorLevel();
        int zMax = includeOtherFloors ? 4 : zMin + 1;
        for (int x = 0; x < 104; x++) {
            for (int y = 0; y < 104; y++) {
                for (int z = zMin; z < zMax; z++) {
                    Position position = new RelativePosition(x, y, z);
                    mapObjectsAtPosition(position, objects);
                }
            }
        }

        return objects;
    }

    private static void mapObjectsAtPosition(Position pos, List<SceneObject> toAdd) {
        RSTile tile = Scene.getTile(pos.getX(), pos.getY(), pos.getFloorLevel());
        if (tile != null) {
            for (RSSceneObject object : tile.getObjects()) {
                toAdd.add(new SceneObject(object));
            }
        }
    }
}
