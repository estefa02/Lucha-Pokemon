package org.pokemon.model;

import org.pokemon.model.acciones.Accion;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static org.pokemon.model.ConstantesModelo.Estados.DEBILITADO;

public class Jugador {
    private String nombre;
    private Map<Integer, Pokemon> pokemons;
    private Integer idPokemonActual;
    private Map<Accion, Integer> items;
    private Boolean sigueEnPelea;


    public Jugador(String nombre, Map<Accion, Integer> items, List<Pokemon> pokemonsIniciales){
        this.nombre = nombre;
        this.idPokemonActual = -1;
        this.items = items;
        this.pokemons = new HashMap<>();
        for (Pokemon pokemon : pokemonsIniciales){
            this.pokemons.put(pokemon.getId(), pokemon.copiar());
        }
        this.sigueEnPelea = true;
    }


    public String getNombre() {
        return nombre;
    }
    public List<Pokemon> getPokemons(){
        return this.pokemons.values().stream().toList();
    }
    public Pokemon getPokemonActual() {
        return pokemons.get(idPokemonActual);
    }
    public Boolean getSigueEnPelea(){
        if (this.sigueEnPelea){
            puedeSeguirPeleando();
        }
        return this.sigueEnPelea;
    }
    public Map<Accion, Integer> getItems(){
        return this.items;
    }
    public Pokemon getPokemonPorId(Integer idPokemon){
        return this.pokemons.get(idPokemon);
    }


    public void cambiarPokemon(int idNuevoPokemon) {
        this.idPokemonActual = idNuevoPokemon;
    }

    public void huir() {
        this.sigueEnPelea = false;
    }

    public void usarItem(Accion itemElegido, CampoDeBatalla campoDeBatalla){
        itemElegido.usar(campoDeBatalla);
        if (itemElegido.getUsos() == 0){
            this.items.remove(itemElegido);
        }
    }

    private void puedeSeguirPeleando(){
        for (Pokemon pokemon : this.pokemons.values()){
            if (pokemon.buscarEstadoActual(DEBILITADO)) {
                this.sigueEnPelea = false;
            } else {
                this.sigueEnPelea = true;
                break;
            }
        }
    }
}
