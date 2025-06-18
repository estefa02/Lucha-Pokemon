package org.pokemon.model;

import java.util.List;

public class ConstantesModelo {
    public enum Estados {
        DORMIDO, ENVENENADO, PARALIZADO, NORMAL, DEBILITADO, CONFUSO
    }

    public enum Acciones {
        ATAQUE, ESTADISTICA, ESTADO, CLIMA
    }

    public static final String POCIONCLAVE = "POCIóN DE REVIVIR";

    public static final int LUCHAR = 1;
    public static final int MOCHILA = 2;
    public static final int POKEMONS = 3;
    public static final int HUIR = 4;

    public static final String ESTADO = "estado";
    public static final String CLIMA = "clima";
    public static final String ESTADISTICA = "estadistica";
    public static final String VIDA = "vida";
    public static final String VIDAMAXIMA = "vida maxima";
    public static final String ATAQUE = "ataque";
    public static final String DEFENSA = "defensa";
    public static final String VELOCIDAD = "velocidad";

    public static final String NIVEL = "nivel";

    public static final String RUTAFONDO = "/org/pokemon/Imagenes/fondoBatalla/";
    public static final String RUTAESTADOS = "/org/pokemon/Imagenes/estados/";
    public static final String RUTAPOKEMON = "/org/pokemon/Imagenes/pokemonSprites/";
    public static final String RUTAPOCION = "/org/pokemon/Imagenes/pociones/";
    public static final String RUTAENTRENADORES = "/org/pokemon/Imagenes/entrenadores/";
    public static final String RUTAIMAGEN = "/org/pokemon/Imagenes/";

    public static final int CANTPOKEMONS = 6;

    public static final String PNG = ".png";
    public static final String GIF = ".gif";

    public static final int VIDAALREVIVIR = 20;

    public static final int MUYEFICAZ = 2;
    public static final double POCOEFICAZ = (double) 1 / 2;
    public static final int NOAFECTA = 0;
    public static final int EFICAZ = 1;

    public static final String GANADOR = "ganador";
    public static final String PERDEDOR = "perdedor";

    public static final List<String> ESTADISTICAS = List.of(VIDA, VIDAMAXIMA, ATAQUE, DEFENSA, VELOCIDAD, NIVEL);
}
