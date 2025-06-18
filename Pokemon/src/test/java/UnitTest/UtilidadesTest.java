package UnitTest;

import org.junit.Test;
import org.pokemon.utilidades.Utilidades;
import org.pokemon.model.Clima;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.Accion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class UtilidadesTest {
    @Test
    public void testArchivoEfectivadTipos() {
        Map<String, List<Set<String>>> efectividadTipos = Utilidades.archivoEfectivadTipos();

        assertNotNull(efectividadTipos);
        assertTrue(efectividadTipos.containsKey("agua"));
        assertTrue(efectividadTipos.containsKey("fuego"));
        assertTrue(efectividadTipos.containsKey("veneno"));
        assertTrue(efectividadTipos.containsKey("bicho"));
        assertTrue(efectividadTipos.containsKey("planta"));
        assertTrue(efectividadTipos.containsKey("dragon"));
        assertTrue(efectividadTipos.containsKey("electrico"));
        assertTrue(efectividadTipos.containsKey("fantasma"));
        assertTrue(efectividadTipos.containsKey("hielo"));
        assertTrue(efectividadTipos.containsKey("lucha"));
        assertTrue(efectividadTipos.containsKey("psiquico"));
        assertTrue(efectividadTipos.containsKey("roca"));
        assertTrue(efectividadTipos.containsKey("tierra"));
    }

    @Test
    public void testLeerArchivoClimas() {
        Map<String, Clima> climas = Utilidades.leerArchivoClimas();

        assertNotNull(climas);
        assertTrue(climas.containsKey("Soleado"));
        assertTrue(climas.containsKey("Lluvia"));
        assertTrue(climas.containsKey("Tormenta de arena"));
        assertTrue(climas.containsKey("Niebla"));
        assertTrue(climas.containsKey("Tormenta de rayos"));
        assertTrue(climas.containsKey("Huracán"));
    }

    @Test
    public void testLeerAcciones() {
        Boolean archivoItems = false;
        Map<String, Clima> climas = Utilidades.leerArchivoClimas();

        Map<Integer, Accion> acciones = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/habilidades.Json", archivoItems, climas);

        assertNotNull(acciones);
        assertEquals(53, acciones.size());
    }

    @Test
    public void testLeerAccionesItems() {
        Boolean archivoItems = true;
        Map<String, Clima> climas = Utilidades.leerArchivoClimas();

        Map<Integer, Accion> acciones = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/items.Json", archivoItems, climas);

        assertNotNull(acciones);
        assertEquals(6, acciones.size());
    }

    @Test
    public void testLeerArchivoPokemons() {
        Map<String, List<Set<String>>> efectividadTipos = Utilidades.archivoEfectivadTipos();
        Map<Integer, Accion> diccionarioHabilidades = new HashMap<>();

        Map<Integer, Pokemon> pokemonsTotales = Utilidades.leerArchivoPokemons(efectividadTipos, diccionarioHabilidades);

        assertNotNull(pokemonsTotales);
        assertEquals(12, pokemonsTotales.size());
    }

    @Test
    public void testLeerArchivoJugadores() {
        Boolean archivoItems = true;
        Map<String, Clima> climas = Utilidades.leerArchivoClimas();

        Map<Integer, Accion> itemsDisponibles = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/items.Json", archivoItems, climas);

        Map<String, List<Set<String>>> efectividadTipos = Utilidades.archivoEfectivadTipos();

        archivoItems = false;
        Map<Integer, Accion> diccionarioHabilidades = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/habilidades.Json", archivoItems, climas);

        Map<Integer, Pokemon> pokemonsDisponibles = Utilidades.leerArchivoPokemons(efectividadTipos, diccionarioHabilidades);

        List<Jugador> jugadores = Utilidades.leerArchivoJugadores(itemsDisponibles, pokemonsDisponibles);

        assertNotNull(jugadores);
        assertEquals(2, jugadores.size());
    }
}

