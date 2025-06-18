package org.pokemon.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.pokemon.controller.Eventos.CambioDeEscenaEvent;
import org.pokemon.controller.Eventos.EventManager;
import org.pokemon.model.Jugador;
import org.pokemon.model.acciones.Accion;

import java.io.IOException;

public class MochilaController {
    @FXML
    private AnchorPane root;
    @FXML
    private VBox items;
    @FXML
    private Label infoPoty;
    @FXML
    private ImageView pocion;
    private Jugador jugador;
    private static ImageView fotoPocion;
    private static Label infoPocion;


    public void initialize(Jugador jugador){
        this.jugador = jugador;

        setEstaticos(infoPoty, pocion);

        int color = 0;
        for (Accion item : this.jugador.getItems().keySet()){
            agregarItem(item, this.jugador.getItems().get(item),  color);
            color += 1;
        }

        EventManager.getInstance().addListener(CambioDeEscenaEvent.ITEM_ELEGIDO, this::agregarJugadorAEvento);
        root.addEventHandler(CambioDeEscenaEvent.ITEM_ELEGIDO, this::agregarJugadorAEvento);
    }

    private void agregarItem(Accion item,Integer usos, Integer color){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/pokemon/ArchivosFXML/Vista-Item.fxml"));
            AnchorPane itemPane = loader.load();

            ItemVistaController itemController = loader.getController();

            itemController.setItem(item);
            itemController.setItemData(usos);
            setColor(itemPane, color);

            setColor(itemPane, color);

            setColor(itemPane, color);

            items.getChildren().add(itemPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setColor(AnchorPane itemPane, Integer num) {
        if (num % 2 == 0){
            itemPane.setStyle("-fx-background-color: " + "#D6EAF8" + ";");
        } else {
            itemPane.setStyle("-fx-background-color: " + "#EAF2F8" + ";");
        }
    }

    public static void setEstaticos(Label label, ImageView imagen) {
        infoPocion = label;
        fotoPocion = imagen;
    }

    public static void setInfoPocion(String infoPokemon) {
        infoPocion.setText(infoPokemon);
    }
    public static void setFotoPocion(Image imagen){
        fotoPocion.setImage(imagen);
    }

    @FXML
    public void volver(MouseEvent event) {
        EventManager.getInstance().fireEvent(new CambioDeEscenaEvent(CambioDeEscenaEvent.CAMBIO_DE_ESCENA, ConstantesController.Escenas.BATALLA));
    }

    private void agregarJugadorAEvento(CambioDeEscenaEvent event){
        event.setData("jugador", jugador);
    }
}
