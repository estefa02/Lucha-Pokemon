package org.pokemon.model;

import java.util.*;

import static org.pokemon.model.ConstantesModelo.*;


public class Estadisticas {
    private Map<String, Double> estadisticas;

    public Estadisticas(Map<String, Double> estadisticasIniciales){
        this.estadisticas = new HashMap<>();
        for (int i = 0; i < estadisticasIniciales.size(); i++) {
            String estadistica = ESTADISTICAS.get(i);
            this.estadisticas.put(estadistica, estadisticasIniciales.get(estadistica));
        }
    }

    public double getEstadistica(String estadistica){
        return this.estadisticas.get(estadistica);
    }

    public void setEstadistica(String estadistica, double valor){
        double estadisticaAnterior = this.estadisticas.get(estadistica);
        if (Objects.equals(estadistica, VIDA)) {
            double nuevaVida = estadisticaAnterior + valor;

            // la vida siempre esta entre 0 y la vida maxima
            this.estadisticas.put(estadistica, Math.max(0, Math.min(nuevaVida, this.estadisticas.get(VIDAMAXIMA))));
        } else {
            this.estadisticas.put(estadistica, estadisticaAnterior + valor);
        }
    }

    public Estadisticas copiar(){
        return new Estadisticas(this.estadisticas);
    }
}
