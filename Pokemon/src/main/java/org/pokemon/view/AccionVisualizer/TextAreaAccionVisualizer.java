package org.pokemon.view.AccionVisualizer;

import javafx.scene.control.TextArea;
import org.pokemon.view.AccionVisualizer.AccionVisualizer;

public class TextAreaAccionVisualizer implements AccionVisualizer {
    private final TextArea textArea;

    public TextAreaAccionVisualizer(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void visualizarAccion(String mensaje) {
        textArea.appendText( mensaje + "\n");
        textArea.setScrollTop(Double.MAX_VALUE);
    }
}