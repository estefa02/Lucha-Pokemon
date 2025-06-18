package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.*;
import org.pokemon.model.acciones.Accion;
import org.pokemon.model.acciones.AccionAtaque;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccionAtaqueTest {

    private Accion accionAtaque;
    private CampoDeBatalla campoDeBatalla;
    private Pokemon pokemonAtacante;
    private Pokemon pokemonDefensor;
    private Tipo tipo;

    @Before
    public void setUp() {
        tipo = mock(Tipo.class);
        accionAtaque = new AccionAtaque("DOBLE PATADA", 2, 20, tipo, 1, "");
        campoDeBatalla = mock(CampoDeBatalla.class);
        pokemonAtacante = mock(Pokemon.class);
        pokemonDefensor = mock(Pokemon.class);

        when(campoDeBatalla.getPokemonAtacante()).thenReturn(pokemonAtacante);
        when(campoDeBatalla.getPokemonDefensor()).thenReturn(pokemonDefensor);
        when(campoDeBatalla.getClima()).thenReturn(mock(Clima.class));
        when(pokemonAtacante.getTipo()).thenReturn(tipo);
        when(pokemonDefensor.getTipo()).thenReturn(tipo);
        when(tipo.getNombre()).thenReturn("normal");

        Set<String> lista = new HashSet<>();
        lista.add("normal");
        when(tipo.getFuerteContra()).thenReturn(lista);
        when(tipo.getDebilContra()).thenReturn(lista);
        when(tipo.getNuloContra()).thenReturn(lista);
    }

    @Test
    public void testUsarConUsos() {
        when(pokemonAtacante.getEstadisticas(ConstantesModelo.ATAQUE)).thenReturn(50.0);
        when(pokemonDefensor.getEstadisticas(ConstantesModelo.DEFENSA)).thenReturn(30.0);
        when(pokemonAtacante.getEstadisticas(ConstantesModelo.NIVEL)).thenReturn(10.0);
        when(pokemonDefensor.getEstadisticas(ConstantesModelo.NIVEL)).thenReturn(10.0);
        when(pokemonAtacante.getEstadisticas(anyString())).thenReturn(0.0);
        when(pokemonDefensor.getEstadisticas(anyString())).thenReturn(0.0);

        accionAtaque.usar(campoDeBatalla);

        verify(pokemonAtacante, times(2)).getTipo();
        verify(pokemonAtacante, times(2)).getEstadisticas(anyString());
        verify(pokemonDefensor, times(1)).getTipo();
        verify(pokemonDefensor, times(1)).getEstadisticas(anyString());
        verify(pokemonDefensor, times(1)).recibirDamage(anyDouble());
        assertEquals(0, accionAtaque.getUsos());
    }

    @Test
    public void testUsarConCeroUsos() {
        accionAtaque.usar(campoDeBatalla);
        verify(pokemonAtacante, never()).recibirDamage(anyDouble());
    }

}
