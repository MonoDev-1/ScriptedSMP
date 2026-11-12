package org.monodev.scriptedSMP.api.event;

import org.jspecify.annotations.NonNull;

/**
 * Interface for ScriptedSMP Event Manager.
 * Public-facing API
 *
 * <p>Provides methods for subscribing, unsubscribing, and publishing events to all listeners.</p>
 * <p>Event Listeners are associated with a specific event's class and receive events published for that class.</p>
 * @see EventListener
 * @author MonoDev
 * @apiNote It is possible to implement this interface if you need your own dedicated event system and you would be responsible for exposing the implementation somewhere in your code.
 * @since 1.0
 * @version 1.0
 */
public interface EventManager {
    /**
     *
     * @param eventType The Event class you would like to subscribe to.
     * @param listener Your listener.
     * @param <T> Supports all classes that implement Event
     * @since 1.0
     * @author MonoDev
     * @see Event
     * @see EventListener
     */
    <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener);
    /**
     *
     * @param eventType The Event class you would like to unsubscribe from.
     * @param listener The original listener.
     * @param <T> Supports all classes that implement Event
     * @since 1.0
     * @author MonoDev
     * @see Event
     * @see EventListener
     */
    <T extends Event> void unsubscribe(Class<T> eventType, EventListener<T> listener);

    /**
     * <p>Broadcasts the event instance to all listeners subscribed to this Event's class</p>
     * @param event The Event instance to publish.
     * @param <T> Supports all instances implementing Event
     * @since 1.0
     * @author MonoDev
     */
    <T extends Event> void publish(@NonNull T event);
}
