package org.pokemon.controller.comandos;

import org.pokemon.model.Juego;
import org.pokemon.model.Jugador;
import org.pokemon.view.InterfazUsuario;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.model.ConstantesModelo.HUIR;

public class ComandoMenu implements Comando{
    private Integer accionElegida;

    @Override
    public Comando ejecutar(Juego juego){
        if (accionElegida == LUCHAR) {
            return new ComandoLuchar();
        } else if (accionElegida == MOCHILA) {
            return new ComandoMochila();
        } else if (accionElegida == POKEMONS) {
            return new ComandoCambiarPokemon();
        } else if (accionElegida == HUIR) {
            return new ComandoHuir();
        }
        return null;
    }

    @Override
    public void mostrar(Juego juego, Jugador jugadorActual){
        this.accionElegida = InterfazUsuario.elegirOpcionesDeJugador(jugadorActual);
    }

}
