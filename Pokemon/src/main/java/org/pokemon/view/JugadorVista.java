package org.pokemon.view;

import org.pokemon.model.acciones.Accion;
import org.pokemon.model.ConstantesModelo.*;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;


public class JugadorVista {

    public static void mostrarJugador(Jugador jugador, int posicion) {
        if (posicion == 0){
            System.out.println(jugador.getNombre());
        }

        System.out.print("|");
        List<Pokemon> pokemonsJugadorActual = jugador.getPokemons();
        for (Pokemon pokemon: pokemonsJugadorActual){
            if (pokemon.buscarEstadoActual(Estados.DEBILITADO)) {
                System.out.print("X|");
            } else {
                System.out.print("O|");
            }
        }
        System.out.println();

        if (posicion == 1){
            System.out.println(jugador.getNombre());
        }
    }

    public static void verPokemons(List<Pokemon> pokemons) {
        int i = 1;
        for (Pokemon pokemon : pokemons) {
            System.out.println(i + " " + PokemonVista.mostrarNombre(pokemon));
            i++;
        }
    }

    public static Accion menuMochila(Jugador jugador){
        List<Accion> lista = new ArrayList<>();
        lista.addAll(jugador.getItems().keySet());
        return InterfazUsuario.elegirAcciones(lista);
    }

    public static Accion menuHabilidades(Jugador jugador){
        return InterfazUsuario.elegirAcciones(jugador.getPokemonActual().getHabilidades());
    }
}