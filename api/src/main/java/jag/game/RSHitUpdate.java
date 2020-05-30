package jag.game;

import jag.RSNode;

public interface RSHitUpdate extends RSNode {

    int getStartWidth();

    int getCurrentWidth();

    int getStartCycle();

    int getCurrentCycle();

}