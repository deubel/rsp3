package org.rspeer.game.adapter.type;

import org.rspeer.game.Game;
import org.rspeer.game.action.Action;

import java.util.function.Predicate;

public interface Interactable extends Actionable {

    /**
     * @param action The action to interact with
     * @return {@code true} if the interaction was successful
     */
    default boolean interact(String action) {
        Action resolved = actionOf(action);
        if (resolved != null) {
            Game.queueAction(resolved);
            return true;
        }
        return false;
    }

    default boolean interact(Predicate<String> predicate) {
        for (String action : getActions()) {
            if (action != null && predicate.test(action)) {
                return interact(action);
            }
        }
        return predicate.test("") && interact("");
    }

    Action actionOf(String action);

    default Action actionOf(Predicate<String> predicate) {
        for (String action : getActions()) {
            if (action != null && predicate.test(action)) {
                return actionOf(action);
            }
        }
        return actionOf("");
    }
}
