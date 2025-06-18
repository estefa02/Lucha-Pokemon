package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.*;
import org.pokemon.model.ConstantesModelo.Estados;
import org.pokemon.model.acciones.Accion;
import org.pokemon.model.estados.Estado;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PokemonTest {
    private Pokemon pokemon;
    private Tipo tipo;
    private Estadisticas estadisticas;

    @Before
    public void setUp() {
        tipo = mock(Tipo.class);
        estadisticas = mock(Estadisticas.class);
        when(estadisticas.getEstadistica(anyString())).thenReturn(20.0);

        pokemon = new Pokemon("Pikachu", 1, estadisticas, tipo, "Pokemon eléctrico");
    }

    @Test
    public void testGetNombre() {
        assertEquals("Pikachu", pokemon.getNombre());
    }

    @Test
    public void testGetTipo() {
        assertEquals(tipo, pokemon.getTipo());
    }

    @Test
    public void testSetHabilidad() {
        Accion habilidad = mock(Accion.class);
        pokemon.setHabilidad(habilidad);

        assertTrue(pokemon.getHabilidades().contains(habilidad));
    }

    @Test
    public void testSetEstadisticaPokemon() {
        when(estadisticas.getEstadistica("Ataque")).thenReturn(100.0);
        when(estadisticas.getEstadistica("Defensa")).thenReturn(50.0);

        pokemon.setEstadisticaPokemon("Ataque", 150.0);
        pokemon.setEstadisticaPokemon("Defensa", 75.0);

        verify(estadisticas).setEstadistica("Ataque", 150.0);
        verify(estadisticas).setEstadistica("Defensa", 75.0);
    }

    @Test
    public void testGetHabilidades() {
        Accion habilidad1 = mock(Accion.class);
        Accion habilidad2 = mock(Accion.class);
        pokemon.setHabilidad(habilidad1);
        pokemon.setHabilidad(habilidad2);

        assertTrue(pokemon.getHabilidades().contains(habilidad1));
        assertTrue(pokemon.getHabilidades().contains(habilidad2));
    }

    @Test
    public void testGetEstados() {
        Estado estado1 = mock(Estado.class);
        Estado estado2 = mock(Estado.class);
        when(estado1.estadoEstaActivo()).thenReturn(true);
        when(estado1.getNombre()).thenReturn(Estados.NORMAL);
        when(estado2.estadoEstaActivo()).thenReturn(false);

        pokemon.activarEstado(Estados.NORMAL);
        pokemon.activarEstado(Estados.PARALIZADO);

        assertTrue(pokemon.getEstados().contains(Estados.NORMAL));
        assertTrue(pokemon.getEstados().contains(Estados.PARALIZADO));
    }

    @Test
    public void testActivarEstado() {
        Estado estado = mock(Estado.class);
        when(estado.getNombre()).thenReturn(Estados.PARALIZADO);
        pokemon.activarEstado(Estados.PARALIZADO);

        assertTrue(pokemon.getEstados().contains(Estados.PARALIZADO));
    }

    @Test
    public void testVaciarEstados() {
        Estado estado = mock(Estado.class);
        when(estado.estadoEstaActivo()).thenReturn(true);
        when(estado.getNombre()).thenReturn(Estados.PARALIZADO);

        pokemon.activarEstado(Estados.PARALIZADO);
        pokemon.vaciarEstados();

        assertFalse(pokemon.getEstados().contains(Estados.PARALIZADO));
    }

    @Test
    public void testUsarHabilidad() {
        Accion habilidad = mock(Accion.class);
        CampoDeBatalla campoDeBatalla = mock(CampoDeBatalla.class);
        pokemon.usarHabilidad(campoDeBatalla, habilidad);

        verify(habilidad).usar(campoDeBatalla);
    }

    @Test
    public void testPuedePelear() {
        Estado estado1 = mock(Estado.class);
        Estado estado2 = mock(Estado.class);
        when(estado1.estadoEstaActivo()).thenReturn(true);
        when(estado1.estadoPelea()).thenReturn(true);
        when(estado2.estadoEstaActivo()).thenReturn(true);
        when(estado2.estadoPelea()).thenReturn(false);

        pokemon.activarEstado(Estados.NORMAL);

        assertTrue(pokemon.puedePelear());
    }

    @Test
    public void testBuscarEstadoActual() {
        Estado estado = mock(Estado.class);
        when(estado.estadoEstaActivo()).thenReturn(true);

        pokemon.activarEstado(Estados.PARALIZADO);

        assertTrue(pokemon.buscarEstadoActual(Estados.PARALIZADO));
    }

    @Test
    public void testRecibirDamage() {
        when(estadisticas.getEstadistica(ConstantesModelo.VIDA)).thenReturn(100.0);

        pokemon.recibirDamage(50.0);

        verify(estadisticas).setEstadistica(ConstantesModelo.VIDA, -50.0);
    }

    @Test
    public void testCopiar() {
        Accion habilidad = mock(Accion.class);
        when(habilidad.copiar()).thenReturn(habilidad);

        Pokemon copia = pokemon.copiar();

        assertEquals(pokemon.getNombre(), copia.getNombre());
        assertEquals(pokemon.getId(), copia.getId());
        assertEquals(pokemon.getTipo(), copia.getTipo());
        assertEquals(pokemon.getHabilidades().size(), copia.getHabilidades().size());
        assertTrue(pokemon.getEstados().containsAll(copia.getEstados()));
    }
}