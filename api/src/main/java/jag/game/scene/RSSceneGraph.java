package jag.game.scene;

import jag.RSProvider;
import jag.game.scene.entity.RSEntityMarker;

public interface RSSceneGraph extends RSProvider {

    RSTile[][][] getTiles();

    RSEntityMarker[] getTempMarkers();

}