package org.rspeer.game.effect;

import org.rspeer.game.component.tab.Skill;
import org.rspeer.game.component.tab.Skills;

public class Health {

    /**
     * @return The current health of the local player
     */
    public static int getCurrent() {
        return Skills.getCurrentLevel(Skill.HITPOINTS);
    }

    /**
     * @return The maximum base health (or level) of the local player)
     */
    public static int getLevel() {
        return Skills.getLevel(Skill.HITPOINTS);
    }

    /**
     * @return The current health of the local player as a percentage
     */
    public static int getPercent() {
        try {
            return getCurrent() * 100 / getLevel();
        } catch (Exception e) {
            return -1;
        }
    }
}
