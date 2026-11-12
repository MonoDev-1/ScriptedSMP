package org.monodev.scriptedSMP.api;

import org.monodev.scriptedSMP.api.event.EventManager;

/**
 * <p>Interface for plugins integrating with ScriptedSMP</p>
 * <p>Implementations use this interface to register their plugin with ScriptedAPI.</p>
 * @author MonoDev
 * @since 1.0
 * @version 1.0
 * @see ScriptedAPI
 */
public interface ScriptedPlugin {
    /**
     * Called immediately when the plugin is registered.
     * @param eventManager The ScriptedAPI's event manager instance used to subscribe, unsubscribe, and publish events.
     * @since 1.0
     * @author MonoDev
     * @see ScriptedAPI
     * @see EventManager
     */
    void registerEvents(EventManager eventManager);
}