package org.rspeer.game.adapter.scene.definition;

import org.rspeer.game.adapter.Adapter;
import org.rspeer.game.adapter.type.Actionable;
import org.rspeer.game.adapter.type.Identifiable;
import org.rspeer.game.adapter.type.Nameable;
import org.rspeer.commons.StringCommons;
import jag.game.type.RSDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Definition<P extends RSDefinition> extends Adapter<P> implements Identifiable, Nameable, Actionable {

    protected Definition(P provider) {
        super(provider);
    }

    @Override
    public int getId() {
        P provider = getProvider();
        return provider != null ? provider.getId() : -1;
    }

    @Override
    public String getName() {
        P provider = getProvider();
        if (provider == null) {
            return "";
        }

        String name = provider.getName();
        return name != null ? StringCommons.replaceJagspace(name) : "";
    }

    @Override
    public String[] getRawActions() {
        P provider = getProvider();
        if (provider == null) {
            return new String[0];
        }

        String[] actions = provider.getActions();
        return actions != null ? actions : new String[0];
    }

    @Override
    public List<String> getActions() {
        P provider = getProvider();
        if (provider == null) {
            return Collections.emptyList();
        }

        List<String> actions = new ArrayList<>();
        for (String action : getRawActions()) {
            if (action != null) {
                actions.add(StringCommons.replaceJagspace(action));
            }
        }
        return actions;
    }
}
