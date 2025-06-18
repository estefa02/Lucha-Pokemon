package org.pokemon.model.acciones;

import org.pokemon.model.Clima;

import java.util.Map;

public class AccionClimaFactory implements AccionFactory{

    @Override
    public Accion crearAccion(String nombre, int id, int usos, String historia, Map<String, Object> atributos) {
        Map<String, Clima> climas = (Map<String, Clima>) atributos.get("climas");
        String climaHabilidad = (String) atributos.get("clima");
        return new AccionClima(nombre, id, climas.get(climaHabilidad), usos);
    }
}
