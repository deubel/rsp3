package jag.game;

import jag.RSProvider;

public interface RSPlayerAppearance extends RSProvider {

    int[] getIds();

    int[] getEquipmentIds();

    int getTransformedNpcId();

    boolean isFemale();

}