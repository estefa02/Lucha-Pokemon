package org.pokemon.model.acciones;

import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Pokemon;
import org.pokemon.view.AccionVisitor;

import org.pokemon.view.AccionVisitor;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.view.ConstantesVista.*;

public class AccionEstadistica extends Accion {
    private final String estadisticaAModificar;
    private final int valor;


    public AccionEstadistica(String nombre, int id,  String estadisticaAModificar, int valor, int usos, String historia) {
        this.nombre = nombre;
        this.estadisticaAModificar = estadisticaAModificar;
        this.valor = valor;
        this.usos = usos;
        this.id = id;
        this.historia = historia;
    }

    @Override
    public Acciones getAccion() {
        return Acciones.ESTADISTICA;
    }

    @Override
    public Accion copiar(){
        return new AccionEstadistica(this.nombre, this.id, this.estadisticaAModificar, this.valor, this.usos, this.historia);
    }

    public Integer getValor(){
        return this.valor;
    }

    public String getEstadisticaAModificar(){
        return  this.estadisticaAModificar;
    }

    @Override
    public String getNombre(){
        return this.nombre;
    }

    @Override
    public void usar(CampoDeBatalla campoDeBatalla) {
        if (this.usos == 0) {
            return;
        }
        Pokemon pokemonAfectado;

        if (this.valor > 0){
            pokemonAfectado = campoDeBatalla.getPokemonAtacante();
        } else {
            pokemonAfectado = campoDeBatalla.getPokemonDefensor();
        }

        pokemonAfectado.setEstadisticaPokemon(estadisticaAModificar, valor);
        this.usos--;
    }

    @Override
    public void accept(Pokemon pokemonAtacante, Pokemon pokemonDefensor, AccionVisitor visitor) {
        visitor.visit(pokemonAtacante, pokemonDefensor, this);
    }
}
