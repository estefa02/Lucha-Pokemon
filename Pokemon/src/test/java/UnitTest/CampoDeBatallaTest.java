package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Clima;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.pokemon.model.ConstantesModelo.Estados.DEBILITADO;

public class CampoDeBatallaTest {

    private CampoDeBatalla campoDeBatalla;
    private Jugador jugadorActual;
    private Jugador jugadorSiguiente;
    private Clima clima;
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    @Before
    public void setUp() {
        jugadorActual = mock(Jugador.class);
        jugadorSiguiente = mock(Jugador.class);

        clima = mock(Clima.class);

        pokemon1 = mock(Pokemon.class);
        pokemon2 = mock(Pokemon.class);

        when(jugadorActual.getPokemonActual()).thenReturn(pokemon1);
        when(jugadorSiguiente.getPokemonActual()).thenReturn(pokemon2);

        List<Clima> climas = new ArrayList<>();
        climas.add(clima);
        when(clima.getNombre()).thenReturn("soleado");

        campoDeBatalla = new CampoDeBatalla(climas);
        campoDeBatalla.setJugadores(jugadorActual, jugadorSiguiente);
    }

    @Test
    public void testClimaInicial() {
        assertNotNull(campoDeBatalla.getClima());
    }

    @Test
    public void testAplicarClima() {
        campoDeBatalla.cambiarClima(clima);
        campoDeBatalla.aplicarClimaCampo();
        verify(clima, times(1)).aplicarClima(campoDeBatalla);
    }

    @Test
    public void testCambiarClima() {
        Clima nuevoClima = mock(Clima.class);
        when(nuevoClima.getNombre()).thenReturn("lluvioso");
        campoDeBatalla.cambiarClima(nuevoClima);
        assertEquals("lluvioso", campoDeBatalla.getClima().getNombre());
    }

    @Test
    public void testGetPokemonsEnBatalla() {
        when(jugadorActual.getPokemonActual()).thenReturn(pokemon1);
        when(jugadorSiguiente.getPokemonActual()).thenReturn(pokemon2);

        assertEquals(2, campoDeBatalla.getPokemonsEnBatalla().size());
    }

    @Test
    public void testGetPokemonAtacante() {
        when(jugadorActual.getPokemonActual()).thenReturn(pokemon1);
        assertEquals(pokemon1, campoDeBatalla.getPokemonAtacante());
    }

    @Test
    public void testGetPokemonDefensor() {
        when(jugadorSiguiente.getPokemonActual()).thenReturn(pokemon2);
        assertEquals(pokemon2, campoDeBatalla.getPokemonDefensor());
    }

    @Test
    public void testGetJugadorAtacante() {
        assertEquals(jugadorActual, campoDeBatalla.getJugadorAtacante());
    }

    @Test
    public void testGetJugadorDefensor() {
        assertEquals(jugadorSiguiente, campoDeBatalla.getJugadorDefensor());
    }

    @Test
    public void testPokemonActualEstaDebilitado() {
        when(jugadorActual.getPokemonActual().buscarEstadoActual(DEBILITADO)).thenReturn(true);
        assertTrue(campoDeBatalla.pokemonActualEstaDebilitado());
    }

    @Test
    public void testPokemonActualPuedePelear() {
        when(jugadorActual.getPokemonActual().puedePelear()).thenReturn(true);
        assertTrue(campoDeBatalla.pokemonActualPuedePelear());
    }
}

