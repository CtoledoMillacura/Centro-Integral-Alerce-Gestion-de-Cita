package com.santotomas.centrointegralalerce_gestindecitas.Model;

public class SocioComunitario {
    private String id;
    private String nombre;

    // Constructor vacío requerido para Firebase
    public SocioComunitario() {
    }

    // Constructor
    public SocioComunitario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}