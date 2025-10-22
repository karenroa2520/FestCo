package com.senasoft.ferias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Administrador;

@Repository
public interface Administrador_Repository extends JpaRepository<Administrador, Long> {
}
