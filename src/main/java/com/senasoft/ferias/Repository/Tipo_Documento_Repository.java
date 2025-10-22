package com.senasoft.ferias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Tipo_Documento;

@Repository
public interface Tipo_Documento_Repository extends JpaRepository<Tipo_Documento, Long> {

}
