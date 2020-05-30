package jag.game.type;

import jag.RSIterableNodeTable;
import jag.RSNode;

public interface RSNpcDefinition extends RSTransformableDefinition {

    int getPrayerIcon();

    boolean isRenderingPrioritized();

    short[] getTextures();

    int getIdleAnimation();

    short[] getColors();

    int getScaleXY();

    boolean isRenderedOnMinimap();

    int getScaleZ();

    int getSize();

    int[] getModelIds();

    short[] getNewColors();

    String getName();

    int getWalkAnimation();

    int getId();

    int getCombatLevel();

    String[] getActions();

    short[] getNewTextures();

    RSIterableNodeTable<? extends RSNode> getProperties();

}