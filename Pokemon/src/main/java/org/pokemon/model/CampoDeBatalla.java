package org.pokemon.model;

import java.util.*;

public class CampoDeBatalla {
    private Jugador jugadorSiguiente;
    private Jugador jugadorActual;
    private Clima climaActual;


    public CampoDeBatalla(List<Clima> climasDisponibles){
        this.climaActual = setClimaInicial(climasDisponibles);
    }

    public Clima setClimaInicial(List<Clima> climasDisponibles) {
        Random rand = new Random();
        double probabilidadSinClima = 2.0 / 3.0;

        if (rand.nextDouble() < probabilidadSinClima) {
            return new Clima("Normal", Collections.emptySet(), false);
        } else {
            int indiceClima = rand.nextInt(climasDisponibles.size());
            return climasDisponibles.get(indiceClima);
        }
    }
    public void aplicarEstadosNormales(){
        getPokemonAtacante().aplicarEstadosNormales();
    }
    public void aplicarEstadosInhabilitantes(){
        getPokemonAtacante().aplicarEstadosInhabilitantes();
    }

    public void cambiarClima(Clima nuevoClima){
        this.climaActual = nuevoClima;
    }

    public Clima getClima(){
        return this.climaActual;
    }

    public void aplicarClimaCampo(){
        this.climaActual.aplicarClima(this);
    }

    public List<Pokemon> getPokemonsEnBatalla(){
        List<Pokemon> pokemonsEnBatalla = new ArrayList<>();
        pokemonsEnBatalla.add(this.jugadorActual.getPokemonActual());
        pokemonsEnBatalla.add(this.jugadorSiguiente.getPokemonActual());
        return pokemonsEnBatalla;
    }

    public Pokemon getPokemonAtacante(){
        return this.jugadorActual.getPokemonActual();
    }

    public Pokemon getPokemonDefensor(){
        return this.jugadorSiguiente.getPokemonActual();
    }

    public Jugador getJugadorAtacante(){
        return this.jugadorActual;
    }

    public Jugador getJugadorDefensor(){
        return this.jugadorSiguiente;
    }

    public Boolean pokemonActualEstaDebilitado(){
        return this.jugadorActual.getPokemonActual().buscarEstadoActual(ConstantesModelo.Estados.DEBILITADO);
    }
    public Boolean pokemonActualPuedePelear(){
        return this.jugadorActual.getPokemonActual().puedePelear();
    }

    public void setJugadores(Jugador jugadorActual, Jugador jugadorSiguiente){
        this.jugadorActual = jugadorActual;
        this.jugadorSiguiente = jugadorSiguiente;
    }
}
