package org.pokemon.model;

import org.pokemon.model.acciones.Accion;
import org.pokemon.view.EstadoVista;

import java.util.*;
import static org.pokemon.model.ConstantesModelo.*;

public class Juego {
    private CampoDeBatalla campoDeBatalla;
    private AdministradorTurno administradorTurno;

    public Juego(List<Jugador> jugadores, List<Clima> climasDisponibles) {
        this.administradorTurno = new AdministradorTurno(jugadores);
        this.campoDeBatalla = new CampoDeBatalla(climasDisponibles);
    }

    public void primerTurno() {
        this.administradorTurno.setPrimerTurno();
        this.campoDeBatalla.setJugadores(this.administradorTurno.getJugadorActual(), this.administradorTurno.getSiguienteJugador());
    }

    public Jugador getJugadorActual() {
        return this.administradorTurno.getJugadorActual();
    }

    public Jugador getSiguienteJugador() {
        return this.administradorTurno.getSiguienteJugador();
    }

    public void avanzarTurno(){
        this.administradorTurno.avanzarTurno();
        this.campoDeBatalla.setJugadores(this.administradorTurno.getJugadorActual(), this.administradorTurno.getSiguienteJugador());
    }
    public CampoDeBatalla getCampoDeBatalla(){
        return this.campoDeBatalla;
    }

    public void elegirPokemonActual(Jugador jugadorActual, int idPokemonElegido){
        jugadorActual.cambiarPokemon(idPokemonElegido);
    }

    public Boolean pokemonActualEstaDebilitado(){
        return this.campoDeBatalla.pokemonActualEstaDebilitado();
    }

    public void aplicarClima(){
        this.campoDeBatalla.aplicarClimaCampo();
    }

    public Boolean pokemonActualPuedeLuchar(){
        return this.campoDeBatalla.pokemonActualPuedePelear();
    }

    public Boolean accionDisponible(Accion accionElegida){
        return accionElegida.getUsos() != 0;
    }

    public void opcionLuchar (Accion habilidadElegida){
        getJugadorActual().getPokemonActual().usarHabilidad(this.campoDeBatalla, habilidadElegida);
    }

    public void opcionMochila (Accion itemElegido, int idPokemonElegido){
        Jugador jugador = getJugadorActual();
        int idPokemonActual = jugador.getPokemonActual().getId();
        jugador.cambiarPokemon(idPokemonElegido);
        jugador.usarItem(itemElegido, this.campoDeBatalla);
        jugador.cambiarPokemon(idPokemonActual);
    }

    public void opcionHuir(){
        getJugadorActual().huir();
    }

    public Map<String, Jugador> getGanadorPerdedor() {
        Map<String,Jugador> ganadorPerdedor = new HashMap<String,Jugador>();
        for (Jugador jugador : this.administradorTurno.getJugadores()) {
            if (jugador.getSigueEnPelea()) {
                ganadorPerdedor.put(GANADOR, jugador);
            } else {
                ganadorPerdedor.put(PERDEDOR, jugador);
            }
        }
        return ganadorPerdedor;
    }

    public List<Jugador> getJugadores(){
        return this.administradorTurno.getJugadores();
    }

    public Boolean juegoListo(){
        for (Jugador jugador : this.administradorTurno.getJugadores()) {
            if (jugador.getPokemonActual() == null) {
                return false;
            }
        }
        return true;
    }

    public Boolean batallaContinua() {
        for (Jugador jugador : this.administradorTurno.getJugadores()) {
            if (!jugador.getSigueEnPelea()) {
                return false;
            }
        }
        return true;
    }

    public void setAdministradorTurno(AdministradorTurno administradorTurno) {
        this.administradorTurno = administradorTurno;
    }

    public void setCampoDeBatalla(CampoDeBatalla campoDeBatalla) {
        this.campoDeBatalla = campoDeBatalla;
    }
}

