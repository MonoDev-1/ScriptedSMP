package org.monodev.scriptedSMP.api.events.kits;

import org.bukkit.entity.Player;
import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.kits.KitData;

public class KitCreateEvent extends KitCancellableEvent {
    private final Player creator;

    public KitCreateEvent(ScriptedAPI api, KitData data, Player creator) {
        super(api, data);
        this.creator = creator;
    }

    public Player getCreator() {
        return creator;
    }
}
