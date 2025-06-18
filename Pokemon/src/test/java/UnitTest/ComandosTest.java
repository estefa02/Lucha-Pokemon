package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.controller.comandos.ComandoCambiarPokemon;
import org.pokemon.model.Juego;
import org.pokemon.model.Jugador;
import org.pokemon.view.InterfazUsuario;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class ComandosTest {
    Juego juego;

    Jugador jugador;

    InterfazUsuario interfaz;

    @Before
    public void setUp() {
        juego = mock(Juego.class);
        jugador = mock(Jugador.class);
        interfaz = mock(InterfazUsuario.class);

    }

    @Test
    public void testComandoCambiarPokemon() {

    }

    @Test
    public void testComandoLuchar() {

    }

    @Test
    public void testComandoMochila() {

    }
}
