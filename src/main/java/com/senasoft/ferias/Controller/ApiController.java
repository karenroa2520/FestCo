package com.senasoft.ferias.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senasoft.ferias.Entity.Artista;
import com.senasoft.ferias.Entity.Departamento;
import com.senasoft.ferias.Entity.Localidad;
import com.senasoft.ferias.Entity.Municipio;
import com.senasoft.ferias.service.EventoCompleto_Service;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private EventoCompleto_Service eventoCompletoService;

    @GetMapping("/departamentos")
    public List<Departamento> getAllDepartamentos() {
        return eventoCompletoService.getAllDepartamentos();
    }

    @GetMapping("/municipios")
    public List<Municipio> getMunicipiosByDepartamento(@RequestParam Long departamentoId) {
        return eventoCompletoService.getMunicipiosByDepartamento(departamentoId);
    }

    @GetMapping("/localidades")
    public List<Localidad> getAllLocalidades() {
        return eventoCompletoService.getAllLocalidades();
    }

    @GetMapping("/artistas")
    public List<Artista> getAllArtistas() {
        return eventoCompletoService.getAllArtistas();
    }

    @PostMapping("/localidad")
    public Localidad crearLocalidad(@RequestBody LocalidadRequest request) {
        Localidad localidad = new Localidad();
        localidad.setLocalidad(request.getNombre());
        return eventoCompletoService.saveLocalidad(localidad);
    }

    @PostMapping("/artista")
    public Artista crearArtista(@RequestBody ArtistaRequest request) {
        Artista artista = new Artista();
        artista.setNombre(request.getNombre());
        artista.setGeneroMusical(request.getGeneroMusical());
        artista.setCiudadNatal(request.getCiudadNatal());
        return eventoCompletoService.saveArtista(artista);
    }

    // Clases internas para requests
    public static class LocalidadRequest {
        private String nombre;
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }

    public static class ArtistaRequest {
        private String nombre;
        private String generoMusical;
        private String ciudadNatal;
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getGeneroMusical() { return generoMusical; }
        public void setGeneroMusical(String generoMusical) { this.generoMusical = generoMusical; }
        
        public String getCiudadNatal() { return ciudadNatal; }
        public void setCiudadNatal(String ciudadNatal) { this.ciudadNatal = ciudadNatal; }
    }
}
