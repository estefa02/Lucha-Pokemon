package org.pokemon.model.estados;

import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;
import org.pokemon.model.estados.Estado;

public class EstadoNormal extends Estado {
    public EstadoNormal(){
        this.nombre = ConstantesModelo.Estados.NORMAL;
        this.estadoActivo = true;
        this.inhabilitante = false;
    }
    @Override
    public void aplicarEstado(Pokemon pokemon){
    }

    public Boolean estadoPelea(){
        return true;
    }

}
