package com.senasoft.ferias.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.senasoft.ferias.Entity.Evento;
import com.senasoft.ferias.service.EventoCompleto_Service;

@Controller
public class IndexController {

    @Autowired
    private EventoCompleto_Service eventoCompletoService;

    @GetMapping("/")
    public String index(Model model) {
        List<Evento> eventos = eventoCompletoService.getAllEventos();
        model.addAttribute("eventos", eventos);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "usuario/usuario";
    }
}
