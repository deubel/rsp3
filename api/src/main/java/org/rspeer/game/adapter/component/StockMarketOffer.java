package org.rspeer.game.adapter.component;

import org.rspeer.game.adapter.Adapter;
import org.rspeer.game.component.Item;
import jag.game.RSStockMarketOffer;

public class StockMarketOffer extends Adapter<RSStockMarketOffer> {

    private static final int PROGRESS_MASK = 2;
    private static final int FINISH_MASK = 4;
    private static final int SELLING_MASK = 8;

    private final int index;

    public StockMarketOffer(RSStockMarketOffer provider, int index) {
        super(provider);
        this.index = index;
    }

    private byte getState() {
        RSStockMarketOffer provider = getProvider();
        return provider != null ? provider.getState() : -1;
    }

    public int getIndex() {
        return index;
    }

    public Item getItem() {
        RSStockMarketOffer provider = getProvider();
        if (provider == null || provider.getState() == 0) {
            return null;
        }
        return Item.unbound(index, provider.getItemId(), provider.getItemQuantity());
    }

    public int getItemPrice() {
        RSStockMarketOffer provider = getProvider();
        return provider != null ? provider.getItemPrice() : -1;
    }

    public int getWealthTransferred() {
        RSStockMarketOffer provider = getProvider();
        return provider != null ? provider.getSpent() : -1;
    }

    public int getAmountTransferred() {
        RSStockMarketOffer provider = getProvider();
        return provider != null ? provider.getTransferred() : -1;
    }

    public Type getType() {
        RSStockMarketOffer provider = getProvider();
        if (provider == null) {
            return Type.EMPTY;
        }

        byte state = provider.getState();
        if (state == 0) {
            return Type.EMPTY;
        } else if ((getState() & SELLING_MASK) == SELLING_MASK) {
            return Type.SELL;
        }
        return Type.BUY;
    }

    public Progress getProgress() {
        RSStockMarketOffer provider = getProvider();
        if (provider == null) {
            return Progress.EMPTY;
        }

        byte state = provider.getState();
        if ((state & PROGRESS_MASK) == PROGRESS_MASK) {
            return Progress.IN_PROGRESS;
        } else if ((state & FINISH_MASK) == FINISH_MASK) {
            return Progress.FINISHED;
        }
        return Progress.EMPTY;
    }

    public enum Type {
        BUY,
        SELL,
        EMPTY
    }

    public enum Progress {
        EMPTY,
        IN_PROGRESS,
        FINISHED
    }

    public enum CollectionAction {

        NOTE("Collect-notes"),
        ITEM("Collect-items"),
        BANK("Bank");

        private final String text;

        CollectionAction(String text) {
            this.text = text;
        }
    }
}
