package org.pokemon.model.estados;

import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;

import java.util.Random;


public class EstadoParalizado extends Estado {
    private boolean puedePelear;
    private Random random;

    public EstadoParalizado(){
        this.nombre = ConstantesModelo.Estados.PARALIZADO;
        this.estadoActivo = false;
        this.random = new Random();
        this.inhabilitante = true;
    }
    public EstadoParalizado(Random random){
        this.nombre = ConstantesModelo.Estados.PARALIZADO;
        this.estadoActivo = false;
        this.random = random;
    }

    @Override
    public void aplicarEstado(Pokemon pokemon){
        if (this.random.nextDouble() > 0.5){
            puedePelear = false;
        } else {
            puedePelear = true;
        }
    }
    public Boolean estadoPelea(){
        return puedePelear;
    }
}
