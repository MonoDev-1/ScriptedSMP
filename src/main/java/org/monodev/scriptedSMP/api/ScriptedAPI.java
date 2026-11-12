package org.monodev.scriptedSMP.api;

import org.jetbrains.annotations.Nullable;
import org.monodev.scriptedSMP.api.event.EventManager;
import org.monodev.scriptedSMP.bots.FakePlayerManager;
import org.monodev.scriptedSMP.voicechat.VoiceChatManager;

/**
 * The public-facing api for ScriptedSMP plugin
 * <p>Provides access to ScriptedSMP functionality like configuration and its event system.</p>
 *
 * <p>Plugins should use this rather than interacting with ScriptedSMPApi which is the implementation for this interface</p>
 * @author MonoDev
 * @since 1.0
 * @version 1.0
 */
public interface ScriptedAPI {
    /**
     * @author MonoDev
     * @return The global fake player manager
     * @since 1.0
     */
    FakePlayerManager getFakePlayerManager();

    /**
     *
     * @return ScriptedSMP's event system. You can publish events and listen to events here.
     * @since 1.0
     * @author MonoDev
     */
    EventManager events();

    /**
     * @author MonoDev
     * @return <p>ScriptedSMP's voice chat manager instance. Useful if you want some control over Simple Voice Chat but don't want to make your own Simple Voice Chat plugin.</p>
     * @since 1.0
     */
    @Nullable
    VoiceChatManager getVoiceChatManager();

    /**
     * <p>When you call this function, your plugin gets registered and the registerEvents function is called on your plugin.</p>
     * @param plugin The ScriptedPlugin to register
     * @since 1.0
     * @author MonoDev
     */
    void registerPlugin(ScriptedPlugin plugin);
}
