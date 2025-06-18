package org.pokemon.view;

import org.pokemon.model.*;
import org.pokemon.model.acciones.*;
import org.pokemon.view.AccionVisualizer.AccionVisualizer;

import static org.pokemon.model.ConstantesModelo.*;

public class AccionVista implements AccionVisitor {
    private final AccionVisualizer visualizer;

    public AccionVista(AccionVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public void mostrarAccion(Pokemon pokemonAt, Pokemon pokemonDef, Accion accionElegida) {
        visualizer.visualizarAccion(pokemonAt.getNombre() + " usa " + accionElegida.getNombre());

        accionElegida.accept(pokemonAt, pokemonDef, this);
    }

    public void visit(Pokemon pokAt, Pokemon pokDef, AccionAtaque accionAtaque) {
        visualizer.visualizarAccion("El ataque " + mostrarEfectividad(pokAt.getTipo(), pokDef.getTipo()));
    }

    public void visit(Pokemon pokAt, Pokemon pokDef, AccionEstado accionEstado) {
        visualizer.visualizarAccion(pokDef.getNombre() + " ahora está " + accionEstado.getNombreEstado());
    }

    public void visit(Pokemon pokAt, Pokemon pokDef, AccionEstadistica accionEstadistica) {
        if (accionEstadistica.getValor() > 0) {
            visualizer.visualizarAccion(pokAt.getNombre() + " aumentó " + accionEstadistica.getEstadisticaAModificar());
        } else {
            visualizer.visualizarAccion(pokDef.getNombre() + " disminuyó " + accionEstadistica.getEstadisticaAModificar());
        }
    }

    public void visit(Pokemon pokAt, Pokemon pokDef, AccionClima accionClima) {
        visualizer.visualizarAccion("Ahora el CLIMA es " + accionClima.getNombre());
    }

    private String mostrarEfectividad(Tipo tipoPokemonAtacante, Tipo tipoPokemonDefensor) {
        double efectividad = Tipo.efectividad(tipoPokemonAtacante, tipoPokemonDefensor);
        if (efectividad == MUYEFICAZ) {
            return "¡es MUY EFICAZ!";
        } else if (efectividad == POCOEFICAZ) {
            return "es POCO EFICAZ...";
        } else if (efectividad == EFICAZ) {
            return "es EFICAZ (hasta ahí)";
        } else {
            return "NO AFECTA :(";
        }
    }
}

