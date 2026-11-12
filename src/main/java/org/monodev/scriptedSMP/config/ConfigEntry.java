package org.monodev.scriptedSMP.config;

import org.monodev.scriptedSMP.ScriptedSMP;
import org.monodev.scriptedSMP.api.ScriptedSMPApi;
import org.monodev.scriptedSMP.api.events.config.ConfigEditEvent;

import java.util.function.Function;

public class ConfigEntry<T> {

    private final String namespace;
    private final String key;
    private final T defaultValue;
    private final Class<T> type;

    private final Function<Object, T> deserializer;
    private final Function<T, Object> serializer;
    private final Function<String, T> parser;

    private T value;

    public ConfigEntry(
            String namespace,
            String key,
            T defaultValue,
            Class<T> type,
            Function<Object, T> deserializer,
            Function<T, Object> serializer,
            Function<String, T> parser
    ) {
        this.namespace = namespace;
        this.key = key;
        this.defaultValue = defaultValue;
        this.type = type;
        this.deserializer = deserializer;
        this.serializer = serializer;
        this.value = defaultValue;
        this.parser = parser;
    }
    public String namespace() { return namespace; }
    public String key() { return key; }
    public T defaultValue() { return defaultValue; }
    public Class<T> type() { return type; }
    public T value() { return value; }

    public void value(T value) {
        T oldValue = this.value;
        ScriptedSMPApi api = ScriptedSMP.instance.getApi();
        ConfigEditEvent<T> configEditEvent = new ConfigEditEvent<T>(api, this, oldValue);
        api.events().publish(configEditEvent);
        if (configEditEvent.isCancelled()) return;
        this.value = value;
    }

    public T deserialize(Object object) {
        return deserializer.apply(object);
    }

    public Object serialize() {
        return serializer.apply(value);
    }

    public T parse(String input) {
        return parser.apply(input);
    }
}