package org.pokemon;

import javafx.application.Application;
import javafx.stage.Stage;
import org.pokemon.controller.FlujoController;

import java.io.IOException;

public class MainJavaFx extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FlujoController controladorPrincipal = new FlujoController();

            controladorPrincipal.pantallaInicio(primaryStage);


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
