package org.rspeer.game.component.tab;

import org.rspeer.commons.predicate.Predicates;
import org.rspeer.game.adapter.component.InterfaceComponent;

public class Tabs {

    public static Tab getOpen() {
        for (Tab tab : Tab.values()) {
            if (isOpen(tab)) {
                return tab;
            }
        }
        return null;
    }

    public static boolean open(Tab tab) {
        if (Tabs.getOpen() == tab) {
            return true;
        }
        InterfaceComponent component = tab.getComponent();
        return component != null && component.interact(Predicates.always());
    }

    public static boolean isOpen(Tab tab) {
        InterfaceComponent component = tab.getComponent();
        return component != null && component.getMaterialId() != -1;
    }
}
