package org.pokemon.view;


import org.pokemon.model.*;
import org.pokemon.model.acciones.AccionAtaque;
import org.pokemon.model.acciones.AccionClima;
import org.pokemon.model.acciones.AccionEstadistica;
import org.pokemon.model.acciones.AccionEstado;

public interface AccionVisitor {
    void visit(Pokemon pokAt, Pokemon pokDef, AccionAtaque accionAtaque);
    void visit(Pokemon pokAt, Pokemon pokDef, AccionEstado accionEstado);
    void visit(Pokemon pokAt, Pokemon pokDef, AccionEstadistica accionEstadistica);
    void visit(Pokemon pokAt, Pokemon pokDef, AccionClima accionClima);
}
