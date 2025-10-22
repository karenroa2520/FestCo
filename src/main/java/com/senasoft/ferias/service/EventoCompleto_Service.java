package com.senasoft.ferias.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.senasoft.ferias.Entity.Artista;
import com.senasoft.ferias.Entity.Cantidad_Boletas;
import com.senasoft.ferias.Entity.Departamento;
import com.senasoft.ferias.Entity.Evento;
import com.senasoft.ferias.Entity.Localidad;
import com.senasoft.ferias.Entity.Municipio;

public interface EventoCompleto_Service {
    
    // Gestión de Departamentos
    List<Departamento> getAllDepartamentos();
    Optional<Departamento> getDepartamentoById(Long id);
    
    // Gestión de Municipios
    List<Municipio> getAllMunicipios();
    List<Municipio> getMunicipiosByDepartamento(Long departamentoId);
    Optional<Municipio> getMunicipioById(Long id);
    
    // Gestión de Localidades
    List<Localidad> getAllLocalidades();
    Localidad saveLocalidad(Localidad localidad);
    Optional<Localidad> getLocalidadById(Long id);
    
    // Gestión de Artistas
    List<Artista> getAllArtistas();
    Artista saveArtista(Artista artista);
    Optional<Artista> getArtistaById(Long id);
    boolean validarHorarioArtista(Long artistaId, LocalDate fechaInicio, LocalDate fechaFin, 
                                 LocalTime horaInicio, LocalTime horaFin);
    
    // Gestión de Eventos
    Evento saveEventoCompleto(Evento evento, List<Cantidad_Boletas> cantidadBoletas, 
                             List<Artista> artistas);
    List<Evento> getAllEventos();
    Optional<Evento> getEventoById(Long id);
    void deleteEvento(Long id);
    
    // Gestión de Cantidad de Boletas
    List<Cantidad_Boletas> getAllCantidadBoletas();
    Cantidad_Boletas saveCantidadBoletas(Cantidad_Boletas cantidadBoletas);
    List<Cantidad_Boletas> getCantidadBoletasByEvento(Long eventoId);
}
