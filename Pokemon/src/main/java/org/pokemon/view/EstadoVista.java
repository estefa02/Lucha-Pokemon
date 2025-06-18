package org.pokemon.view;

import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;

import java.util.List;

public class EstadoVista {

    public static void mostrarEstado(Pokemon pokemon){
        StringBuilder stringEstados = new StringBuilder("Estado: ");
        List<ConstantesModelo.Estados> estados = pokemon.getEstados();
        for (ConstantesModelo.Estados estado : estados){
            if (estado == ConstantesModelo.Estados.DORMIDO){
                stringEstados.append("Dormido ");
            } else if (estado == ConstantesModelo.Estados.ENVENENADO) {
                stringEstados.append("Envenenado ");
            } else if (estado == ConstantesModelo.Estados.PARALIZADO) {
                stringEstados.append("Paralizado ");
            } else if (estado == ConstantesModelo.Estados.CONFUSO) {
                stringEstados.append("Confuso ");
            }
        }

        System.out.println(stringEstados);
    }

    public static void comentarioEstado(Pokemon pokemon){
        String nombre = pokemon.getNombre();
        List<ConstantesModelo.Estados> estados = pokemon.getEstados();
        if (estados.contains(ConstantesModelo.Estados.ENVENENADO)) {
            System.out.println(nombre + " esta ENVENENADO y recibe daño");
        }if (estados.contains(ConstantesModelo.Estados.DORMIDO)) {
            System.out.println(nombre + " esta DORMIDO y no puede luchar");
        }if (estados.contains(ConstantesModelo.Estados.PARALIZADO)) {
            System.out.println(nombre + " esta PARALIZADO...");
        }if (estados.contains(ConstantesModelo.Estados.CONFUSO)) {
            System.out.println(nombre + " esta tan CONFUNDIDO que se hirio asi mismo...");
        }
    }

    public static void pokemonDebilitado(){
        System.out.println("Pokemon DEBILITADO, no puede luchar en este ESTADO");
    }
}
