package UnitTest;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.Accion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;


public class JugadorTest {
    private Jugador jugador;

    @Before
    public void setUp() {

        Pokemon pokemon1 = mock(Pokemon.class);
        Pokemon pokemon2 = mock(Pokemon.class);
        Pokemon pokemon3 = mock(Pokemon.class);

        Mockito.when(pokemon1.getId()).thenReturn(1);
        Mockito.when(pokemon2.getId()).thenReturn(2);
        Mockito.when(pokemon3.getId()).thenReturn(3);

        Accion item1 = mock(Accion.class);
        Accion item2 = mock(Accion.class);
        Accion item3 = mock(Accion.class);

        Map<Accion, Integer> itemsIniciales = new HashMap<>();
        itemsIniciales.put(item1, 1);
        itemsIniciales.put(item2, 1);
        itemsIniciales.put(item3, 1);

        List<Pokemon> pokemonsIniciales = new ArrayList<>();
        pokemonsIniciales.add(pokemon1);
        pokemonsIniciales.add(pokemon2);
        pokemonsIniciales.add(pokemon3);

        jugador = new Jugador("Mati", itemsIniciales, pokemonsIniciales);
    }

    @Test
    public void testCambioDePokemon() {
        jugador.cambiarPokemon(1);
        assertEquals(jugador.getPokemonActual(), jugador.getPokemonPorId(1));

        jugador.cambiarPokemon(2);
        assertEquals(jugador.getPokemonActual(), jugador.getPokemonPorId(2));
    }

    @Test
    public void testHuir() {
        jugador.huir();
        assertFalse(jugador.getSigueEnPelea());
    }

    @Test
    public void testUsarItem() {
        CampoDeBatalla campoDeBatallaMock = mock(CampoDeBatalla.class);
        Accion itemElegido = mock(Accion.class);

        when(itemElegido.getUsos()).thenReturn(1);

        jugador.usarItem(itemElegido, campoDeBatallaMock);

        verify(itemElegido).usar(campoDeBatallaMock);
        assertFalse(jugador.getItems().containsKey(itemElegido));
    }
}
