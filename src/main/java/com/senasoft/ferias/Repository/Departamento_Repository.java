package com.senasoft.ferias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Departamento;

@Repository
public interface Departamento_Repository extends JpaRepository<Departamento, Long> {
}
