package EndToEndTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.*;
import org.pokemon.model.acciones.*;

import java.util.*;


import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.model.ConstantesModelo.Estados.DEBILITADO;
import static org.pokemon.model.ConstantesModelo.Estados.NORMAL;


public class CasosBordesTest {
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

        Accion accionAtaqueMatadora = new AccionAtaque("ACCION ATAQUE MATADORA", 5, 100000000, new Tipo("normal"), 10, "");
        Accion accionAtaqueNormal = new AccionAtaque("ACCION ATAQUE NORMAL", 5, 15,  new Tipo("normal"), 10, "");

        favorecidos.add("fuego");
        Clima climaFuego = new Clima("tormenta de fuego", favorecidos, true);
        Accion accionClima = new AccionClima("CLIMA CON DAMAGE", 1, climaFuego, 10);


        pokemon1.setHabilidad(accionAtaqueMatadora);
        pokemon1.setHabilidad(accionAtaqueNormal);

        pokemon2.setHabilidad(accionAtaqueMatadora);
        pokemon2.setHabilidad(accionAtaqueNormal);
        pokemon2.setHabilidad(accionClima);

        Map<Accion, Integer> itemsIniciales = new HashMap<>();

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
    public void MatoPokemonYLoRevivo() {
        juego.primerTurno();
        Jugador jugadorActual = juego.getJugadorActual();

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(0));
        assertTrue(juego.getSiguienteJugador().getPokemonActual().getEstados().contains(DEBILITADO));

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        Accion itemJugador = new AccionEstado("POCIóN DE REVIVIR", 3, "normal", 20, "", true);
        juego.opcionMochila(itemJugador , 1);
        assertTrue(juego.getJugadorActual().getSigueEnPelea());
        assertTrue(juego.getSiguienteJugador().getPokemonActual().getEstados().contains(NORMAL));
        assertEquals(20.0, juego.getSiguienteJugador().getPokemonActual().getEstadisticas(VIDA), 0.0);

        //Chequeo que la partida siga con normalidad

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(1));
        assertTrue(juego.getSiguienteJugador().getPokemonActual().getEstados().contains(NORMAL));
        assertTrue(juego.getSiguienteJugador().getPokemonActual().getEstadisticas(VIDA) < 20.0);

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        jugadorActual.huir();

        assertFalse(jugadorActual.getSigueEnPelea());
        assertFalse(juego.batallaContinua());

        Map<String, Jugador> ganadorPerdedor = juego.getGanadorPerdedor();

        assertEquals(jugadorActual, ganadorPerdedor.get(PERDEDOR));

    }

    @Test
    public void juegoConClimaConDamage() {
        juego.primerTurno();
        Jugador jugadorActual = juego.getJugadorActual();
        juego.getJugadorActual().cambiarPokemon(2);

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(2)); //cambia a clima con damage
        assertEquals("tormenta de fuego", juego.getCampoDeBatalla().getClima().getNombre());
        assertTrue(jugadorActual.getPokemonActual().getEstados().contains(NORMAL));
        assertTrue(juego.getSiguienteJugador().getPokemonActual().getEstados().contains(NORMAL));

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(1)); // ataque normal
        assertTrue(jugadorActual.getPokemonActual().puedePelear());
        assertTrue(juego.getSiguienteJugador().getPokemonActual().puedePelear());

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        juego.opcionLuchar(jugadorActual.getPokemonActual().getHabilidades().get(0)); // habilidad matadora
        assertTrue(juego.getSiguienteJugador().getPokemonActual().getEstados().contains(DEBILITADO));

        juego.avanzarTurno();
        jugadorActual = juego.getJugadorActual();

        jugadorActual.huir();

        assertFalse(jugadorActual.getSigueEnPelea());
        assertFalse(juego.batallaContinua());

        Map<String, Jugador> ganadorPerdedor = juego.getGanadorPerdedor();

        assertEquals(juego.getSiguienteJugador(), ganadorPerdedor.get(GANADOR));
    }

}
