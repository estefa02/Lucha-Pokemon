package org.pokemon.model.estados;

import org.pokemon.model.Pokemon;
import org.pokemon.model.estados.Estado;

import java.util.Random;

import static org.pokemon.model.ConstantesModelo.*;

public class EstadoDormido extends Estado {
    private double turnosDormido;
    private boolean puedePelear;
    private Random random;

    public EstadoDormido(){
        this.nombre = Estados.DORMIDO;
        this.estadoActivo = false;
        this.random = new Random();
        this.inhabilitante = true;
    }

    public EstadoDormido(Random random){
        this.nombre = Estados.DORMIDO;
        this.estadoActivo = false;
        this.random = random;
    }

    @Override
    public void aplicarEstado(Pokemon pokemon) {
        double probabilidadDeDespertar = 0.25 + this.turnosDormido * 0.25;

        if (this.random.nextDouble() > probabilidadDeDespertar) {
            this.turnosDormido += 1;
            puedePelear = false;
        } else {
            desactivarEstado();
            this.turnosDormido = 0;
            puedePelear = true;
        }
    }
    public Boolean estadoPelea(){
        return puedePelear;
    }
}