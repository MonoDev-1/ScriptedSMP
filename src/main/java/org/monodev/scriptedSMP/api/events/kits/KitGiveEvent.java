package org.monodev.scriptedSMP.api.events.kits;

import org.bukkit.entity.Player;
import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.kits.KitData;

public class KitGiveEvent extends KitCancellableEvent {
    private final Player player;
    public KitGiveEvent(ScriptedAPI api, KitData data, Player player) {
        super(api, data);

        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
