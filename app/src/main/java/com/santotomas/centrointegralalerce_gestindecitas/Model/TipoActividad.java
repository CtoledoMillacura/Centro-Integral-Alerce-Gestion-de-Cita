package com.santotomas.centrointegralalerce_gestindecitas.Model;

public class TipoActividad {
    private String id;
    private String nombre;
    private String descripcion;

    // Constructor vacío requerido para Firebase
    public TipoActividad() {
    }

    // Constructor
    public TipoActividad(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
