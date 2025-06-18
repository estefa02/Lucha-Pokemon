package org.pokemon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;
import org.pokemon.utilidades.ImagenCache;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.controller.ConstantesController.*;

public class HallFamaController {
    @FXML
    private Pane pokemons;
    @FXML
    private Label ganador;

    public void initialize(Jugador jugadorGanador) {
        int i = 0;
        for (Pokemon pokemon : jugadorGanador.getPokemons()){
            ImageView imageView = new ImageView();
            Image pokemonImage = ImagenCache.obtenerImagen(RUTAPOKEMON, pokemon.getNombre(), GIF);
            imageView.setImage(pokemonImage);

            imageView.setFitHeight(ALTURA_HALL_FAMA);
            imageView.setFitWidth(ANCHO_HALL_FAMA);

            if (i < 3) {
                imageView.setLayoutX(X_INICIAL_HALL_FAMA + (SEPARACION_HALL_FAMA * i));
                imageView.setLayoutY(Y_PRIMERA_LINEA_HALL_FAMA);
            } else {
                imageView.setLayoutX(X_INICIAL_HALL_FAMA + (SEPARACION_HALL_FAMA * (i - 3)));
                imageView.setLayoutY(Y_SEGUNDA_LINEA_HALL_FAMA);
            }

            pokemons.getChildren().add(imageView);
            i++;
        }

        ganador.setText("Felicidades " + jugadorGanador.getNombre() + " eres el GANADOR");
    }
}
