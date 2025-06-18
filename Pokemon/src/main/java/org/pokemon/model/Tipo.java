package org.pokemon.model;

import java.util.*;

import static org.pokemon.model.ConstantesModelo.*;

public class Tipo {
    private String nombre;
    private Set<String> fuerteContra;
    private Set<String> debilContra;
    private Set<String> nuloContra;


    public Tipo(String nombre) {
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }
    public Set<String> getFuerteContra() {
        return fuerteContra;
    }
    public Set<String> getDebilContra() {
        return debilContra;
    }
    public Set<String> getNuloContra() {
        return nuloContra;
    }


    public void setContra(Set<String> fuerteContra, Set<String> debilContra, Set<String> nuloContra) {
        this.fuerteContra = fuerteContra;
        this.debilContra = debilContra;
        this.nuloContra = nuloContra;
    }

    public static double efectividad(Tipo tipoPokemonAtacante, Tipo tipoPokemonDefensor) {
        if (tipoPokemonAtacante.getFuerteContra().contains(tipoPokemonDefensor.getNombre())) {
            return MUYEFICAZ;
        } else if (tipoPokemonAtacante.getDebilContra().contains(tipoPokemonDefensor.getNombre())) {
            return (double) POCOEFICAZ;
        } else if (tipoPokemonAtacante.getNuloContra().contains(tipoPokemonDefensor.getNombre())) {
            return NOAFECTA;
        } else {
            return EFICAZ;
        }
    }


}
