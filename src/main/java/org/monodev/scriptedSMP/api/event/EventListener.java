package org.monodev.scriptedSMP.api.event;

/**
 * @since 1.0
 * @version 1.0
 * @author MonoDev
 * @see Event
 * @see EventManager
 * @param <T> The event this listener is listening to
 */
@FunctionalInterface
public interface EventListener<T extends Event> {
    /**
     * Called when the event this listener is listening to gets published/fired.
     * @param event The event passed to this listener.
     * @since 1.0
     * @author MonoDev
     * @see Event
     * @see EventManager
     */
    void onEvent(T event);
}
