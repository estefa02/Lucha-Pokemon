package org.pokemon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.pokemon.controller.Eventos.CambioDeEscenaEvent;
import org.pokemon.controller.Eventos.EventManager;
import org.pokemon.model.Jugador;
import org.pokemon.utilidades.ImagenCache;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.util.List;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.controller.ConstantesController.*;

public class EntrenadoresController {
    private Jugador jugador1;
    private Jugador jugador2;
    @FXML
    private Label entrenador1;
    @FXML
    private Label entrenador2;
    @FXML
    private ImageView fondo;
    @FXML
    private ImageView entrenadorImage1;
    @FXML
    private ImageView entrenadorImage2;

    public void initialize(List<Jugador> jugadores) {
        this.jugador1 = jugadores.get(0);
        this.jugador2 = jugadores.get(1);

        Image imagenFondo = ImagenCache.obtenerImagen(RUTAFONDO, "Enfrentamiento", PNG);
        fondo.setImage(imagenFondo);

        inicializar(jugador1, entrenador1, entrenadorImage1);
        inicializar(jugador2, entrenador2, entrenadorImage2);
    }

    private void inicializar(Jugador jugador, Label entrenador, ImageView imagen) {
        setEntrenador(entrenador, imagen, jugador);

        Image imagenEntrenador = ImagenCache.obtenerImagen(RUTAENTRENADORES, jugador.getNombre(), PNG);
        imagen.setImage(imagenEntrenador);

        if (jugador.getPokemonActual() == null){
            agregarAnimacion(imagen);
        }
    }

    private void setEntrenador(Label nombre, ImageView imagen, Jugador jugador) {
        nombre.setText(jugador.getNombre());
        if (jugador.getPokemonActual() != null){
            imagen.setMouseTransparent(true);
        }
    }

    private void agregarAnimacion(ImageView imagen) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), imagen);
        transition.setFromX(0);
        transition.setToX(10);
        transition.setFromY(0);
        transition.setToY(10);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);

        transition.play();
    }

    @FXML
    public void elijeEntrenador1(MouseEvent event){
        elegirPokemon(jugador1);
    }
    @FXML
    public void elijeEntrenador2(MouseEvent event){
        elegirPokemon(jugador2);
    }

    private void elegirPokemon(Jugador jugador) {
        CambioDeEscenaEvent ev = new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, ConstantesController.Escenas.POKEMONS);
        ev.setData(JUGADOR, jugador);
        ev.setData(LISTO, false);

        if (jugador1.getPokemonActual() != null && jugador2.getPokemonActual() != null){
            ev.setData(LISTO, true);
        }

        EventManager.getInstance().fireEvent(ev);
    }
}
