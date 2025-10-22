package com.senasoft.ferias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Artista;

@Repository
public interface Artista_Repository extends JpaRepository<Artista, Long> {
}
