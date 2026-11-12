package org.monodev.scriptedSMP.api.events.config;

import org.monodev.scriptedSMP.api.ScriptedAPI;
import org.monodev.scriptedSMP.api.events.BaseCancellableEvent;
import org.monodev.scriptedSMP.config.ConfigEntry;

public class ConfigEditEvent<T> extends BaseCancellableEvent {
    private final ConfigEntry<T> entry;
    private final T oldValue;

    public ConfigEditEvent(ScriptedAPI api, ConfigEntry<T> entry, T oldValue) {
        super(api);
        this.entry = entry;
        this.oldValue = oldValue;
    }

    public ConfigEntry<T> getEntry() {
        return entry;
    }

    public T getOldValue() {
        return oldValue;
    }
}