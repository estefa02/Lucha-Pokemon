package org.pokemon.model.estados;

import org.pokemon.model.Pokemon;
import org.pokemon.model.estados.Estado;

import java.util.Random;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.model.ConstantesModelo.VIDAMAXIMA;

public class EstadoConfuso extends Estado {
    private int turnosConfundido;
    private Random random;
    private Boolean puedePelear;

    public EstadoConfuso(){
        this.nombre = Estados.CONFUSO;
        this.estadoActivo = false;
        this.random = new Random();
        this.inhabilitante = true;
    }

    public EstadoConfuso(Random random){
        this.nombre = Estados.CONFUSO;
        this.estadoActivo = false;
        this.random = random;
    }

    @Override
    public void aplicarEstado(Pokemon pokemon) {
        if (turnosConfundido >= 3){
            desactivarEstado();
            turnosConfundido = 0;
        } else {
            if (this.random.nextDouble() > (double) 1 / 3){
                this.puedePelear = false;
                pokemon.recibirDamage(pokemon.getEstadisticas(VIDAMAXIMA) * 0.15);
                turnosConfundido += 1;
            } else {
                this.puedePelear = true;
            }
        }
    }

    public Boolean estadoPelea(){
        return this.puedePelear;
    }
}
