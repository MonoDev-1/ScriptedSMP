package org.monodev.scriptedSMP.api.events.kits;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseEvent;
import org.monodev.scriptedSMP.kits.KitData;

public class KitEvent extends BaseEvent {
    private final KitData data;

    public KitEvent(ScriptedAPI api, KitData data) {
        super(api);
        this.data = data;
    }

    public KitData getData() {
        return data;
    }
}
