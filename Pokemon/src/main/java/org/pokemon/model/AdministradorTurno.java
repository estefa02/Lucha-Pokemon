package org.pokemon.model;

import java.util.List;

import static org.pokemon.model.ConstantesModelo.VELOCIDAD;

public class AdministradorTurno {
    private Jugador jugadorActual;
    private Integer posicionJugadorActual;
    private List<Jugador> jugadores;

    public AdministradorTurno(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void setPrimerTurno() {
        double velocidad1 = getVelocidad(jugadores.get(0));
        double velocidad2 = getVelocidad(jugadores.get(1));

        if (velocidad1 >= velocidad2) {
            this.posicionJugadorActual = 0;
            this.jugadorActual = jugadores.get(0);
        } else {
            this.posicionJugadorActual = 1;
            this.jugadorActual = jugadores.get(1);
        }
    }

    private double getVelocidad(Jugador jugador){
        return jugador.getPokemonActual().getEstadisticas(VELOCIDAD);
    }

    public List<Jugador> getJugadores() {
        return this.jugadores;
    }

    public Jugador getSiguienteJugador(){
        int posicionJugadorSiguiente = 0;
        if (this.posicionJugadorActual < jugadores.size()-1) {
            posicionJugadorSiguiente = this.posicionJugadorActual + 1;
        }
        return this.jugadores.get(posicionJugadorSiguiente);
    }

    public void avanzarTurno(){
        if (this.posicionJugadorActual == jugadores.size()-1) {
            this.posicionJugadorActual = 0;
        }else {
            this.posicionJugadorActual += 1;
        }
        this.jugadorActual = this.jugadores.get(posicionJugadorActual);
    }

    public Jugador getJugadorActual() {
        if (this.jugadorActual == null){
            this.jugadorActual = this.jugadores.get(0);
            this.posicionJugadorActual = 0;
        }
        return this.jugadorActual;
    }
}
