package org.pokemon.utilidades;

import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.Accion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.pokemon.model.ConstantesModelo.VIDA;

public class JugadorInforme {
    String nombre;
    Boolean ganador;
    Map<String, Integer> items;
    List<Map<String, Object> > pokemons;

    public JugadorInforme(Jugador jugador, Boolean ganador) {
        this.nombre = jugador.getNombre();
        this.ganador = ganador;
        Map<Accion, Integer> items = jugador.getItems();
        List<Pokemon> pokemons = jugador.getPokemons();

        Map<String, Integer> itemsInforme = new HashMap<>();
        List<Map<String, Object>> pokemonsInforme = new ArrayList<>();

        for (Accion item : items.keySet()) {
            itemsInforme.put(String.valueOf(item.getId()),item.getUsos());
        }

        for (Pokemon pokemon : pokemons) {
            Map<String, Object> stats = new HashMap<>();

            stats.put("estado", pokemon.getEstados());
            stats.put("vidaRestante", pokemon.getEstadisticas(VIDA));
            stats.put("Id", pokemon.getId());

            pokemonsInforme.add(stats);
        }

        this.items = itemsInforme;
        this.pokemons = pokemonsInforme;
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean getGanador() {
        return ganador;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public List<Map<String, Object>> getPokemons() {
        return pokemons;
    }
}
