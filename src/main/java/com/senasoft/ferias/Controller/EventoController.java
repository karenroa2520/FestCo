package com.senasoft.ferias.Controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senasoft.ferias.Entity.Evento;
import com.senasoft.ferias.service.Evento_Service;


@Controller
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private Evento_Service eventoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoService.save(evento);
    }

    @GetMapping
    public List<Evento> getAllEventos() {
        return eventoService.getAll();
    }
}