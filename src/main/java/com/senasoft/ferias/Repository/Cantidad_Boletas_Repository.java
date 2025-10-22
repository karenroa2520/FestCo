package com.senasoft.ferias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Cantidad_Boletas;

@Repository
public interface Cantidad_Boletas_Repository extends JpaRepository<Cantidad_Boletas, Long> {
}
