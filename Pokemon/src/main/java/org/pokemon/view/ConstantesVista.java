package org.pokemon.view;

import java.util.ArrayList;
import java.util.List;

public class ConstantesVista {

    public static final String CAMPODEBATALLA = "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*";
    public static final String VS = "-------------|vs|--------------   ";

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String GREY = "\u001B[90m";
    public static final String BLANCO = "\u001B[0m";

    public static final List<String> ACCIONESUSUARIO = new ArrayList<>(List.of("LUCHA", "MOCHILA", "POKéMONES", "HUIR"));
}
