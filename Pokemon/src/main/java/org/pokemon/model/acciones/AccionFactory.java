package org.pokemon.model.acciones;

import java.util.Map;

public interface AccionFactory {
    Accion crearAccion(String nombre, int id, int usos, String historia, Map<String, Object> atributos);
}
