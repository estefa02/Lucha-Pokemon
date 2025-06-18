package org.pokemon.model;


import org.pokemon.model.acciones.Accion;
import org.pokemon.model.estados.*;
import org.pokemon.utilidades.ImagenCache;
import javafx.scene.image.Image;

import java.util.*;

import static org.pokemon.model.ConstantesModelo.*;

public class Pokemon {
    private String nombre;
    private Tipo tipo;
    private String historia;
    private Map<Estados, Estado> estadosNormales;
    private Map<Estados, Estado> estadosInhabilitantes;
    private List<Accion> habilidades;
    private Estadisticas estadisticas;
    private Integer id;


    public Pokemon(String nombre, Integer id, Estadisticas estadisticas, Tipo tipo, String historia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.historia = historia;
        this.estadisticas = estadisticas;
        this.habilidades = new ArrayList<>();
        this.id = id;

        this.estadosNormales = new HashMap<>();
        this.estadosInhabilitantes = new HashMap<>();
        setearEstados(estadosNormales, estadosInhabilitantes);
    }

    private void setearEstados(Map<Estados, Estado> normales, Map<Estados, Estado> inhabilitantes){
        normales.put(Estados.NORMAL, new EstadoNormal());
        normales.put(Estados.ENVENENADO, new EstadoEnvenenado());
        inhabilitantes.put(Estados.DORMIDO, new EstadoDormido());
        inhabilitantes.put(Estados.CONFUSO, new EstadoConfuso());
        inhabilitantes.put(Estados.PARALIZADO, new EstadoParalizado());
        inhabilitantes.put(Estados.DEBILITADO, new EstadoDebilitado());
    }
    public void setHabilidad(Accion habilidad) {
        this.habilidades.add(habilidad);
    }
    public void setEstadisticaPokemon(String estadistica, double valor) {
        this.estadisticas.setEstadistica(estadistica, valor);
    }

    public double getEstadisticas(String estadistica) {
        return this.estadisticas.getEstadistica(estadistica);
    }
    public Tipo getTipo() {
        return this.tipo;
    }
    public String getNombre() {
        return this.nombre;
    }
    public List<Accion> getHabilidades() {
        return this.habilidades;
    }
    public List<Estados> getEstados(){
        List<Estados> estados = new ArrayList<>();
        for (Estado estadoActual : this.estadosNormales.values()) {
            if (estadoActual.estadoEstaActivo()){
                estados.add(estadoActual.getNombre());
            }
        }

        for (Estado estadoActual : this.estadosInhabilitantes.values()) {
            if (estadoActual.estadoEstaActivo()){
                estados.add(estadoActual.getNombre());
            }
        }
        return estados;
    }
    public Integer getId() {
        return this.id;
    }
    public String getHistoria(){
        return this.historia;
    }

    public void activarEstado(Estados estadoNombre) {
        if (estadoNombre == Estados.NORMAL){
            vaciarEstados();
        }

        Estado estado = this.estadosInhabilitantes.get(estadoNombre);
        if (estado == null){
            estado = this.estadosNormales.get(estadoNombre);
        }
        estado.activarEstado();
    }

    public void vaciarEstados() {
        for (Estado estado : this.estadosInhabilitantes.values()) {
            estado.desactivarEstado();
        }
        for (Estado estado : this.estadosNormales.values()) {
            estado.desactivarEstado();
        }
    }

    public void usarHabilidad(CampoDeBatalla campoDeBatalla, Accion habilidadSeleccionada){
        habilidadSeleccionada.usar(campoDeBatalla);
    }

    private void aplicarEstados(Map<Estados, Estado> estados){
        for (Estado estado : estados.values()) {
            if (estado.estadoEstaActivo()){
                estado.aplicarEstado(this);
            }
        }
    }

    public void aplicarEstadosNormales(){
        aplicarEstados(this.estadosNormales);
    }
    public void aplicarEstadosInhabilitantes() {
        aplicarEstados(this.estadosInhabilitantes);
    }

    public Boolean puedePelear(){
        for (Estado estado : this.estadosInhabilitantes.values()) {
            if (estado.estadoEstaActivo() && !estado.estadoPelea()){
                return false;
            }
        }
        return true;
    }

    public Boolean buscarEstadoActual(Estados estado){
        Estado estadoActual = this.estadosInhabilitantes.get(estado);
        if (estadoActual == null){
            estadoActual = this.estadosNormales.get(estado);
        }

        return estadoActual.estadoEstaActivo();
    }

    public void recibirDamage(double damage) {
        this.estadisticas.setEstadistica(VIDA, -damage);
        if (this.getEstadisticas(VIDA) < 1) {
            vaciarEstados();
            activarEstado(Estados.DEBILITADO);
        }
    }

    public Pokemon copiar() {
        Pokemon copia = new Pokemon(this.nombre, this.id, this.estadisticas.copiar(), this.tipo, this.historia);

        for (Accion habilidad : this.habilidades) {
            copia.setHabilidad(habilidad.copiar());
        }

        copia.activarEstado(Estados.NORMAL);

        return copia;
    }
}
