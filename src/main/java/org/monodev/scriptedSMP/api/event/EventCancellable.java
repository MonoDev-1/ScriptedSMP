package org.monodev.scriptedSMP.api.event;

/**
 * An extension to Event.
 * Adds functionality to Events which can be canceled.
 * @see org.monodev.scriptedSMP.api.event.Event
 * @version 1.0
 * @since 1.0
 * @apiNote Cancelling an event does not stop its broadcasting (prevent
 *          registered listeners from receiving it). Cancellation is only
 *          represented by the cancelled flag. It is the responsibility
 *          of the event issuer to check this flag and handle the event
 *          accordingly, such as by preventing the associated action.
 */
public interface EventCancellable extends Event {
    /**
     * Sets the event to be canceled or not.
     * @param value Cancellation flag
     * @since 1.0
     * @author MonoDev
     * @apiNote Does not stop this event from broadcasting
     */
    void setCancelled(boolean value);

    /**
     * @since 1.0
     * @return Whether this event is marked as canceled.
     * @author MonoDev
     */
    boolean isCancelled();
}
