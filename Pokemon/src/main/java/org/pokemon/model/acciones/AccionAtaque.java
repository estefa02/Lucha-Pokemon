
package org.pokemon.model.acciones;

import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Pokemon;
import org.pokemon.model.Tipo;
import org.pokemon.view.AccionVisitor;

import java.util.*;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.view.ConstantesVista.RED;
import static org.pokemon.view.ConstantesVista.RESET;

public class AccionAtaque extends Accion {
    private int poder;
    private Tipo tipo;


    public AccionAtaque(String nombre, int id, int poder, Tipo tipo, int usos, String historia) {
        this.nombre = nombre;
        this.poder = poder;
        this.tipo = tipo;
        this.usos = usos;
        this.id = id;
        this.historia = historia;
    }

    @Override
    public String getNombre(){
        return this.nombre;
    }

    @Override
    public Acciones getAccion() {
        return Acciones.ATAQUE;
    }

    @Override
    public Accion copiar(){
        return new AccionAtaque(this.nombre, this.id, this.poder, this.tipo, this.usos, this.historia);
    }

    @Override
    public void usar(CampoDeBatalla campoDeBatalla) {
        if (this.usos == 0) {
            return;
        }
        Pokemon pokemonAtacante = campoDeBatalla.getPokemonAtacante();
        Pokemon pokemonDefensor = campoDeBatalla.getPokemonDefensor();

        double mismoTipo = 1;
        if (pokemonAtacante.getTipo() == this.tipo) {
            mismoTipo = 1.5;
        }

        Random random = new Random();
        int critico = 1;
        if (random.nextDouble() < 0.9) {
            critico = 2;
        }

        double efectividad = Tipo.efectividad(pokemonAtacante.getTipo(), pokemonDefensor.getTipo());
        double rand = (double) (random.nextInt(255 - 217 + 1) + 217) / 255.0;

        double ataqueSobreDefensa = pokemonAtacante.getEstadisticas(ATAQUE) / pokemonDefensor.getEstadisticas(DEFENSA);

        double golpe = calcularGolpe(pokemonAtacante.getEstadisticas(NIVEL), rand, critico, mismoTipo, ataqueSobreDefensa, efectividad);

        if (campoDeBatalla.getClima().pokemonEsFavorecido(pokemonAtacante)) {
            golpe = golpe * 1.10;
        }

        pokemonDefensor.recibirDamage(golpe);
        this.usos--;
    }

    private double calcularGolpe(double nivel, double rand, int critico, double mismoTipo, double ataqueSobreDefensa, double efectividad){
        double golpe = 2;
        golpe *= nivel;
        golpe *= critico;
        golpe *= this.poder;
        golpe *= ataqueSobreDefensa;
        golpe /= 5;
        golpe += 2;
        golpe /= 50;
        golpe *= mismoTipo;
        golpe *= efectividad;
        golpe *= rand;

        return golpe;
    }

    @Override
    public void accept(Pokemon pokemonAtacante, Pokemon pokemonDefensor, AccionVisitor visitor) {
        visitor.visit(pokemonAtacante, pokemonDefensor, this);
    }
}
