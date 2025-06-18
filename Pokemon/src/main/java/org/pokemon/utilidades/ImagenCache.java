package org.pokemon.utilidades;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.pokemon.model.ConstantesModelo.ATAQUE;

public class ImagenCache {

    private static final Map<String, Image> cacheImagenes = new HashMap<>();

    public static Image obtenerImagen(String ruta, String nombreImagen, String extension) {
            try {
                if (!cacheImagenes.containsKey(nombreImagen)) {
                    String rutaImagen = ruta + nombreImagen + extension;

                    InputStream inputStream = ImagenCache.class.getResourceAsStream(rutaImagen);
                    if (inputStream != null) {
                        Image imagen = new Image(inputStream);
                        cacheImagenes.put(nombreImagen, imagen);
                    } else {
                        System.err.println("Error: No se pudo cargar la imagen. InputStream es nulo.");
                    }
                }
                return cacheImagenes.get(nombreImagen);
            } catch (Exception e) {
                System.err.println("Error al intentar cargar la imagen: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
    }

}