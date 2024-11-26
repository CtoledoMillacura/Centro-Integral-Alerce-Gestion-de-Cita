package com.santotomas.centrointegralalerce_gestindecitas.Model;

public class Actividad {
    private String idActividad;
    private String nombre;
    private String periodicidad;
    private String fecha;
    private String hora;
    private String idLugar;         // Id del lugar
    private String idOferente;      // Id del oferente
    private String idProyecto;      // Id del proyecto
    private String idSocioComunitario; // Id del socio comunitario
    private String idTipoActividad; // Id del tipo de actividad

    // Constructor vacío requerido para Firebase
    public Actividad() {
    }

    // Constructor
    public Actividad(String idActividad, String nombre, String periodicidad, String fecha, String hora,
                     String idLugar, String idOferente, String idProyecto, String idSocioComunitario, String idTipoActividad) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.periodicidad = periodicidad;
        this.fecha = fecha;
        this.hora = hora;
        this.idLugar = idLugar;
        this.idOferente = idOferente;
        this.idProyecto = idProyecto;
        this.idSocioComunitario = idSocioComunitario;
        this.idTipoActividad = idTipoActividad;
    }

    // Getters y setters
    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(String idLugar) {
        this.idLugar = idLugar;
    }

    public String getIdOferente() {
        return idOferente;
    }

    public void setIdOferente(String idOferente) {
        this.idOferente = idOferente;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getIdSocioComunitario() {
        return idSocioComunitario;
    }

    public void setIdSocioComunitario(String idSocioComunitario) {
        this.idSocioComunitario = idSocioComunitario;
    }

    public String getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(String idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }
}
