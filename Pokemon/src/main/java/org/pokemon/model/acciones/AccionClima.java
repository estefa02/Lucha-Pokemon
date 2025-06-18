package org.pokemon.model.acciones;

import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.Clima;
import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;
import org.pokemon.view.AccionVisitor;

import static org.pokemon.view.ConstantesVista.*;

public class AccionClima extends Accion {

    private Clima clima;


    public AccionClima(String nombre, int id, Clima clima, int usos) {
        this.nombre = nombre;
        this.usos = usos;
        this.id = id;
        this.clima = clima;
    }

    @Override
    public ConstantesModelo.Acciones getAccion() {
        return ConstantesModelo.Acciones.CLIMA;
    }

    @Override
    public Accion copiar(){
        return new AccionClima(this.nombre, this.id, this.clima, this.usos);
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
        campoDeBatalla.cambiarClima(this.clima);

        this.usos--;
    }

    @Override
    public void accept(Pokemon pokemonAtacante, Pokemon pokemonDefensor, AccionVisitor visitor) {
        visitor.visit(pokemonAtacante, pokemonDefensor, this);
    }
}
