package org.rspeer.game.query.component;

import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.Range;
import org.rspeer.game.adapter.component.StockMarketOffer;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.game.component.Item;
import org.rspeer.game.query.Query;
import org.rspeer.game.query.results.StockMarketOfferQueryResults;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class StockMarketOfferQuery extends Query<StockMarketOffer, StockMarketOfferQuery, StockMarketOfferQueryResults>
        implements Identifiable.Query<StockMarketOfferQuery>,
        Nameable.Query<StockMarketOfferQuery> {

    private final Supplier<List<StockMarketOffer>> provider;

    private Range stackSize = null;

    private int[] ids = null;

    private String[] names = null;
    private String[] nameContains = null;

    private StockMarketOffer.Progress[] progresses = null;
    private StockMarketOffer.Type[] types = null;

    public StockMarketOfferQuery(Supplier<List<StockMarketOffer>> provider) {
        this.provider = provider;
    }

    @Override
    public Supplier<List<StockMarketOffer>> getDefaultProvider() {
        return provider;
    }

    @Override
    protected StockMarketOfferQueryResults createQueryResults(Collection<StockMarketOffer> raw) {
        return new StockMarketOfferQueryResults(raw);
    }

    public StockMarketOfferQuery ids(int... ids) {
        this.ids = ids;
        return self();
    }

    public StockMarketOfferQuery names(String... names) {
        this.names = names;
        return self();
    }

    public StockMarketOfferQuery nameContains(String... names) {
        this.nameContains = names;
        return self();
    }

    public StockMarketOfferQuery stackSize(int minInclusive) {
        return stackSize(minInclusive, Integer.MAX_VALUE);
    }

    public StockMarketOfferQuery stackSize(int minInclusive, int maxInclusive) {
        stackSize = Range.of(minInclusive, maxInclusive);
        return self();
    }

    public StockMarketOfferQuery count(int minInclusive) {
        return count(minInclusive, Integer.MAX_VALUE);
    }

    public StockMarketOfferQuery count(int minInclusive, int maxInclusive) {
        stackSize = Range.of(minInclusive, maxInclusive);
        return self();
    }

    public StockMarketOfferQuery progress(StockMarketOffer.Progress... progresses) {
        this.progresses = progresses;
        return self();
    }

    public StockMarketOfferQuery types(StockMarketOffer.Type... types) {
        this.types = types;
        return self();
    }

    @Override
    public boolean test(StockMarketOffer offer) {
        if (types != null && !ArrayUtils.contains(types, offer.getType())) {
            return false;
        }

        if (progresses != null && !ArrayUtils.contains(progresses, offer.getProgress())) {
            return false;
        }

        Item item = offer.getItem();
        if (ids != null && item != null && !ArrayUtils.contains(ids, item.getId())) {
            return false;
        }

        if (names != null && item != null && !ArrayUtils.containsExactInsensitive(names, item.getName())) {
            return false;
        }

        if (nameContains != null && item != null && !ArrayUtils.containsPartialInsensitive(nameContains, item.getName())) {
            return false;
        }

        if (stackSize != null && item != null && !stackSize.within(item.getStackSize())) {
            return false;
        }

        return super.test(offer);
    }

    @Override
    public StockMarketOfferQuery self() {
        return this;
    }
}
