package jag.game.type;

import jag.RSDoublyLinkedNode;
import jag.graphics.RSSprite;

public interface RSHitsplatDefinition extends RSDoublyLinkedNode {

    int getIconId();

    String getAmount();

    int getForeground();

    int getRightSpriteId();

    int getOffsetX();

    int getDuration();

    int getMiddleSpriteId();

    int getFade();

    int getOffsetY();

    int getVarpIndex();

    int getFontId();

    int getLeftSpriteId();

    int getVarpbitIndex();

    int getComparisonType();

    int[] getTransformIds();

    RSSprite getLeftSprite();

    RSSprite getIcon();

    RSSprite getRightSprite();

    RSSprite getMiddleSprite();

}