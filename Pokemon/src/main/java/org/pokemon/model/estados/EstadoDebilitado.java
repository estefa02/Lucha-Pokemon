package org.pokemon.model.estados;

import org.pokemon.model.Pokemon;
import org.pokemon.model.estados.Estado;

import static org.pokemon.model.ConstantesModelo.*;

public class EstadoDebilitado extends Estado {
    public EstadoDebilitado(){
        this.nombre = Estados.DEBILITADO;
        this.estadoActivo = false;
        this.inhabilitante = true;
    }
    @Override
    public void aplicarEstado(Pokemon pokemon){}

    public Boolean estadoPelea(){
        return false;
    }
}
