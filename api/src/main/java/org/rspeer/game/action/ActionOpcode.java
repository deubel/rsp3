package org.rspeer.game.action;

public enum ActionOpcode {

    ITEM_ON_OBJECT(1, ActionTargetType.OBJECT),
    SPELL_ON_OBJECT(2, ActionTargetType.OBJECT),

    OBJECT_ACTION_0(3, ActionTargetType.OBJECT),
    OBJECT_ACTION_1(4, ActionTargetType.OBJECT),
    OBJECT_ACTION_2(5, ActionTargetType.OBJECT),
    OBJECT_ACTION_3(6, ActionTargetType.OBJECT),
    OBJECT_ACTION_4(1001, ActionTargetType.OBJECT),

    EXAMINE_OBJECT(1002, ActionTargetType.OBJECT),

    ITEM_ON_NPC(7, ActionTargetType.NPC),
    SPELL_ON_NPC(8, ActionTargetType.NPC),

    NPC_ACTION_0(9, ActionTargetType.NPC),
    NPC_ACTION_1(10, ActionTargetType.NPC),
    NPC_ACTION_2(11, ActionTargetType.NPC),
    NPC_ACTION_3(12, ActionTargetType.NPC),
    NPC_ACTION_4(13, ActionTargetType.NPC),

    EXAMINE_NPC(1003, ActionTargetType.NPC),

    ITEM_ON_PLAYER(14, ActionTargetType.PLAYER),
    SPELL_ON_PLAYER(15, ActionTargetType.PLAYER),

    PLAYER_ACTION_0(44, ActionTargetType.PLAYER),
    PLAYER_ACTION_1(45, ActionTargetType.PLAYER),
    PLAYER_ACTION_2(46, ActionTargetType.PLAYER),
    PLAYER_ACTION_3(47, ActionTargetType.PLAYER),
    PLAYER_ACTION_4(48, ActionTargetType.PLAYER),
    PLAYER_ACTION_5(49, ActionTargetType.PLAYER),
    PLAYER_ACTION_6(50, ActionTargetType.PLAYER),
    PLAYER_ACTION_7(51, ActionTargetType.PLAYER),

    ITEM_ON_PICKABLE(16, ActionTargetType.PICKABLE),
    SPELL_ON_PICKABLE(17, ActionTargetType.PICKABLE),

    PICKABLE_ACTION_0(18, ActionTargetType.PICKABLE),
    PICKABLE_ACTION_1(19, ActionTargetType.PICKABLE),
    PICKABLE_ACTION_2(20, ActionTargetType.PICKABLE),
    PICKABLE_ACTION_3(21, ActionTargetType.PICKABLE),
    PICKABLE_ACTION_4(22, ActionTargetType.PICKABLE),

    EXAMINE_PICKABLE(1004, ActionTargetType.PICKABLE),

    WALK_HERE(23, ActionTargetType.POSITION),
    WALK(1337, ActionTargetType.POSITION),

    BUTTON_INPUT(24, ActionTargetType.BUTTON),
    BUTTON_SELECTABLE_SPELL(25, ActionTargetType.BUTTON),
    BUTTON_CLOSE(26, ActionTargetType.BUTTON),
    BUTTON_VAR_FLIP(28, ActionTargetType.BUTTON),
    BUTTON_VAR_SET(29, ActionTargetType.BUTTON),
    BUTTON_DIALOG(30, ActionTargetType.BUTTON),

    ITEM_ON_ITEM(31, ActionTargetType.ITEM),
    SPELL_ON_ITEM(32, ActionTargetType.ITEM),

    ITEM_ACTION_0(33, ActionTargetType.ITEM),
    ITEM_ACTION_1(34, ActionTargetType.ITEM),
    ITEM_ACTION_2(35, ActionTargetType.ITEM),
    ITEM_ACTION_3(36, ActionTargetType.ITEM),
    ITEM_ACTION_4(37, ActionTargetType.ITEM),

    USE_ITEM(38, ActionTargetType.ITEM),
    EXAMINE_ITEM(1005, ActionTargetType.ITEM),

    TABLE_ACTION_0(39, ActionTargetType.ITEM),
    TABLE_ACTION_1(40, ActionTargetType.ITEM),
    TABLE_ACTION_2(41, ActionTargetType.ITEM),
    TABLE_ACTION_3(42, ActionTargetType.ITEM),
    TABLE_ACTION_4(43, ActionTargetType.ITEM),

    SPELL_ON_COMPONENT(58, ActionTargetType.COMPONENT),

    COMPONENT_ACTION(57, ActionTargetType.COMPONENT),
    COMPONENT_ACTION_2(1007, ActionTargetType.COMPONENT),

    CANCEL(1006, ActionTargetType.UNDEFINED);

    private final int id;
    private final ActionTargetType targetType;

    ActionOpcode(int id, ActionTargetType targetType) {
        this.id = id;
        this.targetType = targetType;
    }

    public static ActionOpcode valueOf(int id) {
        for (ActionOpcode opcode : ActionOpcode.values()) {
            if (opcode.id == id) {
                return opcode;
            }
        }
        throw new IllegalStateException("Unknown opcode - " + id + " - please report to Asta");
    }

    public int getId() {
        return id;
    }

    public ActionTargetType getTargetType() {
        return targetType;
    }
}
