package org.monodev.scriptedSMP.api.events;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.event.EventCancellable;

public class BaseCancellableEvent extends BaseEvent implements EventCancellable {
    private boolean cancelled = false;

    public BaseCancellableEvent(ScriptedAPI api) {
        super(api);
    }

    @Override
    public void setCancelled(boolean value) {
        cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
