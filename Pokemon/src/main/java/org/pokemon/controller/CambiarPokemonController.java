package org.pokemon.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.pokemon.controller.Eventos.CambioDeEscenaEvent;
import org.pokemon.controller.Eventos.ElegirPokemonEvent;
import org.pokemon.controller.Eventos.EventManager;
import org.pokemon.controller.ConstantesController.Escenas;
import org.pokemon.model.ConstantesModelo;
import org.pokemon.model.Jugador;
import org.pokemon.model.Pokemon;
import org.pokemon.model.acciones.Accion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.pokemon.controller.ConstantesController.COLOR;
import static org.pokemon.model.ConstantesModelo.POCIONCLAVE;
import static org.pokemon.model.ConstantesModelo.VIDA;

public class CambiarPokemonController {
    @FXML
    private AnchorPane root;
    @FXML
    private VBox vBox;
    @FXML
    private VBox vistaListaPokemons;
    @FXML
    private VBox vistaPokemonActual;
    @FXML
    private Label historiaDelPokemon;
    @FXML
    private Button botonConfirmar;
    private Map<Integer, AnchorPane> pokemonsPane;
    private static Label historiaTexto;
    private Accion mochila;
    private Jugador jugador;

    public static void setHis(Label label) {
        historiaTexto = label;
    }

    public static void setHistoriaPokemon(String infoPokemon) {
        historiaTexto.setText(infoPokemon);
    }

    public void initialize(Jugador jugador, Accion mochila) {
        this.jugador = jugador;
        this.mochila = mochila;
        this.pokemonsPane = new HashMap<>();
        List<Pokemon> pokemonsJugador = this.jugador.getPokemons();
        botonConfirmar.setDisable(true);

        setHis(historiaDelPokemon);

        Pokemon pokemonActual = this.jugador.getPokemonActual();

        if (pokemonActual != null && mochila == null){
            agregarPokemon(pokemonActual, "/org/pokemon/ArchivosFXML/Vista-Pokemon-Actual.fxml", vistaPokemonActual, true);
        } else {
            redimensionar();
        }

        for (Pokemon pokemon : pokemonsJugador) {
            if (pokemonActual != pokemon || mochila != null){
                agregarPokemon(pokemon, "/org/pokemon/ArchivosFXML/Vista-Pokemon-Elegir.fxml", vistaListaPokemons, false);
            }
        }
        setDefault();
    }

    private void redimensionar() {
        vBox.setPrefWidth(90.0);
        vistaPokemonActual.setPrefWidth(875.0);

        vistaPokemonActual.setAlignment(Pos.CENTER);
        root.setStyle(COLOR + "#EAF2F8;");
    }

    private void agregarPokemon(Pokemon pokemon, String path, VBox posicion, Boolean actual) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane pokemonPane = loader.load();

            PokemonController pokemonController = loader.getController();

            pokemonController.setPokemon(pokemon);
            pokemonController.setPokemonData();

            posicion.getChildren().add(pokemonPane);

            if (!actual) {
                pokemonPane.addEventHandler(ElegirPokemonEvent.CONFRIMAR_ELECCION, this::mostrarBotonConfirmacion);
                pokemonsPane.put(pokemon.getId(), pokemonPane);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDefault() {
        AnchorPane pane;
        int color = 0;

        for (Integer id: this.pokemonsPane.keySet()){
            pane = pokemonsPane.get(id);
            if (color % 2 == 0){
                pane.setStyle(COLOR + "#D6EAF8;");
            } else {
                pane.setStyle(COLOR + "#EAF2F8;");
            }
            color ++;
        }
    }

    private void mostrarBotonConfirmacion(ElegirPokemonEvent event) {
        setDefault();
        botonConfirmar.setDisable(false);
        botonConfirmar.setCursor(Cursor.HAND);

        AnchorPane pokemon = pokemonsPane.get(event.getPokemonId());

        pokemon.setStyle(COLOR + "#F0E68C;");

        if (jugador.getPokemonPorId(event.getPokemonId()).getEstados().contains(ConstantesModelo.Estados.DEBILITADO) && !Objects.equals(mochila.getNombre(), POCIONCLAVE)){
            botonConfirmar.setDisable(true);
            botonConfirmar.setCursor(Cursor.DEFAULT);
        }
        botonConfirmar.setOnAction(confirmarEvent -> agregarJugadorAEvento(new ElegirPokemonEvent(ElegirPokemonEvent.POKEMON_SELECTED, event.getPokemonId())));
    }

    private void agregarJugadorAEvento(ElegirPokemonEvent event){
        event.setJugador(jugador);
        event.setMochila(mochila);

        if (jugador.getPokemonActual() != null && jugador.getPokemonActual().getEstadisticas(VIDA) == 0.0) {
            event.setDebilitado(true);
        } else {
            event.setDebilitado(false);
        }
        root.fireEvent(event);
    }

    @FXML
    public void volver(){
        Escenas escena = Escenas.ENTRENADORES;
        if (jugador.getPokemonActual() != null){
            escena = Escenas.BATALLA;
        }
        if (mochila != null) {
            escena = Escenas.MOCHILA;
        }

        CambioDeEscenaEvent ev = new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, escena);
        ev.setData("jugador", this.jugador);
        EventManager.getInstance().fireEvent(ev);
    }
}
