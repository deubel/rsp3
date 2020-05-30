package org.rspeer.game.adapter.scene.definition;

import jag.game.type.RSItemDefinition;

public class ItemDefinition extends Definition<RSItemDefinition> {

    public ItemDefinition(RSItemDefinition provider) {
        super(provider);
    }

    public boolean isStackable() {
        RSItemDefinition provider = getProvider();
        return provider != null && provider.getStackable() > 0;
    }

    public boolean isNoted() {
        RSItemDefinition provider = getProvider();
        return provider != null && provider.getNoteTemplateId() != -1;
    }

    public int getNotedId() {
        RSItemDefinition provider = getProvider();
        if (provider == null) {
            return -1;
        }
        return provider.getNoteTemplateId() != -1 ? provider.getId() : provider.getNoteId();
    }

    public int getUnnotedId() {
        RSItemDefinition provider = getProvider();
        if (provider == null) {
            return -1;
        }
        return provider.getNoteTemplateId() != -1 ? provider.getNoteId() : provider.getId();
    }

    public boolean isStockMarketable() {
        RSItemDefinition provider = getProvider();
        return provider != null && provider.isStockMarketable();
    }
}
