package org.pokemon.controller.comandos;

import org.pokemon.model.Juego;
import org.pokemon.model.Jugador;

import java.util.Map;

public interface Comando {
    Comando ejecutar(Juego juego);

    void mostrar(Juego juego, Jugador jugadorActual);
}
