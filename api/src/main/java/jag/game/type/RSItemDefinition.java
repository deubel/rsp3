package jag.game.type;

import jag.RSIterableNodeTable;
import jag.RSNode;
import jag.game.scene.entity.RSUnlitModel;

public interface RSItemDefinition extends RSDefinition {

    int getSpriteTranslateX();

    int getResizeY();

    int getResizeX();

    int getResizeZ();

    int getModelId();

    int getAmbient();

    int getSpritePitch();

    int getMaleHeadModel2();

    String[] getGroundActions();

    boolean isMembers();

    int getSpriteScale();

    int getId();

    int getFemaleHeadModel2();

    int getValue();

    int getShiftClickActionIndex();

    int getFemaleHeadModel();

    int getSpriteTranslateY();

    int getStackable();

    int getMaleModel2();

    int getMaleModel1();

    int getNoteTemplateId();

    int getSpriteRoll();

    int getMaleHeadModel();

    int getNoteId();

    int getTeam();

    int getSpriteYaw();

    int getFemaleModel1();

    int getFemaleModel2();

    int[] getVariantStackSizes();

    int[] getVariantIds();

    int getContrast();

    String getName();

    boolean isStockMarketable();

    String[] getActions();

    RSIterableNodeTable<? extends RSNode> getProperties();

    RSUnlitModel getEquipmentModel(boolean arg0);

}