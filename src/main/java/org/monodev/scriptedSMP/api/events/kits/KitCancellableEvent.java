package org.monodev.scriptedSMP.api.events.kits;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseCancellableEvent;
import org.monodev.scriptedSMP.kits.KitData;

public class KitCancellableEvent extends BaseCancellableEvent {
    private final KitData data;

    public KitCancellableEvent(ScriptedAPI api, KitData data) {
        super(api);
        this.data = data;
    }

    public KitData getData() {
        return data;
    }
}
