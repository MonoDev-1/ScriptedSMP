package org.monodev.scriptedSMP.api.events.kits;

import org.bukkit.entity.Player;
import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.kits.KitData;

public class KitGivenEvent extends KitEvent {
    private final Player player;
    public KitGivenEvent(ScriptedAPI api, KitData data, Player player) {
        super(api, data);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
