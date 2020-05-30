package org.rspeer.game.query.scene;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.game.adapter.scene.Projectile;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Mobile;
import org.rspeer.game.query.results.SceneNodeQueryResults;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class ProjectileQuery extends SceneNodeQuery<Projectile, ProjectileQuery>
        implements Identifiable.Query<ProjectileQuery>, Mobile.Query<ProjectileQuery> {

    private final Supplier<List<Projectile>> provider;

    private int[] ids = null;
    private Boolean moving = null;

    public ProjectileQuery(Supplier<List<Projectile>> provider) {
        this.provider = provider;
    }

    @Override
    public Supplier<List<Projectile>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected SceneNodeQueryResults<Projectile> createQueryResults(Collection<Projectile> raw) {
        return new SceneNodeQueryResults<>(raw);
    }

    @Override
    public ProjectileQuery self() {
        return this;
    }

    @Override
    public ProjectileQuery ids(int... ids) {
        this.ids = ids;
        return self();
    }

    @Override
    public ProjectileQuery moving() {
        moving = true;
        return self();
    }

    @Override
    public ProjectileQuery stationary() {
        moving = false;
        return self();
    }

    @Override
    public boolean test(Projectile e) {
        if (ids != null && !ArrayUtils.contains(ids, e.getId())) {
            return false;
        }

        if (moving != null && moving != e.isMoving()) {
            return false;
        }

        return super.test(e);
    }
}
