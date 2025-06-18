package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Estadisticas;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EstadisticasTest {
    private Estadisticas estadisticas;

    @Before
    public void setUp() {
        Map<String, Double> estadisticasVacias = new HashMap<>();
        estadisticasVacias.put(ConstantesModelo.VIDA, 0.0);
        estadisticasVacias.put(ConstantesModelo.VIDAMAXIMA, 100.0);
        estadisticasVacias.put(ConstantesModelo.ATAQUE, 0.0);
        estadisticasVacias.put(ConstantesModelo.DEFENSA, 0.0);
        estadisticasVacias.put(ConstantesModelo.VELOCIDAD, 0.0);
        estadisticasVacias.put(ConstantesModelo.NIVEL, 0.0);

        estadisticas = new Estadisticas(estadisticasVacias);
    }

    @Test
    public void testCrearEstadisticas() {
        Map<String, Double> estadisticasCreadas = new HashMap<>();
        estadisticasCreadas.put(ConstantesModelo.VIDA, 100.0);
        estadisticasCreadas.put(ConstantesModelo.VIDAMAXIMA, 100.0);
        estadisticasCreadas.put(ConstantesModelo.ATAQUE, 50.0);
        estadisticasCreadas.put(ConstantesModelo.DEFENSA, 50.0);
        estadisticasCreadas.put(ConstantesModelo.VELOCIDAD, 70.0);
        estadisticasCreadas.put(ConstantesModelo.NIVEL, 20.0);

        estadisticas = new Estadisticas(estadisticasCreadas);

        assertEquals(100.0, estadisticas.getEstadistica(ConstantesModelo.VIDA), 0.01);
        assertEquals(100.0, estadisticas.getEstadistica(ConstantesModelo.VIDAMAXIMA), 0.01);
        assertEquals(50.0, estadisticas.getEstadistica(ConstantesModelo.ATAQUE), 0.01);
        assertEquals(50.0, estadisticas.getEstadistica(ConstantesModelo.DEFENSA), 0.01);
        assertEquals(70.0, estadisticas.getEstadistica(ConstantesModelo.VELOCIDAD), 0.01);
        assertEquals(20.0, estadisticas.getEstadistica(ConstantesModelo.NIVEL), 0.01);
    }

    @Test
    public void testCambiarEstadisticas(){
        estadisticas.setEstadistica(ConstantesModelo.VIDAMAXIMA, 50.0);
        estadisticas.setEstadistica(ConstantesModelo.VIDA, 50.0);
        estadisticas.setEstadistica(ConstantesModelo.ATAQUE, 10.0);
        estadisticas.setEstadistica(ConstantesModelo.DEFENSA, 10.0);
        estadisticas.setEstadistica(ConstantesModelo.VELOCIDAD, 10.0);
        estadisticas.setEstadistica(ConstantesModelo.NIVEL, 10.0);

        assertEquals(150.0, estadisticas.getEstadistica(ConstantesModelo.VIDAMAXIMA), 0.01);
        assertEquals(50.0, estadisticas.getEstadistica(ConstantesModelo.VIDA), 0.01);
        assertEquals(10.0, estadisticas.getEstadistica(ConstantesModelo.ATAQUE), 0.01);
        assertEquals(10.0, estadisticas.getEstadistica(ConstantesModelo.DEFENSA), 0.01);
        assertEquals(10.0, estadisticas.getEstadistica(ConstantesModelo.VELOCIDAD), 0.01);
        assertEquals(10.0, estadisticas.getEstadistica(ConstantesModelo.NIVEL), 0.01);
    }

    @Test
    public void testVidaNuncaDebajoDeCero(){
        estadisticas.setEstadistica(ConstantesModelo.VIDA, 10.0);
        assertEquals(10.0, estadisticas.getEstadistica(ConstantesModelo.VIDA), 0.01);

        estadisticas.setEstadistica(ConstantesModelo.VIDA, -50000.0);
        assertEquals(0.0, estadisticas.getEstadistica(ConstantesModelo.VIDA), 0.01);
    }

    @Test
    public void testVidaNuncaArribaDeVidaMaxima(){
        estadisticas.setEstadistica(ConstantesModelo.VIDA, 10.0);
        assertEquals(10.0, estadisticas.getEstadistica(ConstantesModelo.VIDA), 0.01);

        estadisticas.setEstadistica(ConstantesModelo.VIDA, 50000.0);
        assertEquals(100.0, estadisticas.getEstadistica(ConstantesModelo.VIDA), 0.01);
    }

    @Test
    public void testCopiarEstadistica(){
        Estadisticas copia = estadisticas.copiar();

        assertEquals(0.0, copia.getEstadistica(ConstantesModelo.VIDA), 0.01);
        assertEquals(100.0, copia.getEstadistica(ConstantesModelo.VIDAMAXIMA), 0.01);
        assertEquals(0.0, copia.getEstadistica(ConstantesModelo.ATAQUE), 0.01);
        assertEquals(0.0, copia.getEstadistica(ConstantesModelo.DEFENSA), 0.01);
        assertEquals(0.0, copia.getEstadistica(ConstantesModelo.VELOCIDAD), 0.01);
        assertEquals(0.0, copia.getEstadistica(ConstantesModelo.NIVEL), 0.01);
    }
}
