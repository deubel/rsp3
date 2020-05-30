package org.rspeer.game.component.tab;

import org.rspeer.commons.Time;
import org.rspeer.commons.predicate.Predicates;
import org.rspeer.game.Game;
import org.rspeer.game.Vars;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.type.Interactable;
import org.rspeer.game.component.InterfaceAddress;
import org.rspeer.game.component.InterfaceComposite;
import org.rspeer.game.component.Interfaces;

public class Magic {

    public static boolean isSpellSelected() {
        return Game.getClient().isSpellSelected();
    }

    public static int getSpellTargetFlags() {
        return Game.getClient().getSpellTargetFlags();
    }

    public static boolean canCast(Spell spell) {
        if (getBook() != spell.getBook()) {
            return false;
        }
        InterfaceComponent component = Interfaces.lookup(spell.getAddress());
        return component != null && component.getMaterialId() != spell.getDisabledMaterialId();
    }

    public static boolean interact(Spell spell, String action) {
        InterfaceComponent component = Interfaces.lookup(spell.getAddress());
        if (component == null) {
            return false;
        }

        if (component.getBounds().getX() == 0) {
            Tabs.open(Tab.MAGIC);
            return false;
        }

        return component.interact(action);
    }

    public static boolean cast(Spell spell) {
        return interact(spell, "Cast");
    }

    public static Book getBook() {
        Book[] books = Book.values();
        int index = Vars.get(Vars.Type.VARBIT, 4070);
        if (index < 0 || index > 3) {
            throw new IllegalStateException("Unknown spellbook (" + index + ")! Please report this to a developer");
        }
        return books[index];
    }

    public static boolean cast(Spell spell, Interactable interactable) {
        if (!isSpellSelected()) {
            Magic.cast(spell);
            return false;
        }
        return interactable.interact("Cast");
    }


    public enum Book {
        MODERN,
        ANCIENT,
        LUNAR,
        NECROMANCY
    }

    public static final class Autocast {

        private static final int AUTOCAST_VARP = 108;
        private static final int USABILITY_VARP = 843;
        private static final int AUTOCAST_MODE_VARP = 439;

        private static final InterfaceAddress AUTO_CAST_MENU_BASE = new InterfaceAddress(
                () -> Interfaces.getDirect(InterfaceComposite.AUTOCAST, 1)
        );

        public static boolean isUsable() {
            return Vars.get(USABILITY_VARP) == 18;
        }

        public static boolean isEnabled() {
            return Vars.get(AUTOCAST_VARP) != 0;
        }

        public static Mode getMode() {
            return Vars.get(AUTOCAST_MODE_VARP) >>> 8 == 1 ? Mode.DEFENSIVE : Mode.OFFENSIVE;
        }

        public static Spell getSelectedSpell() {
            for (Spell spell : Spell.Modern.values()) {
                if (spell.isAutoCasted()) {
                    return spell;
                }
            }

            /*for (Spell spell : Spell.Ancient.values()) {
                if (spell.isAutoCasted()) {
                    return spell;
                }
            }*/
            return null;
        }

        public static boolean isSpellSelected(Spell spell) {
            return spell.isAutoCasted();
        }

        public static boolean select(Mode mode, Spell spell) {
            if (Skills.getCurrentLevel(Skill.MAGIC) < spell.getLevelRequired()) {
                return false;
            }
            return openAutoCastSettings(mode) && selectSpell(spell);
        }

        public static boolean isSelectionOpen() {
            return Interfaces.lookup(AUTO_CAST_MENU_BASE) != null;
        }

        private static boolean openAutoCastSettings(Mode mode) {
            if (isSelectionOpen()) {
                return true;
            }

            if (!openCombatTab()) {
                return false;
            }

            InterfaceComponent book = Interfaces.query(InterfaceComposite.COMBAT_TAB)
                    .actions(x -> x.contains("Choose spell"))
                    .results().get(mode.ordinal());
            return book != null
                    && book.interact("Choose spell")
                    && Time.sleepUntil(() -> Interfaces.lookup(AUTO_CAST_MENU_BASE) != null, 1200);
        }

        private static boolean selectSpell(Spell spell) {
            InterfaceComponent pane = Interfaces.getDirect(InterfaceComposite.AUTOCAST, 1);
            if (pane == null) {
                return false;
            }

            InterfaceComponent spellComponent = pane.querySubComponents()
                    .actions(y -> y.equalsIgnoreCase(spell.getName()))
                    .results().first();
            return spellComponent != null
                    && spellComponent.interact(Predicates.always())
                    && Time.sleepUntil(spell::isAutoCasted, 1200);
        }

        private static boolean openCombatTab() {
            return Tabs.open(Tab.COMBAT) && Time.sleepUntil(() -> Tabs.isOpen(Tab.COMBAT), 1200);
        }

        public enum Mode {
            DEFENSIVE, OFFENSIVE;
        }
    }
}
