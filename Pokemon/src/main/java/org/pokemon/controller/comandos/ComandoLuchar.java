package org.pokemon.controller.comandos;

import org.pokemon.model.Juego;
import org.pokemon.model.Jugador;
import org.pokemon.model.acciones.Accion;
import org.pokemon.view.AccionVista;
import org.pokemon.view.JugadorVista;


public class ComandoLuchar implements Comando {
    private Accion accionElegida;
    private Jugador jugadorActual;

    @Override
    public Comando ejecutar(Juego juego) {
        if (this.accionElegida == null){
            return new ComandoMenu();
        }

        Jugador jugadorSiguiente = juego.getSiguienteJugador();

        if (juego.pokemonActualPuedeLuchar()){

            juego.opcionLuchar(this.accionElegida);

            //AccionVista.mostrarAccion(this.jugadorActual.getPokemonActual(), jugadorSiguiente.getPokemonActual(), this.accionElegida);
        }

        return new ComandoTerminarTurno();
    }

    @Override
    public void mostrar(Juego juego, Jugador jugadorActual){
        Accion habilidadElegida = JugadorVista.menuHabilidades(jugadorActual);

        while (habilidadElegida != null && !juego.accionDisponible(habilidadElegida)){
            //AccionVista.accionSinUsos();
            habilidadElegida = JugadorVista.menuHabilidades(jugadorActual);
        }
        this.accionElegida = habilidadElegida;
        this.jugadorActual = jugadorActual;
    }
}
