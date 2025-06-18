package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.pokemon.model.*;
import org.pokemon.model.acciones.Accion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.pokemon.model.ConstantesModelo.GANADOR;
import static org.pokemon.model.ConstantesModelo.PERDEDOR;

public class JuegoTest {
    @Mock
    private CampoDeBatalla campoDeBatalla;

    @Mock
    private AdministradorTurno administradorTurno;

    private Juego juego;

    private Jugador jugador1;
    private Jugador jugador2;

    @Before
    public void setUp() {
        campoDeBatalla = mock(CampoDeBatalla.class);

        this.jugador1 = mock(Jugador.class);
        when(this.jugador1.getPokemonActual()).thenReturn(mock(Pokemon.class));
        this.jugador2 = mock(Jugador.class);
        when(this.jugador2.getPokemonActual()).thenReturn(mock(Pokemon.class));

        administradorTurno = mock(AdministradorTurno.class);
        when(administradorTurno.getJugadorActual()).thenReturn(this.jugador1);
        when(administradorTurno.getSiguienteJugador()).thenReturn(this.jugador2);

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        when(administradorTurno.getJugadores()).thenReturn(jugadores);

        List<Clima> climas = new ArrayList<>();
        Clima soleado = mock(Clima.class);
        when(soleado.getNombre()).thenReturn("soleado");
        climas.add(soleado);
        when(campoDeBatalla.setClimaInicial(climas)).thenReturn(soleado);

        juego = new Juego(jugadores, climas);
        juego.setCampoDeBatalla(campoDeBatalla);
        juego.setAdministradorTurno(administradorTurno);
    }

    @Test
    public void testPrimerTurno() {
        juego.primerTurno();

        verify(administradorTurno).setPrimerTurno();
        verify(campoDeBatalla).setJugadores(this.jugador1, this.jugador2);
    }

    @Test
    public void testPokemonActualEstaDebilitado() {
        when(campoDeBatalla.pokemonActualEstaDebilitado()).thenReturn(true);

        assertTrue(juego.pokemonActualEstaDebilitado());
    }

    @Test
    public void testOpcionLuchar() {
        Accion habilidadElegida = mock(Accion.class);
        Jugador jugadorActual = juego.getJugadorActual();
        Pokemon pokemonActual = jugadorActual.getPokemonActual();

        juego.opcionLuchar(habilidadElegida);

        verify(pokemonActual).usarHabilidad(campoDeBatalla, habilidadElegida);
    }

    @Test
    public void testOpcionMochila() {
        Accion itemElegido = mock(Accion.class);
        Jugador jugadorActual = administradorTurno.getJugadorActual();

        juego.opcionMochila(itemElegido, 1);

        verify(jugadorActual).cambiarPokemon(1);
        verify(jugadorActual).usarItem(itemElegido, campoDeBatalla);
    }

    @Test
    public void testBatallaContinua() {
        when(jugador1.getSigueEnPelea()).thenReturn(true);
        when(jugador2.getSigueEnPelea()).thenReturn(false);

        assertFalse(juego.batallaContinua());
    }

    @Test
    public void testGetGanadorPerdedor() {
        when(jugador1.getSigueEnPelea()).thenReturn(true);
        when(jugador2.getSigueEnPelea()).thenReturn(false);
        Map<String, Jugador> ganadorPerdedor = juego.getGanadorPerdedor();

        assertTrue(ganadorPerdedor.containsKey(GANADOR));
        assertTrue(ganadorPerdedor.containsKey(PERDEDOR));
        assertEquals(this.jugador1, ganadorPerdedor.get(GANADOR));
        assertEquals(this.jugador2, ganadorPerdedor.get(PERDEDOR));
    }

}



