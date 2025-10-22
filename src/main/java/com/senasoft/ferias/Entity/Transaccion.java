package com.senasoft.ferias.Entity;

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
@Table(name = "transaccion")
@Data
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "banco")
    private String banco;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "num_transaccion")
    private String numTransaccion;

    @ManyToOne
    @JoinColumn(name = "id_reserva")
    private Reservas reserva;

    @OneToMany(mappedBy = "transaccion")
    private List<Compra> compras;
}
