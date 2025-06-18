package org.pokemon.model.estados;

import org.pokemon.model.Pokemon;

import static org.pokemon.model.ConstantesModelo.Estados;

public abstract class Estado {
    protected Estados nombre;
    protected Boolean estadoActivo;
    protected Boolean inhabilitante;

    public Estados getNombre() {
        return nombre;
    }

    public abstract void aplicarEstado(Pokemon pokemon);

    public abstract Boolean estadoPelea();

    public Boolean getInhabilitante(){
        return this.inhabilitante;
    }

    public Boolean estadoEstaActivo(){
        return this.estadoActivo;
    }

    public void activarEstado(){
        this.estadoActivo = true;
    }

    public void desactivarEstado(){
        this.estadoActivo = false;
    }
}
