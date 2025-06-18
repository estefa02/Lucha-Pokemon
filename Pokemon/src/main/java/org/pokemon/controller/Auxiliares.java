package org.pokemon.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;
import org.pokemon.utilidades.ImagenCache;

import static org.pokemon.model.ConstantesModelo.PNG;
import static org.pokemon.model.ConstantesModelo.RUTAESTADOS;

public class Auxiliares {
    public static void animacionTexto(String mensaje, Label label){
        Timeline timeline = new Timeline();
        label.setText("");

        for (int i = 0; i <= mensaje.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.02), event -> {
                label.setText(mensaje.substring(0, finalI));
            });

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }
    public static void setEstados(Pokemon pokemon, HBox estados) {
        for (ConstantesModelo.Estados estado : pokemon.getEstados()) {
            if (estado != ConstantesModelo.Estados.NORMAL) {
                Image estadoImage = ImagenCache.obtenerImagen(RUTAESTADOS, estado.name(), PNG);
                ImageView imageView = new ImageView(estadoImage);
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                estados.getChildren().add(imageView);
        }
        }
    }

    public static void setProgressBar(ProgressBar barra, Double progreso){
        if (progreso <= 0.25) {
            barra.getStyleClass().add("progress-bar-red");
        } else if (progreso <= 0.5) {
            barra.getStyleClass().add("progress-bar-orange");
        } else if (progreso <= 0.75) {
            barra.getStyleClass().add("progress-bar-yellow");
        } else {
            barra.getStyleClass().add("progress-bar-green");
        }
        barra.setProgress(progreso);
    }

}
