package org.monodev.scriptedSMP.api;

import org.jspecify.annotations.NonNull;
import org.monodev.scriptedSMP.api.event.Event;
import org.monodev.scriptedSMP.api.event.EventListener;
import org.monodev.scriptedSMP.api.event.EventManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScriptedEventManager implements EventManager {
    private static final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new ConcurrentHashMap<>();

    @Override
    public <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener) {
        listeners.computeIfAbsent(eventType, ignored -> new CopyOnWriteArrayList<>()).add(listener);
    }

    @Override
    public <T extends Event> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
        List<EventListener<? extends Event>> registered = listeners.get(eventType);

        if (registered != null) {
            registered.remove(listener);
        }
    }

    @Override
    public <T extends Event> void publish(@NonNull T event) {
        for (EventListener<? extends Event> listener : listeners.get(event.getClass())) {
            dispatch(event, listener);
        }
    }

    private <T extends Event> void dispatch(T event, EventListener<? extends Event> eventListener) {
        @SuppressWarnings("unchecked")
        EventListener<T> typed = (EventListener<T>) eventListener;

        typed.onEvent(event);
    }
}
