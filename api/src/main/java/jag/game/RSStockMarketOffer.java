package jag.game;

import jag.RSProvider;

public interface RSStockMarketOffer extends RSProvider {

    int getItemId();

    int getTransferred();

    int getItemQuantity();

    int getSpent();

    int getItemPrice();

    byte getState();

}