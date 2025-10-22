package com.senasoft.ferias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Localidad;

@Repository
public interface Localidad_Repository extends JpaRepository<Localidad, Long> {
}
