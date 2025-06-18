package org.pokemon.model.acciones;

import org.pokemon.model.Tipo;

import java.util.Map;

public class AccionAtaqueFactory implements AccionFactory {
    @Override
    public Accion crearAccion(String nombre, int id, int usos, String historia, Map<String, Object> atributos) {
        String tipo = (String) atributos.get("tipoHabilidad");
        int poder = (int) atributos.get("poder");
        return new AccionAtaque(nombre, id, poder, new Tipo(tipo), usos, historia);
    }
}
