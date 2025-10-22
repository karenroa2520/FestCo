package com.senasoft.ferias.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @Column(name = "num_documento")
    private Long numDocumento;

    @ManyToOne
    @JoinColumn(name = "tipo_documento")
    private Tipo_Documento tipoDocumento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "correo")
    private String correo;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "num_telefonico")
    private Long numTelefonico;

    @OneToMany(mappedBy = "usuario")
    private List<Reservas> reservas;
}
