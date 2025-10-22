package com.senasoft.ferias.Controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.senasoft.ferias.Controller.EventoCompletoRequest.CantidadBoletasRequest;
import com.senasoft.ferias.Entity.Administrador;
import com.senasoft.ferias.Entity.Artista;
import com.senasoft.ferias.Entity.Cantidad_Boletas;
import com.senasoft.ferias.Entity.Departamento;
import com.senasoft.ferias.Entity.Evento;
import com.senasoft.ferias.Entity.Localidad;
import com.senasoft.ferias.Entity.Municipio;
import com.senasoft.ferias.Repository.Administrador_Repository;
import com.senasoft.ferias.service.Evento_Service;

@Controller
public class AdministradorCompletoController {

    @Autowired
    private Evento_Service eventoCompletoService;
    
    @Autowired
    private Administrador_Repository administradorRepository;


    @GetMapping("/usuario")
    public String usuario() {
        return "usuario/usuario";
    }

    @GetMapping("/administrador")
    public String administradorPage(Model model) {
        model.addAttribute("departamentos", eventoCompletoService.getAllDepartamentos());
        model.addAttribute("localidades", eventoCompletoService.getAllLocalidades());
        model.addAttribute("artistas", eventoCompletoService.getAllArtistas());
        return "administrador/administrador";
    }



    @PostMapping("/eventos/completo")
    @ResponseBody
    public ResponseEntity<String> crearEventoCompleto(@RequestBody EventoCompletoRequest request) {
        try {
            // Crear el evento
            Evento evento = new Evento();
            evento.setNombre(request.getNombreEvento());
            evento.setDescripcion(request.getDescripcionEvento());
            evento.setFechaInicio(LocalDate.parse(request.getFechaInicio()));
            evento.setFechaFin(LocalDate.parse(request.getFechaFin()));
            evento.setHoraInicio(Time.valueOf(LocalTime.parse(request.getHoraInicio())));
            evento.setHoraFin(Time.valueOf(LocalTime.parse(request.getHoraFin())));

            // Asociar municipio
            Optional<Municipio> municipio = eventoCompletoService.getMunicipioById(request.getMunicipioId());
            if (!municipio.isPresent()) {
                return ResponseEntity.badRequest().body("Municipio no encontrado");
            }
            evento.setMunicipio(municipio.get());

            // Asociar administrador por defecto
            Long defaultAdminId = 1024480167L;
            Optional<Administrador> admin = administradorRepository.findById(defaultAdminId);
            if (!admin.isPresent()) {
                return ResponseEntity.badRequest().body("Administrador por defecto no encontrado");
            }
            evento.setAdministrador(admin.get());

            // Crear cantidades de boletas
            List<Cantidad_Boletas> cantidadBoletas = new ArrayList<>();
            if (request.getLocalidades() != null && request.getCantidadBoletas() != null) {
                for (int i = 0; i < request.getLocalidades().size(); i++) {
                    CantidadBoletasRequest boletaReq = request.getCantidadBoletas().get(i);
                    String nombreLocalidad = request.getLocalidades().get(i).getLocalidad();

                    Cantidad_Boletas cantidad = new Cantidad_Boletas();
                    cantidad.setCantidad(boletaReq.getCantidad());
                    cantidad.setValor(boletaReq.getPrecio());

                    // Buscar o crear localidad
                    Optional<Localidad> localidad = eventoCompletoService.getAllLocalidades().stream()
                            .filter(l -> l.getLocalidad().equalsIgnoreCase(nombreLocalidad))
                            .findFirst();
                    
                    if (localidad.isPresent()) {
                        cantidad.setLocalidad(localidad.get());
                    } else {
                        Localidad nuevaLocalidad = new Localidad();
                        nuevaLocalidad.setLocalidad(nombreLocalidad);
                        Localidad localidadGuardada = eventoCompletoService.saveLocalidad(nuevaLocalidad);
                        cantidad.setLocalidad(localidadGuardada);
                    }
                    cantidadBoletas.add(cantidad);
                }
            }

            // Obtener artistas seleccionados
            List<Artista> artistas = new ArrayList<>();
            if (request.getArtistas() != null) {
                for (Long artistaId : request.getArtistas()) {
                    Optional<Artista> artista = eventoCompletoService.getArtistaById(artistaId);
                    artista.ifPresent(artistas::add);
                }
            }

            // Guardar evento completo
            eventoCompletoService.saveEventoCompleto(evento, cantidadBoletas, artistas);

            return ResponseEntity.ok("Evento creado con éxito");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear el evento: " + e.getMessage());
        }
    }
}
