package org.monodev.scriptedSMP.api.config;

import org.monodev.scriptedSMP.config.ConfigEntry;

import java.util.Map;
import java.util.function.Function;

/**
 * The interface the API's config registry implements.
 * @apiNote The implementation for this interface uses the internal class <b>ConfigRegistry</b> to register <b>ConfigEntry</b>.
 * @see org.monodev.scriptedSMP.config.ConfigRegistry
 * @see ConfigEntry
 * @version 1.0
 * @since 1.0
 * @author MonoDev
 */
public sealed interface APIConfigRegistryI permits APIConfigRegistry {

    /**
     * Registers a configuration entry with the specified namespace, key,
     * default value, and type.
     *
     * @param <T> The type of value stored in this configuration entry.
     * @param namespace The namespace used to identify the configuration entry.
     * @param key The unique key identifying the configuration entry within its namespace.
     * @param defaultValue The default value assigned to the configuration entry.
     * @param type The class representing the configuration entry's value type.
     * @return The registered {@link ConfigEntry}.
     * @since 1.0
     * @apiNote This overload uses the default serializer, deserializer,
     *          and parser for supported types. Custom object types require
     *          explicitly providing these functions through the other
     *          {@code register()} overload.
     * @author MonoDev
     * @throws IllegalArgumentException If the namespace or key is blank,
     *      or an entry with the same registry key is already registered
     */
    <T> ConfigEntry<T> register(
            String namespace,
            String key,
            T defaultValue,
            Class<T> type
    );

    /**
     * Registers a configuration entry with custom serialization, deserialization,
     * and parsing functions.
     *
     * <p>These functions define how the configuration value is converted
     * between its Java representation and its serialized representation.</p>
     *
     * @param <T> The type of value stored in this configuration entry.
     * @param namespace The namespace used to identify the configuration entry.
     * @param key The unique key identifying the configuration entry within its namespace.
     * @param defaultValue The default value assigned to the configuration entry.
     * @param type The class representing the configuration entry's value type.
     * @param deserializer Converts a serialized object into an instance of {@code T}.
     * @param serializer Converts an instance of {@code T} into a serialized object.
     * @param parser Parses a string into an instance of {@code T}.
     * @return The registered {@link ConfigEntry}.
     * @since 1.0
     * @author MonoDev
     * @throws IllegalArgumentException If the namespace or key is blank,
     *      or an entry with the same registry key is already registered
     */
    <T> ConfigEntry<T> register(
            String namespace,
            String key,
            T defaultValue,
            Class<T> type,
            Function<Object, T> deserializer,
            Function<T, Object> serializer,
            Function<String, T> parser
    );

    /**
     * Retrieves all registered configuration entries.
     * <p>Entries are indexed by their registry keys.</p>
     * <p>A registry key is the combination of the {@link ConfigEntry}'s namespace and key with a colon to separate the two.</p>
     * @return A immutable map containing all registered configuration entries,
     *         mapped by their respective keys.
     * @since 1.0
     * @author MonoDev
     */
    Map<String, ConfigEntry<?>> getEntries();
}