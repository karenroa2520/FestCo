package com.senasoft.ferias.ServiceImpl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senasoft.ferias.Entity.Evento;
import com.senasoft.ferias.Repository.Evento_Repository;
import com.senasoft.ferias.service.Evento_Service;

@Service
public class Evento_ServiceImpl implements Evento_Service {

    @Autowired
    private Evento_Repository eventoRepository;

    @Override
    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }
}
