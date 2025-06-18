package org.pokemon.controller.comandos;

import org.pokemon.model.Juego;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.Accion;
import org.pokemon.view.AccionVista;
import org.pokemon.view.InterfazUsuario;
import org.pokemon.view.JugadorVista;

public class ComandoMochila implements Comando {
    private Integer pokemonElegido;
    private Jugador jugadorActual;
    private Accion itemElegido;

    @Override
    public Comando ejecutar(Juego juego){
        if (this.itemElegido == null){
            return new ComandoMenu();
        } else if (pokemonElegido == 0){
            return new ComandoMochila();
        }

        juego.opcionMochila(this.itemElegido, this.pokemonElegido);
        Pokemon pokemonElegido = this.jugadorActual.getPokemonPorId(this.pokemonElegido);

        //AccionVista.mostrarAccion(pokemonElegido, pokemonElegido, itemElegido);

        return new ComandoTerminarTurno();
    }

    @Override
    public void mostrar(Juego juego, Jugador jugadorActual){
        this.itemElegido = JugadorVista.menuMochila(jugadorActual);

        while (this.itemElegido != null && !juego.accionDisponible(this.itemElegido)){
            //AccionVista.accionSinUsos();
            this.itemElegido = JugadorVista.menuMochila(jugadorActual);
        }

        if (this.itemElegido != null){
            this.pokemonElegido = InterfazUsuario.elegirPokemon(jugadorActual);
        } else {
            this.pokemonElegido = 0;
        }

        this.jugadorActual = jugadorActual;
    }
}
