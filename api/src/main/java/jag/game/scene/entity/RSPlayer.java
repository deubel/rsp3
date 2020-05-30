package jag.game.scene.entity;

import org.rspeer.commons.StringCommons;
import jag.game.RSPlayerAppearance;
import jag.game.relationship.RSNamePair;

public interface RSPlayer extends RSPathingEntity {

    int getPrayerIcon();

    int getSkullIcon();

    int getSkillLevel();

    RSPlayerAppearance getAppearance();

    boolean isSoftHidden();

    RSNamePair getNamePair();

    RSModel getTransformedNpcModel();

    int getTeam();

    int getCombatLevel();

    String[] getActions();

    default String getName() {
        RSNamePair pair = getNamePair();
        if (pair == null) {
            return "";
        }

        String name = pair.getRaw();
        return name != null ? StringCommons.replaceJagspace(name) : "";
    }
}