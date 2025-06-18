package org.pokemon;

import org.pokemon.controller.JuegoControlador;
import org.pokemon.model.acciones.Accion;
import org.pokemon.model.Clima;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;
import org.pokemon.utilidades.Utilidades;

import java.util.*;

public class Main {
    public static void main(String[] arg) {
        Map<String, Clima> climas = Utilidades.leerArchivoClimas();
        Map<Integer, Accion> habilidades = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/habilidades.Json", false, climas);
        Map<Integer, Accion> itemsDisponibles = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/items.Json", true, climas);
        Map<String, List<Set<String>>> efectividadTipos = Utilidades.archivoEfectivadTipos();
        Map<Integer, Pokemon> pokemonsDisponibles = Utilidades.leerArchivoPokemons(efectividadTipos, habilidades);
        List<Jugador> jugadores = Utilidades.leerArchivoJugadores(itemsDisponibles, pokemonsDisponibles);

        JuegoControlador juegoControlador = new JuegoControlador();
        juegoControlador.empezar(jugadores, climas.values().stream().toList());
        juegoControlador.batalla();
    }
}

