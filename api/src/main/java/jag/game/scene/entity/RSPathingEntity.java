package jag.game.scene.entity;

import jag.RSLinkedList;
import jag.game.RSHealthBar;

public interface RSPathingEntity extends RSEntity {

    byte[] getRouteWaypointsTraversed();

    int getWalkingStance();

    int getTargetIndex();

    int getAnimationFrameCycle();

    int[] getRouteWaypointsX();

    int[] getHitsplatIds();

    int getRouteWaypointCount();

    int getAbsoluteX();

    int getAbsoluteY();

    int[] getSpecialHitsplats();

    int getAnimationFrame();

    byte getHitsplatCount();

    int getEffect();

    int getEffectFrame();

    int getOrientation();

    String getOverheadText();

    int getAnimationDelay();

    int[] getHitsplatTypes();

    RSLinkedList<RSHealthBar> getHealthBars();

    int getAnimation();

    int[] getHitsplats();

    int[] getRouteWaypointsY();

    int getStanceFrame();

    int[] getHitsplatCycles();

    int getStance();

    void addHitSplat(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5);

    void addHitUpdate(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5);
}