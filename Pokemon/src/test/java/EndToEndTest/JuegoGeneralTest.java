package EndToEndTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.*;
import org.pokemon.model.acciones.Accion;
import org.pokemon.model.acciones.AccionAtaque;
import org.pokemon.model.acciones.AccionEstadistica;
import org.pokemon.model.acciones.AccionEstado;

import java.util.*;

import static org.junit.Assert.*;
import static org.pokemon.model.ConstantesModelo.Estados.NORMAL;
import static org.pokemon.model.ConstantesModelo.GANADOR;


public class JuegoGeneralTest {
    private Juego juego;
    private Jugador jugador1;
    private Jugador jugador2;

    @Before
    public void setUp(){
        Set<String> favorecidos = new HashSet<>();
        Clima climaNormal = new Clima("normal", favorecidos, false);
        favorecidos.add("fuego");
        Clima climaSoleado = new Clima("soleado", favorecidos, false);

        List<Clima> climasIniciales = new ArrayList<>();
        climasIniciales.add(climaSoleado);
        climasIniciales.add(climaNormal);

        Map<String, Double> estadisticas = new HashMap<>();
        estadisticas.put(ConstantesModelo.VIDAMAXIMA, 20.0);
        estadisticas.put(ConstantesModelo.VIDA, 20.0);
        estadisticas.put(ConstantesModelo.ATAQUE, 20.0);
        estadisticas.put(ConstantesModelo.DEFENSA, 20.0);
        estadisticas.put(ConstantesModelo.VELOCIDAD, 20.0);
        estadisticas.put(ConstantesModelo.NIVEL, 20.0);

        Tipo electrico = new Tipo("electrico");
        Set<String> fuerteContra1 = new HashSet<>();
        fuerteContra1.add("agua");
        Set<String> debilContra1 = new HashSet<>();
        debilContra1.add("tierra");
        Set<String> nuloContra = new HashSet<>();
        electrico.setContra(fuerteContra1, debilContra1, nuloContra);

        Tipo fuego = new Tipo("fuego");
        Set<String> fuerteContra2 = new HashSet<>();
        fuerteContra2.add("electrico");
        Set<String> debilContra2 = new HashSet<>();
        debilContra2.add("agua");
        fuego.setContra(fuerteContra2, debilContra2, nuloContra);

        Pokemon pokemon1 = new Pokemon("pikachu", 1, new Estadisticas(estadisticas), electrico, "historia") ;
        Pokemon pokemon2 = new Pokemon("charmander", 2,new Estadisticas(estadisticas), fuego, "historia");
        List<Pokemon> pokemonsIniciales = new ArrayList<>();
        pokemonsIniciales.add(pokemon1);
        pokemonsIniciales.add(pokemon2);

        Accion accionAtaque1 = new AccionAtaque("ACCION ATAQUE ELECTRICO", 1, 20, new Tipo("electrico"), 10, "");
        Accion accionAtaque2 = new AccionAtaque("ACCION ATAQUE FUEGO", 2, 20, new Tipo("fuego"), 10, "");
        Accion accionAtaque3 = new AccionAtaque("ACCION ATAQUE MATADORA", 5, 100000000, new Tipo("normal"), 10, "");

        Accion accionEstadistica = new AccionEstadistica("ITEM VIDA", 3, "vida", 20, 10, "");
        Accion accionEstado = new AccionEstado("ITEM ESTADO PARALIZADO", 4, "paralizado", 10, "", true);

        pokemon1.setHabilidad(accionAtaque1);
        pokemon1.setHabilidad(accionAtaque2);
        pokemon1.setHabilidad(accionAtaque3);

        pokemon2.setHabilidad(accionAtaque1);
        pokemon2.setHabilidad(accionAtaque2);
        pokemon2.setHabilidad(accionAtaque3);

        Map<Accion, Integer> itemsIniciales = new HashMap<>();
        itemsIniciales.put(accionEstadistica, 1);
        itemsIniciales.put(accionEstado, 1);

        jugador1 = new Jugador("Mati", itemsIniciales, pokemonsIniciales);
        jugador1.cambiarPokemon(1);
        jugador2 = new Jugador("Nacho", itemsIniciales, pokemonsIniciales);
        jugador2.cambiarPokemon(1);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        juego = new Juego(jugadores, climasIniciales);
    }

    @Test
    public void JuegoCortoTest() {
        juego.primerTurno();
        Jugador jugadorActual = juego.getJugadorActual();

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(0));
        assertTrue(jugadorActual.getPokemonActual().puedePelear());
        assertTrue(jugadorActual.getPokemonActual().getEstados().contains(NORMAL));

        assertTrue(juego.getSiguienteJugador().getSigueEnPelea());

        assertTrue(juego.getSiguienteJugador().getPokemonActual().getEstadisticas("vida") < 20.0);

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();


        Accion itemJugador = new AccionEstadistica("ITEM VIDA", 3, "vida", 20, 10, "");
        juego.opcionMochila(itemJugador, jugadorActual.getPokemonActual().getId());

        assertTrue(jugadorActual.getPokemonActual().getEstados().contains(NORMAL));
        assertTrue(jugadorActual.getPokemonActual().puedePelear());

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(2)); //Habilidad matadora

        assertFalse(juego.getSiguienteJugador().getPokemonActual().puedePelear());

        assertTrue(juego.batallaContinua());

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        jugadorActual.cambiarPokemon(2);
        assertTrue(jugadorActual.getPokemonActual().puedePelear());

        assertTrue(jugadorActual.getSigueEnPelea());

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(1));

        assertTrue(juego.getSiguienteJugador().getPokemonActual().puedePelear());

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(2)); //Habilidad matadora

        assertFalse(juego.getSiguienteJugador().getPokemonActual().puedePelear());
        assertFalse(juego.batallaContinua());

        Map<String, Jugador> ganadorPerdedor = juego.getGanadorPerdedor();

        assertEquals(jugadorActual, ganadorPerdedor.get(GANADOR));

    }


    @Test
    public void JuegoHuirTest() {
        juego.primerTurno();

        juego.opcionHuir();

        assertFalse(juego.batallaContinua());

        Map<String, Jugador> ganadorPerdedor = juego.getGanadorPerdedor();

        assertEquals(juego.getSiguienteJugador(), ganadorPerdedor.get(GANADOR));

    }
}


