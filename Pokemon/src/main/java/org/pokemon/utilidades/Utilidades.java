package org.pokemon.utilidades;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import org.pokemon.model.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pokemon.model.acciones.*;

import static org.pokemon.model.ConstantesModelo.*;

public class Utilidades {
    public static Map<Integer, Pokemon> leerArchivoPokemons(Map<String, List<Set<String>>> efectividadTipos, Map<Integer, Accion> diccionarioHabilidades) {
        Map<Integer, Pokemon> pokemonsTotales = new HashMap<>();
        String filePath = "src/main/resources/org/pokemon/ArchivosJSON/pokemons.Json";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> data = objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> pokemonData : data) {
                String nombrePokemon = (String) pokemonData.get("nombre");
                String historia = (String) pokemonData.get("historia");
                String tipo = (String) pokemonData.get("tipo");
                Integer id = (Integer) pokemonData.get("id");
                List<Integer> habilidades = (List<Integer>) pokemonData.get("habilidades");

                Tipo tipoPokemon = new Tipo(tipo);
                Set<String> fuerteContra = (efectividadTipos.get(tipo)).get(0);
                Set<String> debilContra = (efectividadTipos.get(tipo)).get(1);
                Set<String> nuloContra = (efectividadTipos.get(tipo)).get(2);
                tipoPokemon.setContra(fuerteContra, debilContra, nuloContra);

                Map<String, Double> estadisticasIniciales = new HashMap<>();

                estadisticasIniciales.put(ConstantesModelo.VIDAMAXIMA, ((Integer) pokemonData.get("vidaMaxima")).doubleValue());
                estadisticasIniciales.put(ConstantesModelo.VIDA, ((Integer) pokemonData.get("vidaMaxima")).doubleValue());

                estadisticasIniciales.put(ConstantesModelo.ATAQUE, ((Integer) pokemonData.get("ataque")).doubleValue());
                estadisticasIniciales.put(ConstantesModelo.DEFENSA, ((Integer) pokemonData.get("defensa")).doubleValue());
                estadisticasIniciales.put(ConstantesModelo.VELOCIDAD, ((Integer) pokemonData.get("velocidad")).doubleValue());
                estadisticasIniciales.put(ConstantesModelo.NIVEL, ((Integer) pokemonData.get("nivel")).doubleValue());

                Pokemon pokemon = new Pokemon(
                        nombrePokemon,
                        id,
                        new Estadisticas(estadisticasIniciales),
                        tipoPokemon,
                        historia
                );

                for (Integer habilidad : habilidades) {
                    pokemon.setHabilidad(diccionarioHabilidades.get(habilidad));
                }

                pokemonsTotales.put(id, pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pokemonsTotales;
    }
    public static Map<String, List<Set<String>>> archivoEfectivadTipos() {
        Map<String, List<Set<String>>> diccionarioEfectividadTipos = new HashMap<>();
        String filePath = "src/main/resources/org/pokemon/ArchivosJSON/efectividadTipos.Json";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> data = objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> efectividadTipos : data) {
                List<Set<String>> valor = new ArrayList<>();

                String tipo = (String) efectividadTipos.get("tipo");
                List<String> fuerte = (List<String>) efectividadTipos.get("fuerteContra");
                List<String> debil = (List<String>) efectividadTipos.get("debilContra");
                List<String> nulo = (List<String>) efectividadTipos.get("nuloContra");

                Set<String> fuerteContra = new HashSet<>((fuerte));
                Set<String> debilContra = new HashSet<>((debil));
                Set<String> nuloContra = new HashSet<>((nulo));

                valor.add(fuerteContra);
                valor.add(debilContra);
                valor.add(nuloContra);
                diccionarioEfectividadTipos.put(tipo, valor);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return diccionarioEfectividadTipos;
    }

    public static Map<Integer, Accion> leerAcciones(String filePath, Boolean archivoItems, Map<String, Clima> climas) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> habilidadesData = objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});

            Map<Integer, Accion> habilidades = new HashMap<>();
            for (Map<String, Object> habilidadData : habilidadesData) {
                String nombre = (String) habilidadData.get("nombre");
                String historia = (String) habilidadData.get("historia");
                int id = (int) habilidadData.get("id");
                int usos = (int) habilidadData.get("usos");

                habilidadData.put("climas", climas);
                habilidadData.put("archivoItems", archivoItems);
                habilidadData.put("historia", archivoItems);

                String tipoHabilidad = (String) habilidadData.get("tipoDeAccion");
                AccionFactory factory = obtenerFactoryPorTipo(tipoHabilidad);

                if (factory != null) {
                    Accion habilidadActual = factory.crearAccion(nombre, id, usos, historia, habilidadData);
                    habilidades.put(id, habilidadActual);
                }
            }
            return habilidades;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static AccionFactory obtenerFactoryPorTipo(String tipoHabilidad) {
        if (Objects.equals(tipoHabilidad, CLIMA)) {
            return new AccionClimaFactory();
        } else if (Objects.equals(tipoHabilidad, ATAQUE)) {
            return new AccionAtaqueFactory();
        } else if (Objects.equals(tipoHabilidad, ESTADO)) {
            return new AccionEstadoFactory();
        } else if (Objects.equals(tipoHabilidad, ESTADISTICA)) {
            return new AccionEstadisticaFactory();
        }
        return null;
    }

    public static List<Jugador> leerArchivoJugadores(Map<Integer, Accion> itemsDisponibles, Map<Integer, Pokemon> pokemonsDisponibles) {
        List<Jugador> jugadores = new ArrayList<>();
        String filePath = "src/main/resources/org/pokemon/ArchivosJSON/jugadores.Json";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> data = objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> jugadorData : data) {
                String nombre = (String) jugadorData.get("nombre");

                Map<String, Integer> itemsData = (Map<String, Integer>) jugadorData.get("items");
                Map<Accion, Integer> itemsJugador = new HashMap<>();
                for (String itemId : itemsData.keySet()) {
                    int cantidadItem = itemsData.get(itemId);
                    itemsJugador.put(itemsDisponibles.get(Integer.parseInt(itemId)), cantidadItem);
                }

                List<Integer> pokemonsData = (List<Integer>) jugadorData.get("pokemons");
                List<Pokemon> pokemons = new ArrayList<>();
                for (int pokemonId : pokemonsData) {
                    pokemons.add(pokemonsDisponibles.get(pokemonId));
                }

                Jugador jugador = new Jugador(nombre, itemsJugador, pokemons);
                jugadores.add(jugador);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jugadores;
    }
    public static Map<String, Clima> leerArchivoClimas(){
        Map<String, Clima> climas = new HashMap<>();
        String filePath = "src/main/resources/org/pokemon/ArchivosJSON/climas.Json";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> data = objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> climaData : data) {
                String nombre = (String) climaData.get("nombre");
                Boolean lastima = (Boolean) climaData.get("lastima");

                List<String> tiposData = (List<String>) climaData.get("tiposFavorecidos");
                Set<String> tiposFavorecidos = new HashSet<>();
                for (String nombreTipo : tiposData) {
                    tiposFavorecidos.add(nombreTipo);
                }

                Clima clima = new Clima(nombre, tiposFavorecidos, lastima);
                climas.put(nombre, clima);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return climas;
    }
    public static void informePartida(Map<String, Jugador> Jugadores){
        Jugador ganador = Jugadores.get(GANADOR);
        Jugador perdedor = Jugadores.get(PERDEDOR);

        JugadorInforme ganadorInforme = new JugadorInforme(ganador, true);
        JugadorInforme perdedorInforme = new JugadorInforme(perdedor, false);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Object> data = new ArrayList<>();
        data.add(ganadorInforme);
        data.add(perdedorInforme);

        try {
            objectMapper.writeValue(new File("src/main/resources/org/pokemon/ArchivosJSON/informe.Json"), data);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void animacionTexto(String mensaje, TextArea label){
        Timeline timeline = new Timeline();
        label.setText("");

        for (int i = 0; i <= mensaje.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.02), event -> {
                label.setText(mensaje.substring(0, finalI));
            });

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }
}
