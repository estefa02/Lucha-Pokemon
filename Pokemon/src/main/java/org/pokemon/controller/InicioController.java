package org.pokemon.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.pokemon.controller.Eventos.CambioDeEscenaEvent;
import org.pokemon.controller.Eventos.EventManager;

public class InicioController {
    @FXML
    private MediaView mediaView;
    @FXML
    private ImageView imageView;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        String videoFile = getClass().getResource("/org/pokemon/Imagenes/fondoBatalla/fondo_inicio.mp4").toExternalForm();
        Media media = new Media(videoFile);
        this.mediaPlayer = new MediaPlayer(media);

        this.mediaView.setMediaPlayer(this.mediaPlayer);
        this.mediaPlayer.setAutoPlay(true);
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

    }

    @FXML
    private void avanzar(MouseEvent event) {
        imageView.setMouseTransparent(true);

        FadeTransition transicionImagen = setTransicion(2, imageView);
        setTransicion(2, mediaView);

        this.mediaPlayer.stop();


        transicionImagen.setOnFinished(e -> {
            EventManager.getInstance().fireEvent(new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, ConstantesController.Escenas.ENTRENADORES));
        });
    }

    private FadeTransition setTransicion(Integer tiempo, Node nodo){
        FadeTransition transicion = new FadeTransition(Duration.seconds(tiempo), nodo);
        transicion.setFromValue(1.0);
        transicion.setToValue(0.0);

        transicion.play();

        return transicion;
    }

}
