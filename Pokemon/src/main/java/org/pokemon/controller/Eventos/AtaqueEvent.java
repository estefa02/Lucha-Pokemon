package org.pokemon.controller.Eventos;

import javafx.event.Event;
import javafx.event.EventType;
import org.pokemon.model.acciones.Accion;

public class AtaqueEvent extends Event {
    public static final EventType<AtaqueEvent> ATACAR = new EventType<>(Event.ANY, "ATACAR");

    public AtaqueEvent() {
        super(ATACAR);
    }
}
