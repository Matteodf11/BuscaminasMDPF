/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.buscaminasmdpf;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author matdipfor
 */
public class Persona {

    private final StringProperty Nombre = new SimpleStringProperty();
    private final StringProperty pathImagen = new SimpleStringProperty();
    private final IntegerProperty tiempo = new SimpleIntegerProperty();

    public Persona(String nombre, String imagen) {
        this.Nombre.setValue(nombre);
        this.pathImagen.setValue(imagen);
    }

    public Persona(String nombre, Integer tiempo) {
        this.Nombre.setValue(nombre);

        this.tiempo.setValue(tiempo);
    }

    public final StringProperty NombreProperty() {
        return this.Nombre;
    }

    public final String getNombre() {
        return this.NombreProperty().get();
    }

    public final void setNombre(final java.lang.String Nombre) {
        this.NombreProperty().set(Nombre);
    }

    public final StringProperty pathImagenProperty() {
        return this.pathImagen;
    }

    public StringProperty getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagenProperty().set(pathImagen);
    }

    public final String getImagen() {
        return this.pathImagenProperty().get();
    }

    /**
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo.get();
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(int tiempo) {
        this.tiempo.set(tiempo);
    }

    public IntegerProperty tiempoProperty() {
        return tiempo;
    }

}
