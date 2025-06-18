package org.pokemon.controller.comandos;

import org.pokemon.model.Juego;
import org.pokemon.model.Jugador;
import org.pokemon.view.EstadoVista;
import org.pokemon.view.InterfazUsuario;


public class ComandoCambiarPokemon implements Comando {
    private Integer pokemonElegido;
    private Jugador jugadorActual;

    @Override
    public Comando ejecutar(Juego juego){
        if (this.pokemonElegido == 0){
            return new ComandoMenu();
        }

        juego.elegirPokemonActual(this.jugadorActual, this.pokemonElegido);
        return new ComandoTerminarTurno();
    }

    @Override
    public void mostrar(Juego juego, Jugador jugadorActual){
        int idPokemonElegido = InterfazUsuario.elegirPokemon(jugadorActual);

        while (juego.pokemonActualEstaDebilitado()) {
            EstadoVista.pokemonDebilitado();
            idPokemonElegido = InterfazUsuario.elegirPokemon(jugadorActual);
        }
        this.pokemonElegido = idPokemonElegido;
        this.jugadorActual = jugadorActual;
    }
}
