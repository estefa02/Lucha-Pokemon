package UnitTest;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.AccionEstadistica;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccionEstadisticaTest {
    private AccionEstadistica accionEstadistica;
    private CampoDeBatalla campoDeBatalla;
    private Pokemon pokemonAtacante;
    private Pokemon pokemonDefensor;

    @Before
    public void setUp() {
        accionEstadistica = new AccionEstadistica("Modificar Ataque", 1, "Ataque", 10, 3, "");
        campoDeBatalla = mock(CampoDeBatalla.class);
        pokemonAtacante = mock(Pokemon.class);
        pokemonDefensor = mock(Pokemon.class);
    }

    @Test
    public void testCopiar() {
        AccionEstadistica copia = (AccionEstadistica) accionEstadistica.copiar();
        assertEquals(accionEstadistica.getNombre(), copia.getNombre());
        assertEquals(accionEstadistica.getEstadisticaAModificar(), copia.getEstadisticaAModificar());
        assertEquals(accionEstadistica.getValor(), copia.getValor());
        assertEquals(accionEstadistica.getUsos(), copia.getUsos());
    }

    @Test
    public void testUsarConValorPositivo() {
        when(campoDeBatalla.getPokemonAtacante()).thenReturn(pokemonAtacante);

        accionEstadistica.usar(campoDeBatalla);

        verify(pokemonAtacante).setEstadisticaPokemon("Ataque", 10);
        assertEquals(2, accionEstadistica.getUsos());
    }

    @Test
    public void testUsarConValorNegativo() {
        when(campoDeBatalla.getPokemonDefensor()).thenReturn(pokemonDefensor);
        accionEstadistica = new AccionEstadistica("Modificar Defensa", 1, "Defensa", -5, 2, "");

        accionEstadistica.usar(campoDeBatalla);

        verify(pokemonDefensor).setEstadisticaPokemon("Defensa", -5);
        assertEquals(1, accionEstadistica.getUsos());
    }

    @Test
    public void testUsarConUsosCero() {
        accionEstadistica = new AccionEstadistica("Modificar Ataque", 1, "Ataque", 10, 0, "");
        when(campoDeBatalla.getPokemonAtacante()).thenReturn(pokemonAtacante);


        accionEstadistica.usar(campoDeBatalla);

        verify(pokemonAtacante, Mockito.never()).setEstadisticaPokemon("Ataque", 10);
        assertEquals(0, accionEstadistica.getUsos());
    }
}
