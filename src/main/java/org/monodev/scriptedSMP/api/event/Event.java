package org.monodev.scriptedSMP.api.event;

import org.monodev.scriptedSMP.api.ScriptedAPI;

/**
 * The base interface that all Events MUST implement.
 * <p>Used in EventManager and EventListener</p>
 * @version 1.0
 * @since 1.0
 * @author MonoDev
 */
public interface Event {
    /**
     * @see ScriptedAPI
     * @return The global ScriptedAPI instance.
     * @since 1.0
     * @author MonoDev
     */
    ScriptedAPI getAPI();
}
