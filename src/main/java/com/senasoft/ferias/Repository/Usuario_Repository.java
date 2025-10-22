package com.senasoft.ferias.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senasoft.ferias.Entity.Usuario;

@Repository
public interface Usuario_Repository extends JpaRepository<Usuario,Long> {

}
