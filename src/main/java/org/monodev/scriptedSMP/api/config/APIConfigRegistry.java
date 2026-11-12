package org.monodev.scriptedSMP.api.config;

import org.monodev.scriptedSMP.config.ConfigEntry;
import org.monodev.scriptedSMP.config.ConfigRegistry;

import java.util.Map;
import java.util.function.Function;

public final class APIConfigRegistry implements APIConfigRegistryI {
    public <T> ConfigEntry<T> register(
            String namespace,
            String key,
            T defaultValue,
            Class<T> type
    ) {
        return register(
                namespace,
                key,
                defaultValue,
                type,
                type::cast,
                value -> value,
                ConfigRegistry.defaultParser(type)
        );
    }

    public <T> ConfigEntry<T> register(
            String namespace,
            String key,
            T defaultValue,
            Class<T> type,
            Function<Object, T> deserializer,
            Function<T, Object> serializer,
            Function<String, T> parser
    ) {
        if (namespace.equals("scriptedsmp")) {
            throw new IllegalArgumentException(
                    "Namespace of config entry cannot be the same namespace that ScriptedSMP uses"
            );
        }

        return ConfigRegistry.register(
                namespace,
                key,
                defaultValue,
                type,
                deserializer,
                serializer,
                parser
        );
    }

    public Map<String, ConfigEntry<?>> getEntries() {
        return ConfigRegistry.getEntries();
    }
}
