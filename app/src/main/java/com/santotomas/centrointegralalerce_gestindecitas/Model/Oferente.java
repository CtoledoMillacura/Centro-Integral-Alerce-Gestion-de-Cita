package com.santotomas.centrointegralalerce_gestindecitas.Model;

public class Oferente {
    private String id;
    private String nombre;
    private String docenteResponsable;
    private String carrera;

    public Oferente() {
        // Constructor vacío necesario para Firebase
    }

    public Oferente(String id, String nombre, String docenteResponsable, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.docenteResponsable = docenteResponsable;
        this.carrera = carrera;
    }

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

    public String getDocenteResponsable() {
        return docenteResponsable;
    }

    public void setDocenteResponsable(String docenteResponsable) {
        this.docenteResponsable = docenteResponsable;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}
