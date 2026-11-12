package org.monodev.scriptedSMP.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConfigRegistry {

    private static final Map<String, ConfigEntry<?>> ENTRIES = new ConcurrentHashMap<>();

    public static <T> ConfigEntry<T> register(
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
                defaultParser(type)
        );
    }

    public static <T> ConfigEntry<T> register(
            String namespace,
            String key,
            T defaultValue,
            Class<T> type,
            Function<Object, T> deserializer,
            Function<T, Object> serializer,
            Function<String, T> parser
    ) {
        String registryKey = namespace + ":" + key;

        if (namespace == null || namespace.isBlank()) {
            throw new IllegalArgumentException("Namespace cannot be empty");
        }

        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Key cannot be empty");
        }

        ConfigEntry<T> entry = new ConfigEntry<>(
                namespace, key, defaultValue, type, deserializer, serializer, parser
        );

        ConfigEntry<?> previous = ENTRIES.putIfAbsent(registryKey, entry);

        if (previous != null) {
            throw new IllegalArgumentException(
                    "Config entry already registered: " + registryKey
            );
        }

        return entry;
    }

    public static Map<String, ConfigEntry<?>> getEntries() {
        return Map.copyOf(ENTRIES);
    }

    public static void load(JavaPlugin plugin) {
        ConfigurationSection section =
                plugin.getConfig().getConfigurationSection("settings");

        if (section == null) {
            plugin.getLogger().config("Missing settings section. Creating an empty one.");
            section = plugin.getConfig().createSection("settings");
        }

        for (ConfigEntry<?> entry : ConfigRegistry.getEntries().values()) {
            loadEntry(section, entry);
        }
    }

    public static void save(JavaPlugin plugin) {
        ConfigurationSection section =
                plugin.getConfig().getConfigurationSection("settings");

        if (section == null) {
            section = plugin.getConfig().createSection("settings");
        }

        for (ConfigEntry<?> entry : ENTRIES.values()) {
            saveEntry(section, entry);
        }

        plugin.saveConfig();
    }

    private static <T> void loadEntry(
            ConfigurationSection section,
            ConfigEntry<T> entry
    ) {
        String registryKey = entry.namespace() + ":" + entry.key();

        if (!section.contains(registryKey)) {
            entry.value(entry.defaultValue());
            return;
        }

        Object raw = section.get(entry.namespace() + ":" + entry.key());

        if (raw == null) {
            entry.value(entry.defaultValue());
            return;
        }

        entry.value(entry.deserialize(raw));
    }

    private static <T> void saveEntry(
            ConfigurationSection section,
            ConfigEntry<T> entry
    ) {
        section.set(entry.namespace() + ":" + entry.key(), entry.serialize());
    }

    @SuppressWarnings("unchecked")
    public static <T> Function<String, T> defaultParser(Class<T> type) {
        if (type == String.class) {
            return input -> (T) input;
        }

        if (type == Boolean.class) {
            return input -> {
                if (!input.equalsIgnoreCase("true")
                        && !input.equalsIgnoreCase("false")) {
                    throw new IllegalArgumentException("Expected true or false");
                }
                return (T) Boolean.valueOf(input);
            };
        }

        if (type == Integer.class) {
            return input -> (T) Integer.valueOf(input);
        }

        if (type == Double.class) {
            return input -> (T) Double.valueOf(input);
        }

        if (type == Long.class) {
            return input -> (T) Long.valueOf(input);
        }

        throw new IllegalArgumentException(
                "No default parser for type: " + type.getName()
        );
    }
}