package org.pokemon.model.acciones;

import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Pokemon;
import org.pokemon.view.AccionVisitor;

import java.util.Objects;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.view.ConstantesVista.*;

public class AccionEstado extends Accion {
    private String nombreEstado;
    private Estados estado;
    private Boolean esUnItem;

    public AccionEstado(String nombre, Integer id, String nombreEstado, Integer usos, String historia, Boolean esUnItem) {
        Estados estado = Estados.NORMAL;
        if (nombreEstado.equals("paralizado")) {
            estado = Estados.PARALIZADO;
        } else if (nombreEstado.equals("envenenado")) {
            estado = Estados.ENVENENADO;
        } else if (nombreEstado.equals("dormido")) {
            estado = Estados.DORMIDO;
        } else if (nombreEstado.equals("confuso")) {
            estado = Estados.CONFUSO;
        }
        this.nombre = nombre;
        this.historia = historia;
        this.nombreEstado = nombreEstado;
        this.estado = estado;
        this.usos = usos;
        this.id = id;
        this.esUnItem = esUnItem;
    }

    @Override
    public Acciones getAccion() {
        return Acciones.ESTADO;
    }

    public String getNombreEstado(){
        return this.nombreEstado;
    }

    @Override
    public String getNombre(){
        return this.nombre;
    }

    @Override
    public Accion copiar(){
        return new AccionEstado(this.nombre, this.id, this.nombreEstado, this.usos, this.historia, this.esUnItem);
    }

    @Override
    public void usar(CampoDeBatalla campoDeBatalla) {
        if (this.usos == 0) {
            return;
        }

        Pokemon pokemonAfectado = campoDeBatalla.getPokemonDefensor();
        if (this.esUnItem){
            pokemonAfectado = campoDeBatalla.getPokemonAtacante();
        }

        if (pokemonAfectado.buscarEstadoActual(Estados.DEBILITADO)){
            if (Objects.equals(nombre, POCIONCLAVE)){
                pokemonAfectado.activarEstado(Estados.NORMAL);
                pokemonAfectado.setEstadisticaPokemon(VIDA, VIDAALREVIVIR);
                this.usos--;
            }
        } else {
            pokemonAfectado.activarEstado(this.estado);
            this.usos--;
        }

    }

    @Override
    public void accept(Pokemon pokemonAtacante, Pokemon pokemonDefensor, AccionVisitor visitor) {
        visitor.visit(pokemonAtacante, pokemonDefensor, this);
    }
}
