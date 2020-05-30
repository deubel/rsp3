package org.rspeer.game.scene;

import org.rspeer.game.Game;
import org.rspeer.game.position.Position;
import jag.game.RSClient;
import jag.game.scene.RSSceneGraph;
import jag.game.scene.RSTile;

public class Scene {

    public static Position getBase() {
        RSClient ctx = Game.getClient();
        return new Position(ctx.getBaseX(), ctx.getBaseY(), ctx.getFloorLevel());
    }

    public static int getFloorLevel() {
        return Game.getClient().getFloorLevel();
    }

    public static Dynamic getDynamic() {
        RSClient client = Game.getClient();
        if (client.isInInstancedScene()) {
            return new Dynamic(client.getDynamicSceneData());
        }
        return null;
    }

    public static byte[][][] getRenderRules() {
        return Game.getClient().getSceneRenderRules();
    }

    public static int[][][] getFloorHeights() {
        return Game.getClient().getFloorHeights();
    }

    public static RSTile getTile(int relativeX, int relativeY, int floorLevel) {
        if (floorLevel < 0 || floorLevel > 3
                || relativeX > 103 || relativeY > 103
                || relativeX < 0 || relativeY < 0) {
            return null;
        }

        RSSceneGraph graph = Game.getClient().getSceneGraph();
        if (graph == null) {
            return null;
        }

        RSTile[][][] tiles = graph.getTiles();
        if (tiles == null) {
            return null;
        }

        return tiles[floorLevel][relativeX][relativeY];
    }

    public static class Dynamic {

        private final int[][][] chunks;

        private Dynamic(int[][][] chunks) {
            this.chunks = chunks;
        }

        public Chunk getChunk(Position position) {
            position = position.toScene();
            int x = position.getX() / 8;
            int y = position.getY() / 8;
            if (x >= 13 || y >= 13) {
                return null;
            }
            return new Chunk(position, chunks[position.getFloorLevel()][x][y]);
        }

        public static class Chunk {

            private final Position relative;
            private final int id;

            public Chunk(Position relative, int id) {
                this.relative = relative;
                this.id = id;
            }

            public Position getRelative() {
                return relative;
            }

            public int getId() {
                return id;
            }

            public int getRotation() {
                return id >> 1 & 0x3;
            }

            public int getX() {
                return (id >> 3 & 0x7FF) * 8;
            }

            public int getY() {
                return (id >> 14 & 0x3FF) * 8;
            }

            public int getFloorLevel() {
                return id >> 24 & 0x3;
            }
        }
    }
}
