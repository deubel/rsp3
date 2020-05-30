package jag.game.type;

import jag.RSIterableNodeTable;
import jag.RSNode;

public interface RSObjectDefinition extends RSTransformableDefinition {

    short[] getTextures();

    int getMapSceneId();

    int getAmbient();

    int getScaleZ();

    short[] getColors();

    int getScaleX();

    int getScaleY();

    int getTranslateZ();

    int[] getModelIds();

    int getTranslateY();

    int getTranslateX();

    short[] getNewColors();

    int getMapFunction();

    int getId();

    short[] getNewTextures();

    int getClipType();

    int getItemSupport();

    boolean isSolid();

    boolean isRotated();

    int getAmbientSoundId();

    int getMapDoorFlag();

    int getAnimation();

    boolean isClipped();

    int getSizeX();

    boolean isImpenetrable();

    boolean isProjectileClipped();

    int getContrast();

    String getName();

    String[] getActions();

    int getSizeY();

    RSIterableNodeTable<? extends RSNode> getProperties();

}