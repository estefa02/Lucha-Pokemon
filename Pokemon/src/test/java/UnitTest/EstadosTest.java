package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.Pokemon;
import org.pokemon.model.estados.*;

import java.util.Random;

import static org.junit.Assert.*;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

class Aleatorio extends Random {}

public class EstadosTest {
    Pokemon pokemon;
    Random random;

    @Before
    public void setUp() {
       pokemon = mock(Pokemon.class);

        random = mock(Aleatorio.class);
        when(random.nextDouble()).thenReturn(1.0);

    }

    @Test
    public void testEstadoConfuso() {
        EstadoConfuso confuso = new EstadoConfuso(random);

        confuso.aplicarEstado(pokemon);
        verify(pokemon).recibirDamage(anyDouble());

        assertFalse(confuso.estadoPelea());
    }

    @Test
    public void testEstadoDebilitado() {
        EstadoDebilitado debilitado = new EstadoDebilitado();

        debilitado.aplicarEstado(pokemon);
        verify(pokemon, never()).recibirDamage(anyDouble());

        assertFalse(debilitado.estadoPelea());

    }

    @Test
    public void testEstadoDormido() {
        EstadoDormido dormido = new EstadoDormido(random);

        dormido.aplicarEstado(pokemon);
        verify(pokemon, never()).recibirDamage(anyDouble());

        assertFalse(dormido.estadoPelea());
    }

    @Test
    public void testEstadoEnvenenado() {
        EstadoEnvenenado envenenado = new EstadoEnvenenado();

        envenenado.aplicarEstado(pokemon);
        verify(pokemon).recibirDamage(anyDouble());

        assertTrue(envenenado.estadoPelea());
    }

    @Test
    public void testEstadoNormal() {
        EstadoNormal normal = new EstadoNormal();

        normal.aplicarEstado(pokemon);
        verify(pokemon, never()).recibirDamage(anyDouble());

        assertTrue(normal.estadoPelea());
    }

    @Test
    public void testEstadoParalizado() {
        EstadoParalizado paralizado = new EstadoParalizado(random);

        paralizado.aplicarEstado(pokemon);

        verify(pokemon, never()).recibirDamage(anyDouble());
        assertFalse(paralizado.estadoPelea());
    }
}


