package com.senasoft.ferias.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Municipio;

@Repository
public interface Municipio_Repository extends JpaRepository<Municipio, Long> {

}
