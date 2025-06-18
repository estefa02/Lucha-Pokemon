package org.pokemon.model.acciones;

import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.ConstantesModelo.*;
import org.pokemon.model.Pokemon;
import org.pokemon.view.AccionVisitor;

public abstract class Accion {
    protected String nombre;
    protected String historia;
    protected Integer id;
    protected Integer usos;

    public abstract String getNombre();
    public int getUsos() {
        return usos;
    }
    public int getId() {return id;}
    public String getHistoria() {return historia;}
    public abstract Acciones getAccion();
    public abstract Accion copiar();

    public abstract void accept(Pokemon pokemonAtacante, Pokemon pokemonDefensor, AccionVisitor visitor);

    public abstract void usar(CampoDeBatalla campoDeBatalla);
}
