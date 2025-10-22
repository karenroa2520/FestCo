package com.senasoft.ferias.Entity;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="evento")
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="fecha_inicio", columnDefinition = "date")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", columnDefinition = "date")
    private LocalDate fechaFin; 

    @Column(name="hora_inicio")
    private Time horaInicio;

    @Column(name="hora_fin")
    private Time horaFin;
    
    @ManyToOne
    @JoinColumn(name = "id_administrador", referencedColumnName = "num_documento")
    private Administrador administrador;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @OneToMany(mappedBy = "evento")
    private List<Artista> artistas;

    @OneToMany(mappedBy = "evento")
    private List<Cantidad_Boletas> cantidadBoletas;

    @OneToMany(mappedBy = "evento")
    private List<Boletas> boletas;

    @OneToMany(mappedBy = "evento")
    private List<Reservas> reservas;
}