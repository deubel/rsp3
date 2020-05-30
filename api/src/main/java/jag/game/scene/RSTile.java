package jag.game.scene;

import jag.RSNode;
import jag.game.scene.entity.*;

import java.util.ArrayList;
import java.util.List;

public interface RSTile extends RSNode {

    RSBoundary getBoundary();

    RSPickableStack getPickableStack();

    int getSceneY();

    int getSceneX();

    RSBoundaryDecor getBoundaryDecor();

    RSTileDecor getDecor();

    int getFloorLevel();

    RSTilePaint getPaint();

    RSTileModel getModel();

    RSEntityMarker[] getMarkers();

    default List<RSSceneObject> getObjects() {
        List<RSSceneObject> components = new ArrayList<>();
        for (RSEntityMarker entity : getMarkers()) {
            if (entity != null && entity.getType() == 2) {
                components.add(entity);
            }
        }

        RSBoundary boundary = getBoundary();
        RSBoundaryDecor boundaryDecor = getBoundaryDecor();
        RSTileDecor tileDecor = getDecor();

        if (boundary != null) {
            components.add(boundary);
        }

        if (boundaryDecor != null) {
            components.add(boundaryDecor);
        }

        if (tileDecor != null) {
            components.add(tileDecor);
        }

        return components;
    }
}