package org.pokemon.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.pokemon.controller.Eventos.AtaqueEvent;
import org.pokemon.controller.Eventos.EventManager;
import org.pokemon.model.Pokemon;

import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.controller.Auxiliares.*;

public class EstadisticaPokemonController {
    private Pokemon pokemon;
    @FXML
    private AnchorPane root;
    @FXML
    private Label nombrePokemon;
    @FXML
    private Label nivel;
    @FXML
    private Label vidaPokemon;
    @FXML
    private ProgressBar barraVidaPokemon;
    @FXML
    private HBox estados;

    public void initialize(Pokemon pokemon) {
        this.pokemon = pokemon;

        setPokemonData();
        setEstados(this.pokemon, this.estados);

        EventManager.getInstance().addListener(AtaqueEvent.ATACAR, this::bajarVida);
        root.addEventHandler(AtaqueEvent.ATACAR, this::bajarVida);
    }

    private double getVida() {
        return this.pokemon.getEstadisticas(VIDA) / this.pokemon.getEstadisticas(VIDAMAXIMA);
    }

    public void setPokemonData() {
        this.nombrePokemon.setText(this.pokemon.getNombre());
        this.nivel.setText(String.format("%.0f", this.pokemon.getEstadisticas(NIVEL)));

        setEstadisticas();
    }

    private void setEstadisticas(){
        setProgressBar(barraVidaPokemon, getVida());

        double vida = this.pokemon.getEstadisticas(VIDA);
        double vidaMaxima = this.pokemon.getEstadisticas(VIDAMAXIMA);
        this.vidaPokemon.setText(String.format("%.0f / %.0f", vida, vidaMaxima));
    }

    private void bajarVida(AtaqueEvent event) {
        KeyValue keyValue = new KeyValue(barraVidaPokemon.progressProperty(), getVida());
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0), keyValue);
        Timeline timeline = new Timeline(keyFrame);

        timeline.setOnFinished(e -> {
            setEstadisticas();
        });

        timeline.play();
    }
}
