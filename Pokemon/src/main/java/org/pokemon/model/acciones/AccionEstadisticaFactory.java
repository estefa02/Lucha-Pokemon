package org.pokemon.model.acciones;

import java.util.Map;

public class AccionEstadisticaFactory implements AccionFactory{
    @Override
    public Accion crearAccion(String nombre, int id, int usos, String historia, Map<String, Object> atributos) {
        int valor = (Integer) atributos.get("poder");
        String estadisticaAModificar = (String) atributos.get("estadisticaDestino");
        return new AccionEstadistica(nombre, id, estadisticaAModificar, valor, usos, historia);
    }
}
