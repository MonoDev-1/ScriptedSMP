package org.monodev.scriptedSMP.api.events.kits;

import org.bukkit.entity.Player;
import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.kits.KitData;

public class KitDeleteEvent extends KitCancellableEvent {
    private final Player deleter;
    public KitDeleteEvent(ScriptedAPI api, KitData data, Player deleter) {
        super(api, data);
        this.deleter = deleter;
    }

    public Player getDeleter() {
        return deleter;
    }
}
