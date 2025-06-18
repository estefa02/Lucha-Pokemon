package org.pokemon.controller;

import org.pokemon.controller.comandos.*;
import org.pokemon.model.*;
import org.pokemon.utilidades.Utilidades;
import org.pokemon.view.*;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.pokemon.model.ConstantesModelo.HUIR;

import static org.pokemon.model.ConstantesModelo.*;

public class JuegoControlador {
    private Juego juego;


    public void empezar(List<Jugador> jugadores, List<Clima> climasDispoibles){
        this.juego = new Juego(jugadores, climasDispoibles);

        for (Jugador jugador : jugadores){
            int idPokemonElegido = InterfazUsuario.elegirPokemon(jugador);
            this.juego.elegirPokemonActual(jugador, idPokemonElegido);
        }

        BatallaVista.inicioBatalla();
    }

    public void batalla() {
        this.juego.primerTurno();

        while (juego.batallaContinua()) {
            Jugador jugadorActual = this.juego.getJugadorActual();

            BatallaVista.batalla(juego.getCampoDeBatalla());

            if (juego.pokemonActualEstaDebilitado()) {
                pokemonActualDebilitado(jugadorActual);
            }

            //juego.aplicarEstados(jugadorActual);
            EstadoVista.comentarioEstado(jugadorActual.getPokemonActual());

            Comando ultimoComando = new ComandoMenu();

            while (ultimoComando != null){

                ultimoComando.mostrar(this.juego, jugadorActual);

                ultimoComando = ultimoComando.ejecutar(this.juego);
            }
        }

        Map<String, Jugador> ganadorPerdedor = juego.getGanadorPerdedor();
        Utilidades.informePartida(ganadorPerdedor);
        BatallaVista.finBatalla(ganadorPerdedor.get(GANADOR), ganadorPerdedor.get(PERDEDOR));
    }

    public void pokemonActualDebilitado(Jugador jugadorActual){
        while (juego.pokemonActualEstaDebilitado()) {
            EstadoVista.pokemonDebilitado();
            int idPokemonElegido = InterfazUsuario.elegirPokemon(jugadorActual);

            juego.elegirPokemonActual(jugadorActual, idPokemonElegido);
        }
    }
}