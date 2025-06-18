package org.pokemon.controller.Eventos;

import javafx.event.Event;
import javafx.event.EventType;
import org.pokemon.model.Jugador;
import org.pokemon.model.acciones.Accion;



public class ElegirPokemonEvent extends Event {
    public static final EventType<ElegirPokemonEvent> POKEMON_SELECTED = new EventType<>(Event.ANY, "POKEMON_SELECTED");
    public static final EventType<ElegirPokemonEvent> CONFRIMAR_ELECCION = new EventType<>(Event.ANY, "CONFIRMAR_ELECCION");
    private final int pokemonId;
    private Jugador jugador;
    private Boolean debilitado;
    private Accion mochila;

    public ElegirPokemonEvent(EventType<ElegirPokemonEvent> eventType, int pokemonId) {
        super(eventType);
        this.pokemonId = pokemonId;
    }

    public Accion getMochila() {
        return mochila;
    }

    public void setMochila(Accion mochila) {
        this.mochila = mochila;
    }

    public boolean getDebilitado() {
        return debilitado;
    }

    public void setDebilitado(boolean debilitado) {
        this.debilitado = debilitado;
    }

    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }

    public Jugador getJugador(){
        return this.jugador;
    }


    public int getPokemonId() {
        return pokemonId;
    }
}
