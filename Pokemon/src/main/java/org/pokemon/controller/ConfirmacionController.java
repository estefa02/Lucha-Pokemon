package org.pokemon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmacionController {
    private boolean confirmado;

    @FXML
    private void confirmar(ActionEvent event) {
        confirmado = true;
        cerrarVentana(event);
    }

    @FXML
    private void cancelar(ActionEvent event) {
        confirmado = false;
        cerrarVentana(event);
    }

    public boolean esConfirmado() {
        return confirmado;
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

