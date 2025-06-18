package org.pokemon.view;

import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Pokemon;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.view.ConstantesVista.*;


public class PokemonVista {

    public static void mostrarPokemon(Pokemon pokemon, int posicion) {
        if (posicion == 1){
            EstadoVista.mostrarEstado(pokemon);
            mostrarVida(pokemon);
            mostrarNivel(pokemon);
            System.out.println("            " + mostrarNombre(pokemon));
        } else {
            System.out.println("            " + mostrarNombre(pokemon));
            mostrarVida(pokemon);
            mostrarNivel(pokemon);
            EstadoVista.mostrarEstado(pokemon);
        }
    }

    private static void mostrarNivel(Pokemon pokemon){
        System.out.println("              Nivel: " + pokemon.getEstadisticas(NIVEL));
    }

    private static void mostrarVida(Pokemon pokemon){
        double vidaActual = Math.round(pokemon.getEstadisticas(VIDA));
        double vidaMaxima = Math.round(pokemon.getEstadisticas(VIDAMAXIMA));

        if (vidaActual <= 0){
            System.out.print(0 + "/ " + vidaMaxima);
        } else {
            System.out.print(vidaActual + "/ " + vidaMaxima);
        }
    }

    public static String mostrarNombre(Pokemon pokemon){
        String color = BLANCO;
        if (pokemon.buscarEstadoActual(Estados.DEBILITADO)){
            color = GREY;
        }
        return color + pokemon.getNombre() + RESET;
    }
}
