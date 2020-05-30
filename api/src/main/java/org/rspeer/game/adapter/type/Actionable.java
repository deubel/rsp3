package org.rspeer.game.adapter.type;

import java.util.List;
import java.util.function.Predicate;

public interface Actionable {

    String[] getRawActions();

    List<String> getActions();

    /**
     * @param predicate The predicate used to test the actions
     * @return {@code true} if any of the actions satisfy the predicate
     */
    default boolean containsAction(Predicate<String> predicate) {
        for (String action : getActions()) {
            if (predicate.test(action)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param action The action to check for
     * @return {@code true} if this Actionable contains the specified action
     */
    default boolean containsAction(String action) {
        return containsAction(p -> p.equalsIgnoreCase(action));
    }

    interface Query<Q extends Query<Q>> {
        Q actions(String... actions);
    }
}
