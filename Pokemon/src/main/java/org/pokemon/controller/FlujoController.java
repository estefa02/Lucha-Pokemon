package org.pokemon.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.pokemon.controller.Eventos.CambioDeEscenaEvent;
import org.pokemon.controller.Eventos.ElegirPokemonEvent;
import org.pokemon.controller.Eventos.EventManager;

import org.pokemon.controller.ConstantesController.Escenas;
import org.pokemon.model.*;
import org.pokemon.model.acciones.Accion;
import org.pokemon.utilidades.Utilidades;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.pokemon.controller.ConstantesController.*;
import static org.pokemon.model.ConstantesModelo.GANADOR;
import static org.pokemon.model.ConstantesModelo.VIDA;

public class FlujoController  {
    private Juego juego;
    private Stage primaryStage;
    private MediaPlayer musica;

    public FlujoController(){
        Map<String, Clima> climas = Utilidades.leerArchivoClimas();
        Map<Integer, Accion> habilidades = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/habilidades.Json", false, climas);
        Map<Integer, Accion> itemsDisponibles = Utilidades.leerAcciones("src/main/resources/org/pokemon/ArchivosJSON/items.Json", true, climas);
        Map<String, List<Set<String>>> efectividadTipos = Utilidades.archivoEfectivadTipos();
        Map<Integer, Pokemon> pokemonsDisponibles = Utilidades.leerArchivoPokemons(efectividadTipos, habilidades);
        List<Jugador> jugadores = Utilidades.leerArchivoJugadores(itemsDisponibles, pokemonsDisponibles);

        String musicFile = "src/main/resources/org/pokemon/Musica/musica-de-pelea-pokemon.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        musica = new MediaPlayer(sound);
        musica.setVolume(0.1);

        EventManager.getInstance().addListener(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, this::manejarCambioDeEscena);
        EventManager.getInstance().addListener(CambioDeEscenaEvent.AVANZAR_TURNO, this::cambioDeTurno);

        this.juego = new Juego(jugadores, climas.values().stream().toList());
    }

    private void manejarCambioDeEscena(CambioDeEscenaEvent event) {
        ConstantesController.Escenas siguiente = event.getTitulo();

        try {
            if (siguiente == Escenas.ENTRENADORES) {
                escenaEntrenadores();
            } else if (siguiente == Escenas.POKEMONS) {
                escenaCambiarPokemon((Jugador) event.getData(JUGADOR), (Accion) event.getData(MOCHILA));
            } else if (siguiente == Escenas.BATALLA) {
                if (!juego.batallaContinua()){
                    escenaHallFama(juego.getGanadorPerdedor().get(GANADOR));
                } else {
                    escenaBatalla();
                }
            } else if (siguiente == Escenas.MOCHILA) {
                escenaMochila((Jugador) event.getData(JUGADOR));
            } else if (siguiente == Escenas.FINAL) {
                escenaHallFama((Jugador) event.getData(JUGADOR));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cambioDeTurno(CambioDeEscenaEvent event){
        try {
            juego.avanzarTurno();
            juego.aplicarClima();
        } catch (NullPointerException e) {
            juego.primerTurno();
        }
        EventManager.getInstance().fireEvent(new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, Escenas.BATALLA));
    }

    private void mostrarEscena(String titulo, Scene escena){
        primaryStage.setTitle(titulo);
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    public void pantallaInicio(Stage stage) throws IOException {
        this.primaryStage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Inicio.fxml"));
        StackPane root = loader.load();
        InicioController pantallaInicioController = loader.getController();
        pantallaInicioController.initialize();

        Scene scene = new Scene(root, 700, 500);

        mostrarEscena("INICIO", scene);
    }

    private void escenaEntrenadores() throws IOException {
        if (musica.getStatus() == MediaPlayer.Status.PAUSED || musica.getStatus() == MediaPlayer.Status.READY){
            musica.play();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Entrenadores.fxml"));
        AnchorPane root = loader.load();

        EntrenadoresController controller = loader.getController();
        controller.initialize(juego.getJugadores());

        mostrarEscena("ENTRENADORES", new Scene(root, 700, 500));
    }

    private void escenaCambiarPokemon(Jugador jugador, Accion mochila) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Cambiar-Pokemon.fxml"));
        AnchorPane root = loader.load();
        CambiarPokemonController cambiarPokemonController = loader.getController();

        cambiarPokemonController.initialize(jugador, mochila);

        root.addEventHandler(ElegirPokemonEvent.POKEMON_SELECTED, this::cambiarPokemon);

        mostrarEscena("ELEGIR POKEMON", new Scene(root, 700, 500));
    }

    private void cambiarPokemon(ElegirPokemonEvent event){
        if (event.getMochila() != null){
            this.juego.opcionMochila((Accion) event.getMochila(), event.getPokemonId());
        } else {
            this.juego.elegirPokemonActual(event.getJugador(), event.getPokemonId());
        }

        if (juego.juegoListo()) {
            if (event.getDebilitado()){
                EventManager.getInstance().fireEvent(new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, Escenas.BATALLA));
            } else {
                EventManager.getInstance().fireEvent(new CambioDeEscenaEvent(CambioDeEscenaEvent.AVANZAR_TURNO, Escenas.BATALLA));
            }
        } else {
            EventManager.getInstance().fireEvent(new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, Escenas.ENTRENADORES));
        }
    }

    private void escenaBatalla() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Batalla.fxml"));
        AnchorPane root = loader.load();
        BatallaController batallaController = loader.getController();
        batallaController.initialize(this.juego.getCampoDeBatalla());

        if(juego.getJugadorActual().getPokemonActual().getEstadisticas(VIDA) != 0.0){
            mostrarEscena("BATALLA", new Scene(root, 700, 500));
        } else {
            escenaCambiarPokemon(juego.getJugadorActual(), null);
        }
    }

    private void escenaMochila(Jugador jugador) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Mochila.fxml"));
        AnchorPane root = loader.load();
        MochilaController controller = loader.getController();

        controller.initialize(jugador);

        mostrarEscena("MOCHILA", new Scene(root, 700, 500));

        EventManager.getInstance().addListener(CambioDeEscenaEvent.ITEM_ELEGIDO, this::itemElegido);
        root.addEventHandler(CambioDeEscenaEvent.ITEM_ELEGIDO, this::itemElegido);
    }

    private void itemElegido(CambioDeEscenaEvent event){
        CambioDeEscenaEvent ev = new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, Escenas.POKEMONS);
        ev.setData(JUGADOR, event.getData(JUGADOR));
        ev.setData(MOCHILA, event.getData(ITEM));
        EventManager.getInstance().fireEvent(ev);
    }

    private void escenaHallFama(Jugador jugador) throws IOException {
        Map<String, Jugador> ganadorPerdedor = juego.getGanadorPerdedor();
        Utilidades.informePartida(ganadorPerdedor);

        musica.stop();

        String musicFile = "src/main/resources/org/pokemon/Musica/musica_final.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer musicaFinal = new MediaPlayer(sound);
        musicaFinal.setCycleCount(MediaPlayer.INDEFINITE);
        musicaFinal.setAutoPlay(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Hall-Fama.fxml"));
        AnchorPane root = loader.load();
        HallFamaController controller = loader.getController();

        controller.initialize(jugador);

        mostrarEscena("HALL DE LA FAMA", new Scene(root, 700, 500));
    }
}
