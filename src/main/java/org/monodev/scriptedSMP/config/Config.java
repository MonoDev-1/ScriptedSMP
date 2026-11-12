package org.monodev.scriptedSMP.config;

public class Config {
    
    private static <T> ConfigEntry<T> register(String key, T defaultValue, Class<T> type) {
        return ConfigRegistry.register("scriptedsmp", key, defaultValue, type);
    }

    public static final ConfigEntry<Boolean> PERMA_DEATH =
            register("permaDeath", false, Boolean.class);

    public static final ConfigEntry<Boolean> KICK_ON_DEATH =
            register("kickOnDeath", false, Boolean.class);

    public static final ConfigEntry<Boolean> BREAK_BLOCKS =
            register("breakBlocks", true, Boolean.class);

    public static final ConfigEntry<Boolean> PLACE_BLOCKS =
            register("placeBlocks", true, Boolean.class);

    public static final ConfigEntry<Boolean> INTERACT_BLOCKS =
            register("interactBlocks", true, Boolean.class);

    public static final ConfigEntry<Boolean> DEATH_SOUND =
            register("deathSound", false, Boolean.class);

    public static final ConfigEntry<Boolean> VOICE_ALLOWED =
            register("voiceAllowed", true, Boolean.class);

    public static final ConfigEntry<Boolean> CREATE_GROUPS =
            register("createGroups", true, Boolean.class);

    public static final ConfigEntry<Boolean> PRIVATE_GROUPS_ALLOWED =
            register("privateGroupsAllowed", true, Boolean.class);

    public static void toLoadClass() {
        // Exists purely to force the JVM to load the class.
        // Called in ScriptedSMP.class
    }
}