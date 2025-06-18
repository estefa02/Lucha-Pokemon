package org.pokemon.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.pokemon.controller.Eventos.CambioDeEscenaEvent;
import org.pokemon.controller.Eventos.EventManager;
import org.pokemon.model.acciones.Accion;
import org.pokemon.utilidades.ImagenCache;

import static org.pokemon.model.ConstantesModelo.*;

public class ItemVistaController {
    private Accion item;
    @FXML
    private Label nombre;
    @FXML
    private Label usos;


    public void setItem(Accion item){
        this.item = item;
    }

    public void setItemData(Integer uso){
        nombre.setText(item.getNombre());
        usos.setText("" + uso);
    }

    @FXML
    public void onMouseEnteredMostrarItem(MouseEvent event){
        String infoPoty = this.item.getHistoria();
        MochilaController.setInfoPocion(infoPoty);
        MochilaController.setFotoPocion(ImagenCache.obtenerImagen(RUTAPOCION, this.item.getNombre(), PNG));
    }

    @FXML
    public void onMouseExitedLimpiar(MouseEvent event) {
        MochilaController.setInfoPocion("");
        MochilaController.setFotoPocion(null);
    }

    @FXML
    public void elegirPoty(MouseEvent event){
        CambioDeEscenaEvent ev = new CambioDeEscenaEvent(CambioDeEscenaEvent.ITEM_ELEGIDO, ConstantesController.Escenas.POKEMONS);
        ev.setData("item", item);
        EventManager.getInstance().fireEvent(ev);
    }
}
