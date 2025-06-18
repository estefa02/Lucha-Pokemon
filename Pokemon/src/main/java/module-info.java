module org.pokemon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.jline;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens org.pokemon to javafx.fxml;
    opens org.pokemon.controller to javafx.fxml;
    exports org.pokemon.utilidades;

    opens org.pokemon.controller.Eventos to javafx.fxml;

    exports org.pokemon;
    opens org.pokemon.view.AccionVisualizer to javafx.fxml;

}