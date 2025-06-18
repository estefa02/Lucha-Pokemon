package org.pokemon.controller;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.pokemon.controller.Eventos.*;
import org.pokemon.controller.ConstantesController.Escenas;
import org.pokemon.model.CampoDeBatalla;
import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.Accion;
import org.pokemon.utilidades.ImagenCache;
import javafx.scene.control.MenuItem;
import org.pokemon.view.AccionVista;
import org.pokemon.view.AccionVisualizer.TextAreaAccionVisualizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.pokemon.controller.ConstantesController.MOCHILA;
import static org.pokemon.model.ConstantesModelo.*;
import static org.pokemon.controller.ConstantesController.*;
import static org.pokemon.utilidades.Utilidades.animacionTexto;

public class BatallaController {
    private CampoDeBatalla campo;
    @FXML
    private HBox derecho;
    @FXML
    private HBox derechoPokeballs;
    @FXML
    private HBox izquierdo;
    @FXML
    private HBox izquierdoPokeballs;
    @FXML
    private ImageView fondo;
    @FXML
    private ImageView TextoImagen;
    @FXML
    private ImageView pokemonAtacanteImagen;
    @FXML
    private ImageView pokemonDefensorImagen;
    @FXML
    private Button botonLucha;
    @FXML
    private Button botonPokemon;
    @FXML
    private Button botonHuir;
    @FXML
    private Button botonMochila;
    @FXML
    private TextArea texto;
    private List<Button> listaBotones;
    private Map<String, Accion> habilidades;
    private ContextMenu menuHabilidades;
    private AccionVista accionVista;

    public void initialize(CampoDeBatalla campoDeBatalla) {
        this.campo = campoDeBatalla;
        this.habilidades = new HashMap<>();
        this.listaBotones = Arrays.asList(botonLucha, botonPokemon, botonHuir, botonMochila);
        this.campo.aplicarEstadosNormales();


        Pokemon pokemonAtacante = this.campo.getPokemonAtacante();
        Pokemon pokemonDefensor = this.campo.getPokemonDefensor();

        setImage(ImagenCache.obtenerImagen(RUTAFONDO, campo.getClima().getNombre(), PNG), fondo);
        setImage(ImagenCache.obtenerImagen(RUTAIMAGEN, "Text", PNG), TextoImagen);

        set(pokemonAtacante.getNombre() + ATAQUE, pokemonAtacante, derecho, pokemonAtacanteImagen, derechoPokeballs, campo.getJugadorAtacante().getPokemons());
        set(pokemonDefensor.getNombre(), pokemonDefensor, izquierdo, pokemonDefensorImagen, izquierdoPokeballs, campo.getJugadorDefensor().getPokemons());

        setBotonLucha();
        String mensaje = "Es el turno de " + campo.getJugadorAtacante().getNombre() + "\n";

        mensaje += pokemonAfectaClima(pokemonAtacante);

        TextAreaAccionVisualizer visualizer = new TextAreaAccionVisualizer(texto);

        this.accionVista = new AccionVista(visualizer);

        mensaje += pokemonEstadosNormalesTexto(pokemonAtacante);

        animacionTexto(mensaje, this.texto);
    }

    private void set(String nombre, Pokemon pokemon, HBox posicionPokemon, ImageView imagen, HBox pokeballs, List<Pokemon> pokemones) {
        agregarPokemon(pokemon, posicionPokemon);
        setImage(ImagenCache.obtenerImagen(RUTAPOKEMON, nombre, GIF), imagen);
        agregarPokeballs(pokeballs, pokemones);
    }

    private void setBotonLucha() {
        menuHabilidades = new ContextMenu();

        for (Accion habilidad : campo.getPokemonAtacante().getHabilidades()) {
            habilidades.put(habilidad.getNombre(), habilidad);
            MenuItem menuItem = new MenuItem(habilidad.getNombre());
            if (habilidad.getUsos() == 0) {
                menuItem.setDisable(true);
            }
            menuItem.setOnAction(this::seleccionarHabilidad);
            menuHabilidades.getItems().add(menuItem);
        }

        botonLucha.setOnAction(event -> menuHabilidades.show(botonLucha, Side.BOTTOM, 0, 0));

        menuHabilidades.setOnHidden(event -> {});
    }

    private void seleccionarHabilidad(ActionEvent actionEvent) {
        campo.aplicarEstadosInhabilitantes();

        if (!this.campo.getPokemonAtacante().puedePelear()){
            EventManager.getInstance().fireEvent(new AtaqueEvent());

            animacionTexto(pokemonEstadosInhabilitantesTexto(this.campo.getPokemonAtacante()), this.texto);
            cambiarTurno();
            return;
        }

        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        String habilidad = menuItem.getText();

        if (habilidades.get(habilidad).getAccion() == Acciones.ATAQUE) {
            animacionAtaque();
        }
        
        accionVista.mostrarAccion(this.campo.getPokemonAtacante(), this.campo.getPokemonDefensor(), habilidades.get(habilidad));

        campo.getPokemonAtacante().usarHabilidad(campo, habilidades.get(habilidad));

        EventManager.getInstance().fireEvent(new AtaqueEvent());
        deshabilitarBotones();

        cambiarTurno();
    }

    private void deshabilitarBotones() {
        for (Button boton : listaBotones) {
            boton.setDisable(true);
        }
    }

    private void animacionAtaque() {
        TranslateTransition atacanteTransition = new TranslateTransition(Duration.seconds(0.1), pokemonAtacanteImagen);

        atacanteTransition.setToX(300.0);
        atacanteTransition.setToY(-100.0);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.5));

        atacanteTransition.setOnFinished(event -> {
            TranslateTransition regresoAtacanteTransition = new TranslateTransition(Duration.seconds(0.2), pokemonAtacanteImagen);
            regresoAtacanteTransition.setToX(0);
            regresoAtacanteTransition.setToY(0);

            regresoAtacanteTransition.play();

            pauseTransition.play();
        });

        atacanteTransition.play();
    }

    private String pokemonAfectaClima(Pokemon pokemon) {
        String mensaje = "";
        if (this.campo.getClima().pokemonEsFavorecido(pokemon)) {
            mensaje = "El clima potencia el ataque de " + pokemon.getNombre() + " !\n";
        }else if (this.campo.getClima().lastima()) {
            mensaje = "El clima está dañando a " + pokemon.getNombre() + " !\n";
        }
        return mensaje;
    }

    private String pokemonEstadosNormalesTexto(Pokemon pokemon) {
        String mensaje = "";
        for (ConstantesModelo.Estados estado : pokemon.getEstados()) {
            if (estado == ConstantesModelo.Estados.ENVENENADO) {
                mensaje += pokemon.getNombre() +  " esta ENVENENADO y recibe daño\n";
            }
        }
        return mensaje;
    }
    private String pokemonEstadosInhabilitantesTexto(Pokemon pokemon) {
        String mensaje = pokemon.getNombre() + " ";
        for (ConstantesModelo.Estados estado : pokemon.getEstados()) {
            if (estado == ConstantesModelo.Estados.DORMIDO) {
                mensaje += "esta DORMIDO y no puede luchar\n";
            }if (estado == ConstantesModelo.Estados.PARALIZADO) {
                mensaje += "esta PARALIZADO...\n";
            }if (estado == ConstantesModelo.Estados.CONFUSO) {
                mensaje +=  "esta tan CONFUNDIDO que se hirio a si mismo...\n";
            }
        }
        return mensaje;
    }

    private void agregarPokeballs(HBox contenedor, List<Pokemon> pokemones) {
        for (Pokemon pokemon : pokemones) {
            Image pokeballImage;

            if (pokemon.getEstados().contains(Estados.DEBILITADO)) {
                pokeballImage = ImagenCache.obtenerImagen(RUTAIMAGEN, "pokeball_debilitado", PNG);
            } else {
                pokeballImage = ImagenCache.obtenerImagen(RUTAIMAGEN, "pokeball", PNG);
            }

            ImageView pokeball = new ImageView(pokeballImage);
            pokeball.setFitWidth(35.0);
            pokeball.setFitHeight(35.0);
            contenedor.getChildren().add(pokeball);
        }
    }

    private void setImage(Image imagenTexto, ImageView imagenView) {
        imagenView.setImage(imagenTexto);
    }

    private void agregarPokemon(Pokemon pokemon, HBox contenedor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Estadistica-Pokemon.fxml"));
            AnchorPane pokemonPane = loader.load();

            EstadisticaPokemonController pokemonController = loader.getController();

            pokemonController.initialize(pokemon);

            contenedor.getChildren().add(pokemonPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cambiarAElegirPokemon(MouseEvent event) throws IOException {
        CambioDeEscenaEvent ev = new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, Escenas.POKEMONS);
        ev.setData(JUGADOR, campo.getJugadorAtacante());
        ev.setData(MOCHILA, null);

        EventManager.getInstance().fireEvent(ev);
    }

    @FXML
    public void abrirMochila(MouseEvent event){
        CambioDeEscenaEvent ev = new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, Escenas.MOCHILA);
        ev.setData(JUGADOR, campo.getJugadorAtacante());

        EventManager.getInstance().fireEvent(ev);
    }

    @FXML
    public void huir(MouseEvent event) throws IOException {
        if (confirmarHuida()) {
            campo.getJugadorAtacante().huir();
            CambioDeEscenaEvent ev = new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, Escenas.FINAL);
            ev.setData(JUGADOR, campo.getJugadorDefensor());
            EventManager.getInstance().fireEvent(ev);
        }
    }

    private boolean confirmarHuida() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Confirmacion.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);

        ConfirmacionController controller = loader.getController();

        stage.showAndWait();

        return controller.esConfirmado();
    }

    private void cambiarTurno(){
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2.0));
        pauseTransition.play();
        pauseTransition.setOnFinished(pauseEvent -> {
            EventManager.getInstance().fireEvent(new CambioDeEscenaEvent(CambioDeEscenaEvent.AVANZAR_TURNO, Escenas.BATALLA));
        });
    }

}
