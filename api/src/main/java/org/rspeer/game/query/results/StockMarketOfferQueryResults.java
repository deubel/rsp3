package org.rspeer.game.query.results;

import org.rspeer.game.adapter.component.StockMarketOffer;

import java.util.Collection;

public class StockMarketOfferQueryResults extends QueryResults<StockMarketOffer, StockMarketOfferQueryResults> {

    public StockMarketOfferQueryResults(Collection<StockMarketOffer> results) {
        super(results);
    }

    @Override
    public StockMarketOfferQueryResults self() {
        return this;
    }
}
