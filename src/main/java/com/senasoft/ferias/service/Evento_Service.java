package com.senasoft.ferias.service;

import java.util.List;

import com.senasoft.ferias.Entity.Evento;

public interface Evento_Service {
    Evento save(Evento evento);
    List<Evento> getAll();
}

