package org.monodev.scriptedSMP.api.events;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.event.Event;

public class BaseEvent implements Event {
    private final ScriptedAPI api;

    public BaseEvent(ScriptedAPI api) {
        this.api = api;
    }

    @Override
    public ScriptedAPI getAPI() {
        return api;
    }
}
