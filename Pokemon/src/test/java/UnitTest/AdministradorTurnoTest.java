package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pokemon.model.AdministradorTurno;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.pokemon.model.ConstantesModelo.VELOCIDAD;


public class AdministradorTurnoTest {
    private AdministradorTurno administradorTurno;
    private List<Jugador> jugadores;

    @Before
    public void setUp() {
        jugadores = new ArrayList<>();

        Jugador jugador1 = mock(Jugador.class);
        Jugador jugador2 = mock(Jugador.class);

        Pokemon pokemon1 = mock(Pokemon.class);
        Pokemon pokemon2 = mock(Pokemon.class);

        Mockito.when(jugador1.getPokemonActual()).thenReturn(pokemon1);
        Mockito.when(jugador2.getPokemonActual()).thenReturn(pokemon2);

        Mockito.when(pokemon1.getEstadisticas(VELOCIDAD)).thenReturn(15.0);
        Mockito.when(pokemon2.getEstadisticas(VELOCIDAD)).thenReturn(10.0);

        jugadores.add(jugador1);
        jugadores.add(jugador2);

        administradorTurno = new AdministradorTurno(jugadores);
        administradorTurno.setPrimerTurno();
    }

    @Test
    public void testSiguienteJugador() {
        Jugador jugadorEsperado = jugadores.get(1);
        Jugador siguienteJugador = administradorTurno.getSiguienteJugador();
        assertEquals(jugadorEsperado, siguienteJugador);
    }

    @Test
    public void testAvanzarTurno() {
        Jugador jugadorInicial = administradorTurno.getJugadorActual();
        administradorTurno.avanzarTurno();
        Jugador siguienteJugador = administradorTurno.getJugadorActual();
        assertNotEquals(jugadorInicial, siguienteJugador);
    }

    @Test
    public void testAvanzarTurnoCircular() {
        Jugador jugadorInicial = administradorTurno.getJugadorActual();
        for (int i = 0; i < jugadores.size(); i++) {
            administradorTurno.avanzarTurno();
        }
        Jugador jugadorFinal = administradorTurno.getJugadorActual();
        assertEquals(jugadorInicial, jugadorFinal);
    }
}