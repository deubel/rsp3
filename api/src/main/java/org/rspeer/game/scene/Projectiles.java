package org.rspeer.game.scene;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.NodeDeque;
import org.rspeer.game.adapter.scene.Projectile;
import org.rspeer.game.query.scene.ProjectileQuery;
import jag.RSNodeDeque;
import jag.game.scene.entity.RSProjectile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Projectiles {

    private static List<Projectile> getLoaded() {
        RSNodeDeque<RSProjectile> raw = Game.getClient().getProjectiles();
        if (raw == null) {
            return Collections.emptyList();
        }

        List<Projectile> projectiles = new ArrayList<>();
        NodeDeque<RSProjectile> deque = new NodeDeque<>(raw);

        RSProjectile projectile = deque.head();
        while (projectile != null) {
            if (Game.getEngineCycle() >= projectile.getStartCycle()) {
                projectiles.add(new Projectile(projectile));
            }
            projectile = deque.next();
        }
        return projectiles;
    }

    public static ProjectileQuery query() {
        return new ProjectileQuery(Projectiles::getLoaded);
    }
}
