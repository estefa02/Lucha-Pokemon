package org.pokemon.view;

import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;

import static org.pokemon.view.ConstantesVista.CAMPODEBATALLA;
import static org.pokemon.view.ConstantesVista.VS;

public class BatallaVista {
    public static void inicioBatalla(){
    }

    public static void batalla(CampoDeBatalla campoDeBatalla){
        Jugador jugador1 = campoDeBatalla.getJugadorAtacante();
        Jugador jugador2 = campoDeBatalla.getJugadorDefensor();

        System.out.println(CAMPODEBATALLA);
        mostrarPokemonEsFavorecido(jugador2.getPokemonActual(), campoDeBatalla);
        JugadorVista.mostrarJugador(jugador2, 1);
        PokemonVista.mostrarPokemon(jugador2.getPokemonActual(), 1);
        System.out.print(VS);
        System.out.println("Clima Actual: " + campoDeBatalla.getClima().getNombre());
        PokemonVista.mostrarPokemon(jugador1.getPokemonActual(), 0);
        JugadorVista.mostrarJugador(jugador1, 0);
        mostrarPokemonEsFavorecido(jugador1.getPokemonActual(), campoDeBatalla);
        System.out.println(CAMPODEBATALLA);
    }

    public static void finBatalla(Jugador ganador, Jugador perdedor){
        System.out.println(ganador.getNombre() + " ha ganado la batalla");
        System.out.println(perdedor.getNombre() + " huye o no puede seguir peleando");
    }

    public static void mostrarPokemonEsFavorecido(Pokemon pokemon, CampoDeBatalla campoDeBatalla) {
        if (campoDeBatalla.getClima().pokemonEsFavorecido(pokemon)){
            System.out.println("El clima potencia el ataque de " + pokemon.getNombre() + "!!!");
        }else {
            if (campoDeBatalla.getClima().lastima()) {
                System.out.println("El clima está dañando a " + pokemon.getNombre() + "!!!");
            }
        }
    }
}
