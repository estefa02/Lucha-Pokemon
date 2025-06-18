package UnitTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Clima;
import org.pokemon.model.acciones.AccionClima;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccionClimaTest {
    private AccionClima accionClima;
    private CampoDeBatalla campoDeBatalla;
    private Clima climaSoleado;

    @Before
    public void setUp() {
        climaSoleado = mock(Clima.class);
        when(climaSoleado.getNombre()).thenReturn("soleado");

        accionClima = new AccionClima("Cambiar Clima", 1, climaSoleado, 3);
        campoDeBatalla = mock(CampoDeBatalla.class);
    }

    @Test
    public void testCopiar() {
        AccionClima copia = (AccionClima) accionClima.copiar();
        assertEquals(accionClima.getNombre(), copia.getNombre());
        assertEquals(accionClima.getUsos(), copia.getUsos());
    }

    @Test
    public void testUsarConUsosPositivos() {
        accionClima.usar(campoDeBatalla);

        Mockito.verify(campoDeBatalla).cambiarClima(climaSoleado);
        assertEquals(2, accionClima.getUsos());
    }

    @Test
    public void testUsarConCeroUsos() {
        Clima climaLluvioso = mock(Clima.class);
        when(climaLluvioso.getNombre()).thenReturn("lluvia");
        accionClima = new AccionClima("lluvia", 1, climaLluvioso, 0);

        accionClima.usar(campoDeBatalla);

        Mockito.verify(campoDeBatalla, Mockito.never()).cambiarClima(climaLluvioso);
        assertEquals(0, accionClima.getUsos());
    }
}
