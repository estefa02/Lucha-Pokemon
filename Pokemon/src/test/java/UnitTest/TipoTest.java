package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.Tipo;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TipoTest {
    private Tipo tipo1;
    private Tipo tipo2;
    private Tipo tipo3;
    private Tipo tipo4;

    @Before
    public void setUp() {
        tipo1 = new Tipo("Fuego");
        tipo2 = new Tipo("Agua");
        tipo3 = new Tipo("Planta");
        tipo4 = new Tipo("Roca");

        Set<String> tipo1FuerteContra = new HashSet<>();
        tipo1FuerteContra.add("Planta");
        Set<String> tipo1DebilContra = new HashSet<>();
        tipo1DebilContra.add("Agua");
        Set<String> tipo1NuloContra = new HashSet<>();
        tipo1NuloContra.add("Roca");
        tipo1.setContra(tipo1FuerteContra, tipo1DebilContra, tipo1NuloContra);
    }

    @Test
    public void testEfectividad() {
        assertEquals(Tipo.efectividad(tipo1, tipo3), 2.0, 0.01);  // MUY EFICAZ
        assertEquals(Tipo.efectividad(tipo1, tipo2), 0.5, 0.01);  // POCO EFICAZ
        assertEquals(Tipo.efectividad(tipo1, tipo1), 1.0, 0.01);  // NULO
        assertEquals(Tipo.efectividad(tipo1, tipo4), 0.0, 0.01);  // EFICAZ (normal)
    }

}
