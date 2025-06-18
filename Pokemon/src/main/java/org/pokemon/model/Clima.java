package org.pokemon.model;


import java.util.HashSet;
import java.util.Set;

import static org.pokemon.model.ConstantesModelo.VIDA;

public class Clima {
    private String nombre;
    private Integer turnosClimaActivo;
    private Set<String> tiposFavorecidos;
    private Boolean lastima;

    public Clima(String nombre, Set<String> tiposFavorecidos, Boolean lastima) {
        this.nombre = nombre;
        this.turnosClimaActivo = 5;
        this.tiposFavorecidos = tiposFavorecidos;
        this.lastima = lastima;
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean pokemonEsFavorecido(Pokemon pokemon) {
        return this.tiposFavorecidos.contains(pokemon.getTipo().getNombre());
    }

    public void aplicarClima(CampoDeBatalla campoDeBatalla){
        if (this.lastima){
            for (Pokemon pokemon : campoDeBatalla.getPokemonsEnBatalla()){
                if (!pokemonEsFavorecido(pokemon)){
                    pokemon.recibirDamage(pokemon.getEstadisticas(VIDA) * 0.1);
                }
            }
        }
        this.turnosClimaActivo--;
        if (this.turnosClimaActivo == 0){
            campoDeBatalla.cambiarClima(new Clima("Normal", new HashSet<>(), false));
        }
    }
    public Boolean lastima(){ return this.lastima; }


}