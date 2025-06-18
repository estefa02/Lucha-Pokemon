package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.AccionEstado;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.pokemon.model.ConstantesModelo.Estados.*;

public class AccionEstadoTest {
    private AccionEstado accionEstado;
    private CampoDeBatalla campoDeBatalla;
    private Pokemon pokemonAtacante;
    private Pokemon pokemonDefensor;

    @Before
    public void setUp() {
        accionEstado = new AccionEstado("Paralizar", 1, "paralizado", 3, "", false);
        campoDeBatalla = mock(CampoDeBatalla.class);
        pokemonAtacante = mock(Pokemon.class);
        pokemonDefensor = mock(Pokemon.class);
    }

    @Test
    public void testGetAccion() {
        assertEquals(ConstantesModelo.Acciones.ESTADO, accionEstado.getAccion());
    }

    @Test
    public void testGetNombreEstado() {
        assertEquals("paralizado", accionEstado.getNombreEstado());
    }

    @Test
    public void testCopiar() {
        AccionEstado copia = (AccionEstado) accionEstado.copiar();
        assertEquals(accionEstado.getNombre(), copia.getNombre());
        assertEquals(accionEstado.getNombreEstado(), copia.getNombreEstado());
        assertEquals(accionEstado.getUsos(), copia.getUsos());
    }

    @Test
    public void testUsarConUsosPositivos() {
        when(campoDeBatalla.getPokemonDefensor()).thenReturn(pokemonDefensor);

        accionEstado.usar(campoDeBatalla);

        verify(pokemonDefensor).activarEstado(PARALIZADO);
        assertEquals(2, accionEstado.getUsos());
    }

    @Test
    public void testUsarConUsosCero() {
        accionEstado = new AccionEstado("Paralizar", 1, "paralizado", 0, "", false);
        Mockito.when(campoDeBatalla.getPokemonDefensor()).thenReturn(pokemonDefensor);

        accionEstado.usar(campoDeBatalla);

        verify(pokemonDefensor, Mockito.never()).activarEstado(PARALIZADO);
        assertEquals(0, accionEstado.getUsos());
    }

    @Test
    public void testUsarConDebilitado() {
        accionEstado = new AccionEstado("POCIóN DE REVIVIR", 1, "normal", 3, "", true);
        when(campoDeBatalla.getPokemonAtacante()).thenReturn(pokemonAtacante);
        when(pokemonAtacante.buscarEstadoActual(DEBILITADO)).thenReturn(true);

        accionEstado.usar(campoDeBatalla);

        verify(pokemonAtacante).activarEstado(NORMAL);
        verify(pokemonAtacante).setEstadisticaPokemon("vida", 20);
        assertEquals(2, accionEstado.getUsos());
    }

    @Test
    public void testUsarConDebilitadoYNoEsPocionClave() {
        accionEstado = new AccionEstado("Paralizar", 1, "paralizado", 3, "", false);
        when(campoDeBatalla.getPokemonDefensor()).thenReturn(pokemonDefensor);
        when(pokemonDefensor.buscarEstadoActual(DEBILITADO)).thenReturn(true);

        accionEstado.usar(campoDeBatalla);

        assertEquals(3, accionEstado.getUsos());
    }

    @Test
    public void testUsarConDebilitadoYEsPocionClave() {
        accionEstado = new AccionEstado("POCIóN DE REVIVIR", 1, "normal", 3, "", true);
        when(campoDeBatalla.getPokemonAtacante()).thenReturn(pokemonAtacante);
        when(pokemonAtacante.buscarEstadoActual(DEBILITADO)).thenReturn(true);

        accionEstado.usar(campoDeBatalla);

        verify(pokemonAtacante).activarEstado(NORMAL);
        verify(pokemonAtacante).setEstadisticaPokemon("vida", 20);
        assertEquals(2, accionEstado.getUsos());
    }
}

