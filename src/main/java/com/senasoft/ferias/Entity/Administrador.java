package com.senasoft.ferias.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "administrador")
@Data
public class Administrador {

    @Id
    @Column(name = "num_documento")
    private Long num_documento; 

    @OneToOne
    @JoinColumn(name = "tipo_documento")
    private Tipo_Documento documento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "correo")
    private String correo;

    @Column(name = "contrasena")
    private String contrasena;


}
