package org.pokemon.controller.Eventos;

import javafx.event.Event;
import javafx.event.EventType;
import org.pokemon.controller.ConstantesController;

import java.util.HashMap;
import java.util.Map;

public class CambioDeEscenaEvent extends Event{
    public static final EventType<CambioDeEscenaEvent> CAMBIO_DE_ESCENA = new EventType<>(Event.ANY, "CAMBIO_DE_ESCENA");
    public static final EventType<CambioDeEscenaEvent> AVANZAR_TURNO = new EventType<>(Event.ANY, "AVANZAR_TURNO");
    public static final EventType<CambioDeEscenaEvent> ITEM_ELEGIDO = new EventType<>(Event.ANY, "ITEM_ELEGIDO");
    private final ConstantesController.Escenas titulo;
    private Map<String, Object> datos;


    public CambioDeEscenaEvent(EventType<CambioDeEscenaEvent> eventType, ConstantesController.Escenas titulo) {
        super(eventType);
        this.titulo = titulo;
        this.datos = new HashMap<>();
    }

    public void setData(String data, Object objeto){
        datos.put(data, objeto);
    }

    public Object getData(String data){
        return datos.get(data);
    }
    public ConstantesController.Escenas getTitulo() {
        return titulo;
    }
}
