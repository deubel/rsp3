package org.rspeer.game.query;

import org.rspeer.commons.typing.SelfTyped;
import org.rspeer.game.query.results.QueryResults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Query<T, QB extends SelfTyped<QB>, QR extends QueryResults<T, QR>> implements Cloneable, SelfTyped<QB>, Predicate<T> {

    private Supplier<List<T>> provider;
    private Predicate<T> customFilter;

    public abstract Supplier<List<T>> getDefaultProvider();

    public final QR results() {
        List<T> data = provider != null ? provider.get() : getDefaultProvider().get();
        List<T> filtered = new ArrayList<>();
        for (T elem : data) {
            if (test(elem)) {
                filtered.add(elem);
            }
        }

        return createQueryResults(filtered);
    }

    public final QB filter(Predicate<T> filter) {
        if (customFilter != null) {
            Predicate<? super T> old = customFilter;
            customFilter = t -> old.test(t) && filter.test(t);
        } else {
            customFilter = filter;
        }

        return self();
    }

    public final QB provider(Supplier<List<T>> provider) {
        this.provider = provider;
        return self();
    }

    protected abstract QR createQueryResults(Collection<T> raw);

    @Override
    public boolean test(T t) {
        return customFilter == null || customFilter.test(t);
    }
}
