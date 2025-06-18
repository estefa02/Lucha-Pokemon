package org.pokemon.model.acciones;

import java.util.Map;

public class AccionEstadoFactory implements AccionFactory {
    @Override
    public Accion crearAccion(String nombre, int id, int usos, String historia, Map<String, Object> atributos) {
        String efecto = (String) atributos.get("estadoNuevo");
        boolean archivoItems = (boolean) atributos.get("archivoItems");
        return new AccionEstado(nombre, id, efecto, usos, historia, archivoItems);
    }
}
