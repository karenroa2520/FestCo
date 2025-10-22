package com.senasoft.ferias.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "localidad")
@Data
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "localidada")
    private String localidad;

    @OneToMany(mappedBy = "localidad")
    private List<Cantidad_Boletas> cantidadBoletas;

    @OneToMany(mappedBy = "localidad")
    private List<Boletas> boletas;
}
