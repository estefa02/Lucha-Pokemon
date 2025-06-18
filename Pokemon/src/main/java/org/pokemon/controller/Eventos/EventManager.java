package org.pokemon.controller.Eventos;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private static final EventManager INSTANCE = new EventManager();
    private final Map<EventType<?>, List<EventHandler<?>>> eventHandlers = new HashMap<>();

    private EventManager() {
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    public <T extends Event> void addListener(EventType<T> eventType, EventHandler<T> eventHandler) {
        eventHandlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(eventHandler);
    }

    public <T extends Event> void removeListener(EventType<T> eventType, EventHandler<T> eventHandler) {
        List<EventHandler<?>> handlers = eventHandlers.get(eventType);
        if (handlers != null) {
            handlers.remove(eventHandler);
            if (handlers.isEmpty()) {
                eventHandlers.remove(eventType);
            }
        }
    }

    public <T extends Event> void fireEvent(T event) {
        List<EventHandler<?>> handlers = eventHandlers.get(event.getEventType());
        if (handlers != null) {
            handlers.forEach(handler -> ((EventHandler<T>) handler).handle(event));
        }
    }
}
