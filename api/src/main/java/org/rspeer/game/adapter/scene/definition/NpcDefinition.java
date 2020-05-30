package org.rspeer.game.adapter.scene.definition;

import org.rspeer.game.Definitions;
import jag.game.type.RSNpcDefinition;

public class NpcDefinition extends TransformableDefinition<RSNpcDefinition, NpcDefinition> {

    public NpcDefinition(RSNpcDefinition provider) {
        super(provider, Definitions::getNpc);
    }

    public int getPrayerIcon() {
        RSNpcDefinition provider = getProvider();
        return provider != null ? provider.getPrayerIcon() : -1;
    }

    public boolean isRenderedOnMinimap() {
        RSNpcDefinition provider = getProvider();
        return provider != null && provider.isRenderedOnMinimap();
    }

    public int getTileSize() {
        RSNpcDefinition provider = getProvider();
        return provider != null ? provider.getSize() : -1;
    }

    public int getCombatLevel() {
        RSNpcDefinition provider = getProvider();
        return provider != null ? provider.getCombatLevel() : -1;
    }
}
