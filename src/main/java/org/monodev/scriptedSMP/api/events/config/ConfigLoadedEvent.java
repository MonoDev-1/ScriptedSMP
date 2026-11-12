package org.monodev.scriptedSMP.api.events.config;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseEvent;

public class ConfigLoadedEvent extends BaseEvent {
    public ConfigLoadedEvent(ScriptedAPI api) {
        super(api);
    }
}
