package org.pokemon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.pokemon.controller.Eventos.ElegirPokemonEvent;
import org.pokemon.model.Pokemon;
import org.pokemon.utilidades.ImagenCache;

import static org.pokemon.controller.Auxiliares.setEstados;
import static org.pokemon.controller.Auxiliares.setProgressBar;
import static org.pokemon.model.ConstantesModelo.*;

public class PokemonController {
    private Pokemon pokemon;
    @FXML
    private AnchorPane pokemon_i;
    @FXML
    private ImageView fotitoPokemon;
    @FXML
    private Label nombrePokemon;
    @FXML
    private Label nivelPokemon;
    @FXML
    private Label vidaPokemon;
    @FXML
    private ProgressBar barraVidaPokemon;
    @FXML
    private HBox estados;

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public void setPokemonData() {
        this.nombrePokemon.setText(this.pokemon.getNombre());
        this.nivelPokemon.setText(String.format("%.0f", this.pokemon.getEstadisticas(NIVEL)));

        setEstados(this.pokemon, estados);

        double vida = this.pokemon.getEstadisticas(VIDA);
        double vidaMaxima = this.pokemon.getEstadisticas(VIDAMAXIMA);
        this.vidaPokemon.setText(String.format("%.0f / %.0f", vida, vidaMaxima));


        Image imagePokemon = ImagenCache.obtenerImagen(RUTAPOKEMON, this.pokemon.getNombre(), GIF);
        this.fotitoPokemon.setImage(imagePokemon);

        double progreso = this.pokemon.getEstadisticas(VIDA) / this.pokemon.getEstadisticas(VIDAMAXIMA);

        setProgressBar(this.barraVidaPokemon, progreso);
    }

    @FXML
    void elegirPokemon(MouseEvent event) {
        int pokemonId = this.pokemon.getId();
        ElegirPokemonEvent elegirPokemonEvent = new ElegirPokemonEvent(ElegirPokemonEvent.CONFRIMAR_ELECCION, pokemonId);
        pokemon_i.fireEvent(elegirPokemonEvent);
    }

    @FXML
    void onMouseEnteredMostrarHistoria(MouseEvent event) {
        String infoPokemon = this.pokemon.getHistoria();
        CambiarPokemonController.setHistoriaPokemon(infoPokemon);
    }

    @FXML
    void onMouseExitedLimpiar(MouseEvent event) {
        CambiarPokemonController.setHistoriaPokemon("");
    }
}
