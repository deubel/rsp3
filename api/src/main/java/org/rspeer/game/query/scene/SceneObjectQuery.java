package org.rspeer.game.query.scene;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.adapter.type.Actionable;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.position.Position;
import org.rspeer.game.query.results.SceneNodeQueryResults;
import org.rspeer.game.scene.Players;
import org.rspeer.game.scene.Scene;
import jag.game.scene.RSTile;
import jag.game.scene.entity.RSSceneObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class SceneObjectQuery extends SceneNodeQuery<SceneObject, SceneObjectQuery> implements
        Actionable.Query<SceneObjectQuery>,
        Identifiable.Query<SceneObjectQuery>,
        Nameable.Query<SceneObjectQuery> {

    private final Supplier<List<SceneObject>> provider;

    private Class<? extends RSSceneObject>[] types = null;

    private int[] ids = null;
    private int[] mapFunctions = null;
    private int[] mapScenes = null;

    private int[] colors = null;

    private String[] names = null;
    private String[] nameContains = null;
    private String[] actions = null;

    public SceneObjectQuery(Supplier<List<SceneObject>> provider) {
        this.provider = provider;
    }

    @Override
    public Supplier<List<SceneObject>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected SceneNodeQueryResults<SceneObject> createQueryResults(Collection<SceneObject> raw) {
        return new SceneNodeQueryResults<>(raw);
    }

    public SceneObjectQuery names(String... names) {
        this.names = names;
        return self();
    }

    public SceneObjectQuery nameContains(String... names) {
        this.nameContains = names;
        return self();
    }

    public SceneObjectQuery actions(String... actions) {
        this.actions = actions;
        return self();
    }

    public SceneObjectQuery mapFunctions(int... mapFunctions) {
        this.mapFunctions = mapFunctions;
        return self();
    }

    public SceneObjectQuery mapScenes(int... mapScenes) {
        this.mapScenes = mapScenes;
        return self();
    }

    public SceneObjectQuery types(Class<? extends RSSceneObject>... types) {
        this.types = types;
        return self();
    }

    public SceneObjectQuery colors(int... colors) {
        this.colors = colors;
        return self();
    }


    public SceneObjectQuery ids(int... ids) {
        this.ids = ids;
        return this;
    }

    @Override
    public SceneObjectQuery within(SceneNode src, int distance) {
        provider(() -> {
            List<SceneObject> objects = new ArrayList<>();
            for (int x = -distance; x < distance; x++) {
                for (int y = -distance; y < distance; y++) {
                    Position position = src.getPosition().toScene().translate(x, y);
                    RSTile tile = Scene.getTile(position.getX(), position.getY(), position.getFloorLevel());
                    if (tile != null) {
                        for (RSSceneObject object : tile.getObjects()) {
                            objects.add(new SceneObject(object));
                        }
                    }
                }
            }
            return objects;
        });
        return super.within(src, distance);
    }

    @Override
    public SceneObjectQuery within(int distance) {
        Player src = Players.getLocal();
        if (src == null) {
            return this;
        }

        provider(() -> {
            List<SceneObject> objects = new ArrayList<>();
            for (int x = -distance; x < distance; x++) {
                for (int y = -distance; y < distance; y++) {
                    Position position = src.getPosition().toScene().translate(x, y);
                    RSTile tile = Scene.getTile(position.getX(), position.getY(), position.getFloorLevel());
                    if (tile != null) {
                        for (RSSceneObject object : tile.getObjects()) {
                            objects.add(new SceneObject(object));
                        }
                    }
                }
            }
            return objects;
        });
        return super.within(distance);
    }

    public SceneObjectQuery on(Position... positions) {
        provider(() -> {
            List<SceneObject> objects = new ArrayList<>();
            for (Position position : positions) {
                position = position.toScene();
                RSTile tile = Scene.getTile(position.getX(), position.getY(), position.getFloorLevel());
                if (tile != null) {
                    for (RSSceneObject object : tile.getObjects()) {
                        objects.add(new SceneObject(object));
                    }
                }
            }
            return objects;
        });
        return super.on(positions);
    }

    @Override
    public boolean test(SceneObject obj) {
        if (names != null && !ArrayUtils.containsExactInsensitive(names, obj.getName())) {
            return false;
        }

        if (ids != null && !ArrayUtils.contains(ids, obj.getId())) {
            return false;
        }

        if (nameContains != null && !ArrayUtils.containsPartialInsensitive(nameContains, obj.getName())) {
            return false;
        }

        if (actions != null && !obj.containsAction(x -> ArrayUtils.containsExactInsensitive(actions, x))) {
            return false;
        }

        if (colors != null) {
            boolean match = false;
            outer:
            for (short color : obj.getDefinition().getColors()) {
                for (int filter : colors) {
                    if (filter == color) {
                        match = true;
                        break outer;
                    }
                }
            }


            if (!match) {
                return false;
            }
        }

        if (types != null && !ArrayUtils.contains(types, obj.getProvider().getClass())) {
            return false;
        }

        if (mapFunctions != null && !ArrayUtils.contains(mapFunctions, obj.getDefinition().getMapFunction())) {
            return false;
        }

        if (mapScenes != null && !ArrayUtils.contains(mapScenes, obj.getDefinition().getMapScene())) {
            return false;
        }

        return super.test(obj);
    }

    @Override
    public SceneObjectQuery self() {
        return this;
    }
}
