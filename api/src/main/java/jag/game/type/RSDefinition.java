package jag.game.type;

import jag.RSDoublyLinkedNode;

import java.util.function.Predicate;

public interface RSDefinition extends RSDoublyLinkedNode {

    int getId();

    String getName();

    String[] getActions();

    default boolean containsAction(Predicate<String> action) {
        String[] actions = getActions();
        if (actions != null && action != null) {
            for (String action0 : actions) {
                if (action0 != null && action.test(action0)) {
                    return true;
                }
            }
        }
        return false;
    }
}
