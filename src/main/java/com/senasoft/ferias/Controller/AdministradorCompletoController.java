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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/")
    public String index() {
        return "index";
    }

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
    public String crearEventoCompleto(
            @RequestParam String nombreEvento,
            @RequestParam String descripcionEvento,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam String horaInicio,
            @RequestParam String horaFin,
            @RequestParam Long municipioId,
            @RequestParam Long administradorId,
            @RequestParam(required = false) String[] nombresLocalidades,
            @RequestParam(required = false) Double[] preciosLocalidades,
            @RequestParam(required = false) Integer[] cantidadesLocalidades,
            @RequestParam(required = false) Long[] artistasIds) {

        try {
            System.out.println("=== DEBUG: Creando evento ===");
            System.out.println("Nombre: " + nombreEvento);
            System.out.println("Nombres localidades: " + (nombresLocalidades != null ? java.util.Arrays.toString(nombresLocalidades) : "null"));
            System.out.println("Precios localidades: " + (preciosLocalidades != null ? java.util.Arrays.toString(preciosLocalidades) : "null"));
            System.out.println("Cantidades localidades: " + (cantidadesLocalidades != null ? java.util.Arrays.toString(cantidadesLocalidades) : "null"));
            System.out.println("Artistas IDs: " + (artistasIds != null ? java.util.Arrays.toString(artistasIds) : "null"));
            
            // Crear el evento
            Evento evento = new Evento();
            evento.setNombre(nombreEvento);
            evento.setDescripcion(descripcionEvento);
            evento.setFechaInicio(LocalDate.parse(fechaInicio));
            evento.setFechaFin(LocalDate.parse(fechaFin));
            evento.setHoraInicio(Time.valueOf(LocalTime.parse(horaInicio)));
            evento.setHoraFin(Time.valueOf(LocalTime.parse(horaFin)));

            // Asociar municipio
            Optional<Municipio> municipio = eventoCompletoService.getMunicipioById(municipioId);
            municipio.ifPresent(evento::setMunicipio);

            // Asociar administrador
            Optional<Administrador> admin = administradorRepository.findById(administradorId);
            admin.ifPresent(evento::setAdministrador);

            // Crear cantidades de boletas
            List<Cantidad_Boletas> cantidadBoletas = new ArrayList<>();
            if (nombresLocalidades != null && preciosLocalidades != null && cantidadesLocalidades != null) {
                System.out.println("Procesando " + nombresLocalidades.length + " localidades");
                for (int i = 0; i < nombresLocalidades.length; i++) {
                    Cantidad_Boletas cantidad = new Cantidad_Boletas();
                    cantidad.setCantidad(cantidadesLocalidades[i]);
                    cantidad.setValor(preciosLocalidades[i]);
                    
                    // Buscar o crear localidad
                    final String nombreLocalidad = nombresLocalidades[i];
                    System.out.println("Procesando localidad: " + nombreLocalidad);
                    Optional<Localidad> localidad = eventoCompletoService.getAllLocalidades().stream()
                            .filter(l -> l.getLocalidad().equals(nombreLocalidad))
                            .findFirst();
                    
                    if (localidad.isPresent()) {
                        System.out.println("Localidad encontrada: " + localidad.get().getId());
                        cantidad.setLocalidad(localidad.get());
                    } else {
                        System.out.println("Creando nueva localidad: " + nombreLocalidad);
                        Localidad nuevaLocalidad = new Localidad();
                        nuevaLocalidad.setLocalidad(nombreLocalidad);
                        Localidad localidadGuardada = eventoCompletoService.saveLocalidad(nuevaLocalidad);
                        cantidad.setLocalidad(localidadGuardada);
                    }
                    
                    cantidadBoletas.add(cantidad);
                }
            } else {
                System.out.println("No hay localidades para procesar");
            }

            // Obtener artistas seleccionados
            List<Artista> artistas = new ArrayList<>();
            if (artistasIds != null) {
                for (Long artistaId : artistasIds) {
                    Optional<Artista> artista = eventoCompletoService.getArtistaById(artistaId);
                    artista.ifPresent(artistas::add);
                }
            }

            // Validar horarios de artistas
            boolean horariosValidos = true;
            for (Artista artista : artistas) {
                if (!eventoCompletoService.validarHorarioArtista(
                        artista.getId(), 
                        evento.getFechaInicio(), 
                        evento.getFechaFin(),
                        evento.getHoraInicio().toLocalTime(), 
                        evento.getHoraFin().toLocalTime())) {
                    horariosValidos = false;
                    break;
                }
            }

            if (!horariosValidos) {
                return "redirect:/administrador?error=conflicto_horario";
            }

            // Guardar evento completo
            eventoCompletoService.saveEventoCompleto(evento, cantidadBoletas, artistas);

            return "redirect:/administrador?success=evento_creado";

        } catch (Exception e) {
            return "redirect:/administrador?error=error_general";
        }
    }
}
