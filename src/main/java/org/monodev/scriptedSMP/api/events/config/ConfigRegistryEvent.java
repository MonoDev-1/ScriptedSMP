package org.monodev.scriptedSMP.api.events.config;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.config.APIConfigRegistryI;
import org.monodev.scriptedSMP.api.events.BaseEvent;

public class ConfigRegistryEvent extends BaseEvent {
    private final APIConfigRegistryI registry;

    public ConfigRegistryEvent(ScriptedAPI api, APIConfigRegistryI registry) {
        super(api);
        this.registry = registry;
    }

    public APIConfigRegistryI getRegistry() {
        return registry;
    }
}
