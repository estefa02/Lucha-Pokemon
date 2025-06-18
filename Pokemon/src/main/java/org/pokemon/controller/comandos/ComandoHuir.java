package org.pokemon.controller.comandos;

import org.pokemon.model.Juego;
import org.pokemon.model.Jugador;

public class ComandoHuir implements Comando{
    @Override
    public Comando ejecutar(Juego juego){
        juego.opcionHuir();
        return null;
    }

    @Override
    public void mostrar(Juego juego, Jugador jugadorActual){
    }
}
