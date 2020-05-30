package jag.game;

import org.rspeer.commons.ArrayUtils;
import jag.RSNode;

public interface RSInterfaceComponent extends RSNode {

    RSInterfaceComponent getParent();

    boolean isExplicitlyHidden();

    int getRenderCycle();

    int getModelId();

    int getRootX();

    int getRootY();

    int getRelativeX();

    int getRelativeY();

    int[] getItemIds();

    Object[] getScrollListeners();

    int getZRotation();

    int getType();

    int getViewportHeight();

    int getEnabledMaterialId();

    Object[] getMousePressListeners();

    int[] getItemStackSizes();

    int getBorderThickness();

    int getXRotation();

    int getYLayout();

    int getFontId();

    Object[] getMouseEnterListeners();

    String getText();

    int getXMargin();

    int getContentType();

    int getShadowColor();

    int getHeight();

    int getParentUid();

    int getSubComponentIndex();

    Object[] getTableTransmitArgs();

    default boolean transmits(Object arg) {
        Object[] args = getTableTransmitArgs();
        if (args == null) {
            return false;
        }
        return ArrayUtils.contains(args, arg);
    }

    int[][] getFunctionOpcodes();

    int getSpriteId();

    String getSpellName();

    int getInsetX();

    int getModelType();

    int getMaterialId();

    int getInsetY();

    int getDragArea();

    int getForeground();

    int getAnimation();

    int getItemId();

    void setItemId(int itemId);

    boolean isFlippedHorizontally();

    boolean isFlippedVertically();

    boolean isNoClickThrough();

    int getModelZoom();

    String getName();

    String[] getActions();

    String[] getTableActions();

    RSInterfaceComponent[] getSubComponents();

    int getYMargin();

    Object[] getVarpTransmitArgs();

    Object[] getSkillTransmitArgs();

    int getBoundsIndex();

    int getXPadding();

    int[] getVarpTransmitTriggers();

    int getYPadding();

    int getUid();

    int getButtonType();

    int getModelOffsetY();

    int getModelOffsetX();

    int getTextSpacing();

    int getAlpha();

    boolean isNoScrollThrough();

    int getHorizontalMargin();

    int getYRotation();

    boolean isScrollBar();

    boolean isTextShadowed();

    Object[] getMouseExitListeners();

    int getViewportWidth();

    int[] getSkillTransmitTriggers();

    int getVerticalMargin();

    String getToolTip();

    int getXLayout();

    Object[] getHoverListeners();

    int[] getTableTransmitTriggers();

    Object[] getRenderListeners();

    String getSelectedAction();

    int getWidth();

    int getDragAreaThreshold();

    int getItemStackSize();

    void setItemStackSize(int itemStackSize);

    int getConfig();

    default int getX() {
        return getRootX() + getRelativeX();
    }

    default int getY() {
        return getRootY() + getRelativeY();
    }
}