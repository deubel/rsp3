package jag.graphics;

import jag.RSDoublyLinkedNode;

public interface RSSprite extends RSDoublyLinkedNode {

    int[] getPixels();

    int getWidth();

    int getHeight();

}