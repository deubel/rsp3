package org.rspeer.game.component;

import org.rspeer.game.Game;
import org.rspeer.game.Vars;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.query.results.ComponentQueryResults;
import jag.game.option.RSClientPreferences;

import java.util.function.BooleanSupplier;

public class InterfaceOptions {

    private static final int ORBS_DISABLED = 4084;
    private static final int ACCEPT_AID = 4180;
    private static final int REMAINING_XP = 4181;
    private static final int LLNTD = 1627;
    private static final int MOUSE_CAMERA_DISABLED = 4134;
    private static final int STONES_ARRANGEMENT = 4607;
    private static final int CHATBOX_MODE = 4608;
    private static final int SIDE_PANEL_MODE = 4609;
    private static final int SOCIAL_LIST_MODE = 6516;
    private static final int QUEST_TAB_MODE = 8168;

    private static final int BRIGHTNESS = 166;
    private static final int MUSIC_VOLUME = 168;
    private static final int SOUND_EFFECT_VOLUME = 169;
    private static final int AREA_SOUND_VOLUME = 872;
    private static final int CHAT_EFFECTS_DISABLED = 171;
    private static final int SPLIT_PRIVATE_CHAT_ENABLED = 287;
    private static final int PROFANITY_FILTER_DISABLED = 1074;
    private static final int MOUSE_BUTTONS_1 = 170;

    private static final InterfaceAddress STONES_ARRANGEMENT_ADDRESS = new InterfaceAddress(
            () -> Interfaces.query(InterfaceComposite.ADVANCED_INTERFACE_OPTIONS)
                    .actions(x -> x.contains("Side-stones arrangement"))
                    .results().first()
    );

    private static final InterfaceAddress TAB_SIDEPANEL_MODE_ADDRESS = new InterfaceAddress(
            () -> Interfaces.query(InterfaceComposite.ADVANCED_INTERFACE_OPTIONS)
                    .actions(x -> x.contains("Transparent side-panel"))
                    .results().first()
    );

    private static final InterfaceAddress CHATBOX_MODE_ADDRESS = new InterfaceAddress(
            () -> Interfaces.query(InterfaceComposite.ADVANCED_INTERFACE_OPTIONS)
                    .actions(x -> x.contains("Transparent Chatbox"))
                    .results().first()
    );

    private static final InterfaceAddress ADVANCED_OPTIONS_ADDRESS = new InterfaceAddress(
            () -> Interfaces.query(InterfaceComposite.OPTIONS_TAB)
                    .actions(x -> x.equals("Configure"))
                    .results().first()
    );

    private static final InterfaceAddress ACCEPT_AID_ADDRESS = new InterfaceAddress(
            () -> Interfaces.query(InterfaceComposite.OPTIONS_TAB)
                    .actions(x -> x.equals("Toggle Accept Aid"))
                    .results().first()
    );

    private static boolean toggleAdvancedOption(BooleanSupplier condition, InterfaceAddress buttonAddress) {
        if (condition.getAsBoolean()) {
            return true;
        }
        InterfaceComponent button = Interfaces.lookup(buttonAddress);
        if (button == null) {
            InterfaceComponent options = Interfaces.lookup(ADVANCED_OPTIONS_ADDRESS);
            if (options != null && options.interact(x -> true)) {
                return false;
            }
        }
        return button != null && button.interact(x -> true);
    }

    public static TabLayout getTabLayout() {
        return TabLayout.values()[Vars.get(Vars.Type.VARBIT, STONES_ARRANGEMENT)];
    }

    public static boolean setTabLayout(TabLayout layout) {
        return toggleAdvancedOption(() -> getTabLayout() == layout, STONES_ARRANGEMENT_ADDRESS);
    }

    public static ChatboxMode getChatboxMode() {
        return ChatboxMode.values()[Vars.get(Vars.Type.VARBIT, CHATBOX_MODE)];
    }

    public static boolean setChatboxMode(ChatboxMode mode) {
        return toggleAdvancedOption(() -> getChatboxMode() == mode, CHATBOX_MODE_ADDRESS);
    }

    public static SidePanelMode getSidePanelMode() {
        return SidePanelMode.values()[Vars.get(Vars.Type.VARBIT, SIDE_PANEL_MODE)];
    }

    public static boolean setSidePanelMode(SidePanelMode mode) {
        return toggleAdvancedOption(() -> getSidePanelMode() == mode, TAB_SIDEPANEL_MODE_ADDRESS);
    }

    public static SocialTabMode getSocialTabMode() {
        return SocialTabMode.values()[Vars.get(Vars.Type.VARBIT, SOCIAL_LIST_MODE)];
    }

    public static boolean setSocialTabMode(SocialTabMode mode) {
        SocialTabMode active = getSocialTabMode();
        if (active == mode) {
            return true;
        }

        InterfaceComponent button = Interfaces.query(active.composite)
                .actions(x -> x.contains("View"))
                .results().first();
        return button != null && button.interact(x -> true);
    }

    public static QuestTabMode getQuestTabMode() {
        int value = Vars.get(Vars.Type.VARBIT, QUEST_TAB_MODE);
        for (QuestTabMode mode : QuestTabMode.values()) {
            if (value == mode.ordinal()) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Unsupported quest tab: " + value + "!");
    }

    public static boolean setQuestTabMode(QuestTabMode mode) {
        QuestTabMode active = getQuestTabMode();
        if (active == mode) {
            return true;
        }

        InterfaceComponent button = Interfaces.query(InterfaceComposite.QUEST_TABPANE)
                .actions(x -> x.toLowerCase().contains(active.toString().toLowerCase()))
                .results().first();
        return button != null && button.interact(x -> true);
    }

    public static ViewMode getViewMode() {
        if (Game.getClientPreferences().getResizable() != 1) {
            return ViewMode.RESIZABLE_MODE;
        }
        return ViewMode.FIXED_MODE;
    }

    public static boolean setViewMode(ViewMode mode) {
        if (mode == getViewMode()) {
            return true;
        } else if (mode == ViewMode.UNDETERMINED) {
            throw new IllegalArgumentException();
        }

        InterfaceComponent button = Interfaces.query(InterfaceComposite.OPTIONS_TAB)
                .actions(x -> x.toLowerCase().contains(mode.toString().toLowerCase()))
                .results().first();
        return button != null && button.interact(x -> true);
    }

    public static boolean isAcceptingAid() {
        return Vars.get(Vars.Type.VARBIT, ACCEPT_AID) == 1;
    }

    public static boolean setAcceptingAid(boolean state) {
        if (isAcceptingAid() == state) {
            return true;
        }

        InterfaceComponent button = Interfaces.lookup(ACCEPT_AID_ADDRESS);
        return button != null && button.interact(x -> true);
    }

    public enum ViewMode {

        FIXED_MODE(4),
        RESIZABLE_MODE(0),
        UNDETERMINED(-1);

        private final int state;

        ViewMode(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }


        @Override
        public String toString() {
            String name = super.toString();
            return name.charAt(0) + name.substring(1).toLowerCase().replace("_", " ");
        }
    }

    public enum TabLayout {
        BOX, LINE
    }

    public enum ChatboxMode {
        OPAQUE, TRANSPARENT
    }

    public enum SidePanelMode {
        TRANSPARENT, OPAQUE
    }

    public enum SocialTabMode {

        FRIENDS_LIST(InterfaceComposite.FRIENDS_LIST),
        IGNORE_LIST(InterfaceComposite.IGNORE_LIST);

        private final InterfaceComposite composite;

        SocialTabMode(InterfaceComposite composite) {
            this.composite = composite;
        }
    }

    public enum QuestTabMode {

        QUEST_LIST,
        ACHIEVEMENT_DIARIES,
        MINIGAME_LIST,
        KOUREND_FAVOUR;

        @Override
        public String toString() {
            String name = super.toString();
            return name.charAt(0) + name.substring(1).toLowerCase().replace("_", " ");
        }
    }

    public static class Controls {

        public static int getMouseButtons() {
            return Vars.get(MOUSE_BUTTONS_1) == 1 ? 1 : 2;
        }

        public static boolean isMouseCameraEnabled() {
            return Vars.get(Vars.Type.VARBIT, MOUSE_CAMERA_DISABLED) != 1;
        }
/*
        public static AttackOptionPriority getAttackOptionPriority() {
            return AttackOptionPriority.values()[Varps.get(VARP_ATTACK_OPTION_PRIORITY)];
        }

        public enum AttackOptionPriority {
            // In client-respected order, don't change...
            COMBAT_LEVEL, // Depends on combat levels.
            LEFT_CLICK, // Left-click where available.
            RIGHT_CLICK // Always right-click.
        }*/
    }

    public static class Chat {

        public static boolean isChatEffectsEnabled() {
            return Vars.get(CHAT_EFFECTS_DISABLED) != 1;
        }

        public static boolean isSplitPrivateChatEnabled() {
            return Vars.get(SPLIT_PRIVATE_CHAT_ENABLED) == 1;
        }

        public static boolean isProfanityFilterEnabled() {
            return Vars.get(PROFANITY_FILTER_DISABLED) != 1;
        }

        public static boolean isLoginLogoutNotificationTimeoutEnabled() {
            return Vars.get(Vars.Type.VARBIT, LLNTD) != 1;
        }
    }

    public static class Audio {

        public static int getVolume(Type type) {
            return Vars.get(type.var);
        }

        public static boolean setVolume(Type type, int level) {
            if (level < 0 || level > 4) {
                throw new IllegalArgumentException("Level must be between 0 and 4!");
            }

            ComponentQueryResults slider = Interfaces.query(InterfaceComposite.OPTIONS_TAB)
                    .actions(x -> x.contains(type.action))
                    .results();
            if (slider.size() == 0 || slider.size() < level) {
                return false;
            }

            InterfaceComponent notch = slider.get(level);
            return notch != null && notch.interact(x -> true);
        }

        public enum Type {

            MUSIC(MUSIC_VOLUME, "Adjust Music Volume"),
            SOUND_EFFECT(SOUND_EFFECT_VOLUME, "Adjust Sound Effect Volume"),
            AREA_SOUND(AREA_SOUND_VOLUME, "Adjust Area Sound Effect Volume");

            private final int var;
            private final String action;

            Type(int var, String action) {
                this.var = var;
                this.action = action;
            }
        }
    }

    public static class Display {

        public static boolean isRoofsHidden() {
            RSClientPreferences prefs = Game.getClient().getPreferences();
            return prefs != null && prefs.isRoofsHidden();
        }

        public static boolean isOrbsEnabled() {
            return Vars.get(Vars.Type.VARBIT, ORBS_DISABLED) != 1;
        }

        public static int getBrightness() {
            return Vars.get(BRIGHTNESS);
        }

        public static boolean isRemainingXpOn() {
            return Vars.get(Vars.Type.VARBIT, REMAINING_XP) == 1;
        }
    }
}