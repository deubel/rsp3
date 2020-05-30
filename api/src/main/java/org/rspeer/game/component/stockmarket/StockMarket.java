package org.rspeer.game.component.stockmarket;

import org.rspeer.game.Game;
import org.rspeer.game.adapter.component.StockMarketOffer;
import org.rspeer.game.query.component.StockMarketOfferQuery;
import jag.game.RSStockMarketOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StockMarket {

    private static List<StockMarketOffer> getOffers() {
        RSStockMarketOffer[] raw = Game.getClient().getStockMarketOffers();
        if (raw == null) {
            return Collections.emptyList();
        }

        List<StockMarketOffer> offers = new ArrayList<>();
        for (int i = 0; i < raw.length; i++) {
            RSStockMarketOffer offer = raw[i];
            if (offer != null) {
                offers.add(new StockMarketOffer(offer, i));
            }
        }
        return offers;
    }

    public static StockMarketOfferQuery query() {
        return new StockMarketOfferQuery(StockMarket::getOffers);
    }
}
