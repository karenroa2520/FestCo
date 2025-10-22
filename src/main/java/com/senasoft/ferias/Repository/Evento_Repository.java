package com.senasoft.ferias.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Evento;

@Repository
public interface Evento_Repository extends JpaRepository<Evento, Long> {

}
