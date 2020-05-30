package org.rspeer.game;

import jag.game.RSClient;
import jag.game.type.RSTransformableDefinition;
import jag.game.type.RSVarpbit;
import org.rspeer.game.adapter.scene.definition.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

//TODO rework this entirely
public class Definitions {

    private static final Map<Integer, ItemDefinition> ITEMS = Collections.synchronizedMap(new LinkedHashMap<>());
    private static final Map<Integer, ObjectDefinition> OBJECTS = Collections.synchronizedMap(new LinkedHashMap<>());
    private static final Map<Integer, NpcDefinition> NPCS = Collections.synchronizedMap(new LinkedHashMap<>());
    private static final Map<Integer, RSVarpbit> VARBITS = Collections.synchronizedMap(new LinkedHashMap<>());

    private static final int LOAD_LIMIT = 1 << 16;

    public static synchronized void populate() {
        RSClient client = Game.getClient();
        if (client != null) {
            boolean membersWorld = client.isMembersWorld();
            client.setLoadMembersItemDefinitions(true);
            loadDefinitions(ITEMS, id -> new ItemDefinition(Game.getClient().getItemDefinition(id)));
            client.setLoadMembersItemDefinitions(membersWorld);
            loadDefinitions(NPCS, id -> new NpcDefinition(Game.getClient().getNpcDefinition(id)));
            loadDefinitions(OBJECTS, id -> new ObjectDefinition(Game.getClient().getObjectDefinition(id)));
            loadDefinitions(VARBITS, id -> Game.getClient().getVarpbit(id));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void loadDefinitions(
            Map<Integer, T> dest,
            Function<Integer, T> invoker) {
        for (int i = 0; i < LOAD_LIMIT; i++) {
            T definition = invoker.apply(i);
            if (definition != null) {
                if (definition instanceof TransformableDefinition) {
                    TransformableDefinition transformed = ((TransformableDefinition) definition).transform();
                    if (transformed != null) {
                        dest.put(i, (T) transformed);
                    } else {
                        dest.put(i, definition);
                    }
                } else if (definition instanceof Definition) {
                    dest.put(i, definition);
                } else {
                    dest.put(i, definition);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <K extends RSTransformableDefinition, T extends TransformableDefinition<K, T>> T getTransformable(int id, Class<T> type) {
        T td;
        if (type.equals(ObjectDefinition.class)) {
            td = (T) getObject(id);
        } else {
            td = (T) getNpc(id);
        }

        if (td != null) {
            T transform = td.transform();
            if (transform != null) {
                return transform;
            }
            return td;
        }

        return null;
    }

    public static RSVarpbit getVarpbit(int id) {
        return VARBITS.get(id);
    }

    public static NpcDefinition getNpc(int id) {
        return NPCS.get(id);
    }

    public static ObjectDefinition getObject(int id) {
        return OBJECTS.get(id);
    }

    public static ItemDefinition getItem(int id) {
        return ITEMS.get(id);
    }

    public static ItemDefinition getItem(String name, Predicate<ItemDefinition> predicate) {
        for (int i = 0; i < LOAD_LIMIT; i++) {
            ItemDefinition d = ITEMS.get(i);
            if (d != null && d.getName() != null && d.getName().equalsIgnoreCase(name) && predicate.test(d)) {
                return d;
            }
        }
        return null;
    }

    public static Map<Integer, ObjectDefinition> getObjects() {
        return OBJECTS;
    }

    public static Map<Integer, ItemDefinition> getItems() {
        return ITEMS;
    }

    public static Map<Integer, NpcDefinition> getNpcs() {
        return NPCS;
    }
}
