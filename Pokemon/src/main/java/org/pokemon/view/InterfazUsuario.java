package org.pokemon.view;

import org.pokemon.model.acciones.Accion;
import org.pokemon.model.Jugador;

import java.util.List;
import java.util.Scanner;

import static org.pokemon.view.ConstantesVista.ACCIONESUSUARIO;

public class InterfazUsuario {

    public static int pedirNumero(int maximo) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("elija un número: ");
        if (scanner.hasNextInt()) {
            int numero = scanner.nextInt();
            if (numero <= maximo & 0 <= numero) {
                return numero - 1;
            }
        }
        System.out.println("Entrada no válida. Elija un número de la lista.");
        return pedirNumero(maximo);
    }

    public static Integer elegirPokemon(Jugador jugador) {
        System.out.println(jugador.getNombre() + " Seleccione un pokemon:");
        JugadorVista.verPokemons(jugador.getPokemons());
        System.out.print(jugador.getNombre() + " ");

        int posicionEnLaLista = pedirNumero(jugador.getPokemons().size());

        if (posicionEnLaLista == -1){
            return 0;
        }

        return jugador.getPokemons().get(posicionEnLaLista).getId();
    }

    public static Integer elegirOpcionesDeJugador(Jugador jugador) {
        for (int i = 0; i < ACCIONESUSUARIO.size(); i++) {
            System.out.println((i + 1) + " " + ACCIONESUSUARIO.get(i));
        }

        System.out.print(jugador.getNombre() + " ");
        return pedirNumero(4) + 1;
    }

    public static Accion elegirAcciones(List<Accion> acciones){
        //AccionVista.mostrarListaDeAcciones(acciones);
        int habilidadElegida = pedirNumero(acciones.size());

        if (habilidadElegida == -1){
            return null;
        }
        return acciones.get(habilidadElegida);
    }
}
