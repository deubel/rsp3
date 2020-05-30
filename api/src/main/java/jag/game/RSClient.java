package jag.game;

import org.rspeer.game.event.EventDispatcher;
import org.rspeer.game.provider.callback.EventMediator;
import jag.RSIntegerNode;
import jag.RSLinkedList;
import jag.RSNodeDeque;
import jag.RSNodeTable;
import jag.audio.RSAudioEffect;
import jag.game.option.RSActionPrioritySetting;
import jag.game.option.RSClientPreferences;
import jag.game.relationship.RSChatHistory;
import jag.game.relationship.RSClanSystem;
import jag.game.relationship.RSRelationshipSystem;
import jag.game.scene.RSCameraCapture;
import jag.game.scene.RSCollisionMap;
import jag.game.scene.RSSceneGraph;
import jag.game.scene.entity.*;
import jag.game.type.*;
import jag.graphics.RSFont;
import jag.graphics.RSSprite;
import jag.js5.RSReferenceTable;
import jag.opcode.*;
import jag.script.RSScriptEvent;

import java.util.Map;

public interface RSClient extends RSGameEngine {

    void processDialogActionPacket(int componentUid, int subComponentIndex);

    RSScriptEvent newScriptEvent();

    void fireScriptEvent(RSScriptEvent event);

    default void fireScriptLater(int id, Object... args) {
        RSScriptEvent event = newScriptEvent();
        Object[] rawArgs = new Object[args.length + 1];
        System.arraycopy(args, 0, rawArgs, 1, args.length);
        rawArgs[0] = id;
        event.setArgs(rawArgs);
        try {
            getEventMediator().getEngineTasks().offer(() -> fireScriptEvent(event));
        } catch (Exception ignored) {

        }
    }

    void setPreviousTimeOfClick(long previousTimeOfClick);

    EventMediator getEventMediator();

    void setEventMediator(EventMediator mediator);

    EventDispatcher getEventDispatcher();

    void setEventDispatcher(EventDispatcher dispatcher);

    int getMouseIdleTime();

    void setMouseIdleTime(int idle);

    int getMouseX();

    void setMouseX(int mouseX);

    RSNodeDeque<RSPendingSpawn> getPendingSpawns();

    int getPendingClickY();

    int getPlayerCount();

    int getMouseY();

    void setMouseY(int mouseY);

    int[] getInterfaceHeights();

    int getDestinationX();

    int getLogoutTimer();

    void setLogoutTimer(int value);

    RSMouseRecorder getMouseRecorder();

    int getDestinationY();

    int getPendingDestinationY();

    void setPendingDestinationY(int y);

    int getViewportHeight();

    int getPendingDestinationX();

    void setPendingDestinationX(int x);

    int getPendingClickX();

    int[] getDrawingAreaPixels();

    int getDrawingAreaBottom();

    String getPassword();

    void setPassword(String password);

    int getDrawingAreaRight();

    int[][][] getFloorHeights();

    boolean isMembersWorld();

    int getEngineCycle();

    int[] getInterfaceWidths();

    RSConnection getConnection();

    int[] getMenuSecondaryArgs();

    int getCursorState();

    RSNodeDeque<RSEffectObject> getEffectObjects();

    int[] getTempVarps();

    int[][] getXteaKeys();

    int getEnergy();

    int[][][] getDynamicSceneData();

    byte[][][] getSceneRenderRules();

    RSClanSystem getClanSystem();

    RSPacketBuffer getPacketBuffer();

    int getPublicChatMode();

    RSPlayer[] getPlayers();

    int[] getNpcIndices();

    int getSpellTargetFlags();

    int getViewportScale();

    int getBaseX();

    int[] getMenuPrimaryArgs();

    int getBaseY();

    String[] getPlayerActions();

    String[] getMenuActions();

    int getPendingMouseY();

    int getPendingMouseX();

    RSWorld[] getWorlds();

    boolean isViewportWalking();

    void setViewportWalking(boolean viewportWalking);

    int getMenuRowCount();

    String getCurrentDomain();

    boolean isMenuOpen();

    int getCanvasHeight();

    long getPendingMouseMoveTime();

    long getPendingTimeOfClick();

    int getRegionChunkX();

    RSActionPrioritySetting getPlayerActionPriority();

    int getRights();

    boolean[] getValidInterfaces();

    int getRedrawMode();

    void setRedrawMode(int redrawMode);

    RSClientPreferences getPreferences();

    RSInterfaceComponent getPleaseWaitComponent();

    int getViewportWidth();

    int getRegionChunkY();

    int getCameraY();

    RSNodeTable<RSIntegerNode> getInterfaceConfigs();

    boolean isSpellSelected();

    int getCameraZ();

    int getDrawingAreaLeft();

    int getCameraX();

    int getMapRotation();

    RSAudioEffect[] getAudioEffects();

    RSNodeDeque<RSPickable>[][][] getPickableNodeDeques();

    int[] getPlayerIndices();

    String getLoginResponse1();

    String getLoginResponse3();

    String getLoginResponse2();

    default String getLoginResponse() {
        String[] lines = {getLoginResponse1(), getLoginResponse2(), getLoginResponse3()};
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (line != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    boolean isInInstancedScene();

    int[] getLevels();

    RSNpc[] getNpcs();

    RSNodeTable<RSInventory> getInventories();

    int getCurrentWorld();

    RSClient getInstance();

    int getLoginStep();

    RSCollisionMap[] getCollisionMaps();

    Map<Integer, RSChatHistory> getChatHistory();

    int getOnCursorCount();

    RSLinkedList<RSClassStructure> getClassStructures();

    int getPlayerIndex();

    long[] getOnCursorUids();

    String[] getMenuTargets();

    RSSceneGraph getSceneGraph();

    boolean isCameraLocked();

    int getMapState();

    int[] getInterfacePositionsY();

    int getCameraYaw();

    int[] getInterfacePositionsX();

    int getLatestSelectedItemIndex();

    int getAudioEffectCount();

    RSStockMarketOffer[] getStockMarketOffers();

    RSRelationshipSystem getRelationshipSystem();

    int getCanvasWidth();

    int getDrawingAreaHeight();

    int getFloorLevel();

    RSActionPrioritySetting getNpcActionPriority();

    int getHintArrowNpcIndex();

    int getWeight();

    int[] getExperiences();

    int[] getMapRegions();

    RSNodeDeque<RSProjectile> getProjectiles();

    int[] getMenuTertiaryArgs();

    int getHintArrowX();

    int getHintArrowY();

    short[] getStockMarketSearchResults();

    int getHintArrowZ();

    int getMenuHeight();

    int[] getCurrentLevels();

    int getGameState();

    void setGameState(int state);

    boolean[] getPlayerActionPriorities();

    int getClickMeta();

    void setClickMeta(int meta);

    int getClickX();

    void setClickX(int clickX);

    int getClickY();

    void setClickY(int clickY);

    int[] getVarps();

    int getTradeChatMode();

    boolean[] getMenuShiftClickActions();

    byte[] getRandom();

    RSNodeTable<RSSubInterface> getSubInterfaces();

    long getTimeOfClick();

    void setTimeOfClick(long timeOfClick);

    int getHintArrowPlayerIndex();

    boolean isLowMemory();

    int getBootState();

    RSPlayer getPlayer();

    int getNpcCount();

    RSInterfaceComponent[][] getInterfaces();

    RSFont getFont_p12full();

    boolean isLoadMembersItemDefinitions();

    void setLoadMembersItemDefinitions(boolean loadMembersItemDefinitions);

    int getCurrentWorldMask();

    int getHintArrowType();

    int getRootInterfaceIndex();

    boolean isLoginWorldSelectorOpen();

    int getBuild();

    RSConnectionContext getConnectionContext();

    long getMouseMoveTime();

    void setMouseMoveTime(long mouseMoveTime);

    int getDrawingAreaWidth();

    int getItemSelectionState();

    int[] getMenuOpcodes();

    RSCameraCapture[] getCameraCaptures();

    int getDrawingAreaTop();

    String getUsername();

    void setUsername(String username);

    void setLoginStep(int step);

    String getTotp();

    void setTotp(String username);

    int getCameraPitch();

    RSItemDefinition getItemDefinition(int id);

    void absoluteToViewport(int arg0, int arg1, int arg2);

    void spawnObjectLater(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8);

    boolean loadWorlds();

    void processComponentRendering(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6);

    RSObjectDefinition getObjectDefinition(int id);

    RSEffectAnimation getEffectAnimation(int id);

    RSNpcDefinition getNpcDefinition(int id);

    void messageReceived(int type, String arg1, String arg2, String arg3);

    RSDefinitionProperty getDefinitionProperty(int id);

    RSAnimationSequence getAnimationSequence(int id);

    RSHitsplatDefinition getHitsplatDefinition(int id);

    RSReferenceTable getArchive(int arg0, boolean arg1, boolean arg2, boolean arg3);

    void processAction(int sec, int ter, int op, int pri, String action, String target, int crosshairX, int crosshairY);

    void resetDrawingArea();

    void setWorld(RSWorld world);

    void setLoginMessages(String line1, String line2, String line3);

    RSVarpbit getVarpbit(int id);

    RSSprite getItemSprite(int arg0, int arg1, int arg2, int arg3, int arg4, boolean arg5);

    void buildComponentMenu(RSInterfaceComponent arg0, int arg1, int arg2);
}