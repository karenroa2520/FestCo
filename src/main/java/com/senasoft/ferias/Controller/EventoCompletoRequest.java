package com.senasoft.ferias.Controller;

import java.util.List;

public class EventoCompletoRequest {
    private String nombreEvento;
    private String descripcionEvento;
    private Long municipioId;
    private String fechaInicio;
    private String horaInicio;
    private String fechaFin;
    private String horaFin;
    private Long administradorId;
    private List<LocalidadRequest> localidades;
    private List<Long> artistas;
    private List<CantidadBoletasRequest> cantidadBoletas;

    // Getters and setters

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Long getAdministradorId() {
        return administradorId;
    }

    public void setAdministradorId(Long administradorId) {
        this.administradorId = administradorId;
    }

    public List<LocalidadRequest> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<LocalidadRequest> localidades) {
        this.localidades = localidades;
    }

    public List<Long> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<Long> artistas) {
        this.artistas = artistas;
    }

    public List<CantidadBoletasRequest> getCantidadBoletas() {
        return cantidadBoletas;
    }

    public void setCantidadBoletas(List<CantidadBoletasRequest> cantidadBoletas) {
        this.cantidadBoletas = cantidadBoletas;
    }

    public static class LocalidadRequest {
        private String localidad;

        public String getLocalidad() {
            return localidad;
        }

        public void setLocalidad(String localidad) {
            this.localidad = localidad;
        }
    }

    public static class CantidadBoletasRequest {
        private double precio;
        private int cantidad;

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }
}
