package com.api.centrointegralalerce_gestindecita.Model;

public class Lugar {
    private String id;
    private String nombre;
    private Integer cupo; // Opcional

    // Constructor vacío requerido para Firebase
    public Lugar() {
    }

    // Constructor
    public Lugar(String id, String nombre, Integer cupo) {
        this.id = id;
        this.nombre = nombre;
        this.cupo = cupo;
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

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }
}
