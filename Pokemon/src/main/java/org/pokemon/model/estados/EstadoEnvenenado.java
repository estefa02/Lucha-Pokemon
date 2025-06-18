package org.pokemon.model.estados;

import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;
import org.pokemon.model.estados.Estado;

import static org.pokemon.model.ConstantesModelo.VIDAMAXIMA;

public class EstadoEnvenenado extends Estado {

    public EstadoEnvenenado(){
        this.nombre = ConstantesModelo.Estados.ENVENENADO;
        this.estadoActivo = false;
        this.inhabilitante = false;
    }
    @Override
    public void aplicarEstado(Pokemon pokemon){
        double damageVeneno = pokemon.getEstadisticas(VIDAMAXIMA) * 0.05;
        pokemon.recibirDamage(Math.round(damageVeneno));
    }

    public Boolean estadoPelea(){
        return true;
    }
}
