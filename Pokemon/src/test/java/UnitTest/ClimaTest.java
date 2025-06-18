package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Clima;
import org.pokemon.model.Pokemon;
import org.pokemon.model.Tipo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;


public class ClimaTest {
    private Clima clima;
    private List<Pokemon> pokemonsEnBatalla;
    private List<Pokemon> pokemonsEnBatalla2;
    private CampoDeBatalla campoDeBatalla;
    private CampoDeBatalla campoDeBatalla2;

    @Before
    public void setUp(){
        Pokemon pokemon1 = mock(Pokemon.class);
        Pokemon pokemon2 = mock(Pokemon.class);
        Pokemon pokemon3 = mock(Pokemon.class);

        pokemonsEnBatalla = new ArrayList<>();
        pokemonsEnBatalla.add(pokemon1);
        pokemonsEnBatalla.add(pokemon2);

        pokemonsEnBatalla2 = new ArrayList<>();
        pokemonsEnBatalla2.add(pokemon1);
        pokemonsEnBatalla2.add(pokemon3);

        campoDeBatalla = mock(CampoDeBatalla.class);
        campoDeBatalla2 = mock(CampoDeBatalla.class);

        when(campoDeBatalla.getPokemonsEnBatalla()).thenReturn(pokemonsEnBatalla);
        when(campoDeBatalla2.getPokemonsEnBatalla()).thenReturn(pokemonsEnBatalla2);

        when(pokemon1.getTipo()).thenReturn(new Tipo("fuego"));
        when(pokemon2.getTipo()).thenReturn(new Tipo("agua"));
        when(pokemon3.getTipo()).thenReturn(new Tipo("planta"));
    }

    @Test
    public void testAplicarClimaSinDamage() {
        Set<String> tiposFavorecidos = new HashSet<>();
        tiposFavorecidos.add("fuego");

        clima = new Clima("Soleado", tiposFavorecidos, false);

        clima.aplicarClima(campoDeBatalla);

        for (Pokemon pokemon : pokemonsEnBatalla){
            verify(pokemon, never()).recibirDamage(anyDouble());
        }
    }

    @Test
    public void testAplicarClimaConDamageAPokemonNoFavorecido() {
        Set<String> tiposFavorecidos = new HashSet<>();
        tiposFavorecidos.add("agua");
        clima = new Clima("Lluvia", tiposFavorecidos, true);

        clima.aplicarClima(campoDeBatalla);

        verify(pokemonsEnBatalla.get(0)).recibirDamage(anyDouble());
    }

    @Test
    public void testAplicarClimaConDamageAPokemonFavorecido() {
        Set<String> tiposFavorecidos = new HashSet<>();
        tiposFavorecidos.add("fuego");
        clima = new Clima("Tormenta de fuego", tiposFavorecidos, true);

        clima.aplicarClima(campoDeBatalla2);

        verify(pokemonsEnBatalla2.get(0), never()).recibirDamage(anyDouble());
    }
}

