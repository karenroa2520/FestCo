package com.senasoft.ferias.Controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senasoft.ferias.Entity.Administrador;
import com.senasoft.ferias.Entity.Evento;
import com.senasoft.ferias.Entity.Municipio;
import com.senasoft.ferias.Repository.Administrador_Repository;
import com.senasoft.ferias.Repository.Evento_Repository;
import com.senasoft.ferias.Repository.Municipio_Repository;



@Controller
public class WebEventoController {

    private final Evento_Repository eventoRepository;
    private final Municipio_Repository municipioRepository;
    private final Administrador_Repository administradorRepository;

    public WebEventoController(Evento_Repository eventoRepository,
            Municipio_Repository municipioRepository,
            Administrador_Repository administradorRepository) {
        this.eventoRepository = eventoRepository;
        this.municipioRepository = municipioRepository;
        this.administradorRepository = administradorRepository;
    }


    @GetMapping("/eventos/{id}/editar")
    public String editarEventoForm(@PathVariable Long id, Model model) {
        Optional<Evento> oe = eventoRepository.findById(id);
        if (oe.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("evento", oe.get());
        model.addAttribute("municipios", municipioRepository.findAll());
        return "edit"; // requiere template edit.html (simple form similar a index)
    }

    @PostMapping("/eventos")
    public String crearEvento(
        @RequestParam String nombre,
        @RequestParam(required = false) String descripcion,
        @RequestParam(required = false, name = "fechaInicio") String fechaInicio,
        @RequestParam(required = false, name = "fechaFin") String fechaFin,
        @RequestParam(required = false, name = "horaInicio") String horaInicio,
        @RequestParam(required = false, name = "horaFin") String horaFin,
        @RequestParam(required = false, name = "municipioId") Long municipioId,
        @RequestParam(name = "administradorId") Long administradorId
    ) {
        Evento evento = new Evento();
        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);

        if (fechaInicio != null && !fechaInicio.isEmpty()) {
            evento.setFechaInicio(LocalDate.parse(fechaInicio));
        }

        if (fechaFin != null && !fechaFin.isEmpty()) {
            evento.setFechaFin(LocalDate.parse(fechaFin));
        }

        if (horaInicio != null && !horaInicio.isEmpty()) {
            evento.setHoraInicio(Time.valueOf(LocalTime.parse(horaInicio).toString() + ":00"));
        }

        if (horaFin != null && !horaFin.isEmpty()) {
            evento.setHoraFin(Time.valueOf(LocalTime.parse(horaFin).toString() + ":00"));
        }

        if (municipioId != null) {
            Optional<Municipio> municipio = municipioRepository.findById(municipioId);
            municipio.ifPresent(evento::setMunicipio);
        }

        Optional<Administrador> admin = administradorRepository.findById(administradorId);
        admin.ifPresent(evento::setAdministrador);
        
        eventoRepository.save(evento);
        return "redirect:/";
    }

    @PostMapping("/eventos/{id}/actualizar")
    public String actualizarEvento(
        @PathVariable Long id,
        @RequestParam String nombre,
        @RequestParam(required = false) String descripcion,
        @RequestParam(required = false, name = "fechaInicio") String fechaInicio,
        @RequestParam(required = false, name = "fechaFin") String fechaFin,
        @RequestParam(required = false, name = "horaInicio") String horaInicio,
        @RequestParam(required = false, name = "horaFin") String horaFin,
        @RequestParam(required = false, name = "municipioId") Long municipioId,
        @RequestParam(name = "administradorId") Long administradorId
    ) {
        Optional<Evento> oe = eventoRepository.findById(id);
        if (oe.isEmpty()) return "redirect:/";
        Evento evento = oe.get();
        evento.setNombre(nombre);
        evento.setDescripcion(descripcion);
        if (fechaInicio != null && !fechaInicio.isEmpty()) evento.setFechaInicio(LocalDate.parse(fechaInicio));
        if (fechaFin != null && !fechaFin.isEmpty()) evento.setFechaFin(LocalDate.parse(fechaFin));
        if (horaInicio != null && !horaInicio.isEmpty()) evento.setHoraInicio(Time.valueOf(LocalTime.parse(horaInicio).toString() + ":00"));
        if (horaFin != null && !horaFin.isEmpty()) evento.setHoraFin(Time.valueOf(LocalTime.parse(horaFin).toString() + ":00"));
        if (municipioId != null) {
            municipioRepository.findById(municipioId).ifPresent(evento::setMunicipio);
        }
        administradorRepository.findById(administradorId).ifPresent(evento::setAdministrador);
        eventoRepository.save(evento);
        return "redirect:/";
    }

    @PostMapping("/eventos/{id}/eliminar")
    public String eliminarEvento(@PathVariable Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
        }
        return "redirect:/";
    }
}
