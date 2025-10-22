package com.senasoft.ferias.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senasoft.ferias.Entity.Artista;
import com.senasoft.ferias.Entity.Cantidad_Boletas;
import com.senasoft.ferias.Entity.Departamento;
import com.senasoft.ferias.Entity.Evento;
import com.senasoft.ferias.Entity.Localidad;
import com.senasoft.ferias.Entity.Municipio;
import com.senasoft.ferias.Repository.Artista_Repository;
import com.senasoft.ferias.Repository.Cantidad_Boletas_Repository;
import com.senasoft.ferias.Repository.Departamento_Repository;
import com.senasoft.ferias.Repository.Evento_Repository;
import com.senasoft.ferias.Repository.Localidad_Repository;
import com.senasoft.ferias.Repository.Municipio_Repository;
import com.senasoft.ferias.service.EventoCompleto_Service;

@Service
@Transactional
public class EventoCompleto_ServiceImpl implements EventoCompleto_Service {

    @Autowired
    private Departamento_Repository departamentoRepository;
    
    @Autowired
    private Municipio_Repository municipioRepository;
    
    @Autowired
    private Localidad_Repository localidadRepository;
    
    @Autowired
    private Artista_Repository artistaRepository;
    
    @Autowired
    private Evento_Repository eventoRepository;
    
    @Autowired
    private Cantidad_Boletas_Repository cantidadBoletasRepository;

    @Override
    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    @Override
    public Optional<Departamento> getDepartamentoById(Long id) {
        return departamentoRepository.findById(id);
    }

    @Override
    public List<Municipio> getAllMunicipios() {
        return municipioRepository.findAll();
    }

    @Override
    public List<Municipio> getMunicipiosByDepartamento(Long departamentoId) {
        return municipioRepository.findByDepartamentoId(departamentoId);
    }

    @Override
    public Optional<Municipio> getMunicipioById(Long id) {
        return municipioRepository.findById(id);
    }

    @Override
    public List<Localidad> getAllLocalidades() {
        return localidadRepository.findAll();
    }

    @Override
    public Localidad saveLocalidad(Localidad localidad) {
        return localidadRepository.save(localidad);
    }

    @Override
    public Optional<Localidad> getLocalidadById(Long id) {
        return localidadRepository.findById(id);
    }

    @Override
    public List<Artista> getAllArtistas() {
        return artistaRepository.findAll();
    }

    @Override
    public Artista saveArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    @Override
    public Optional<Artista> getArtistaById(Long id) {
        return artistaRepository.findById(id);
    }

    @Override
    public boolean validarHorarioArtista(Long artistaId, LocalDate fechaInicio, LocalDate fechaFin, 
                                        LocalTime horaInicio, LocalTime horaFin) {
        List<Artista> artistas = artistaRepository.findAll();
        
        for (Artista artista : artistas) {
            if (artista.getId().equals(artistaId)) {
                Evento evento = artista.getEvento();
                if (evento != null) {
                    // Verificar si hay solapamiento de fechas
                    if (evento.getFechaInicio().isBefore(fechaFin) && evento.getFechaFin().isAfter(fechaInicio)) {
                        // Si hay solapamiento de fechas, verificar horarios
                        if (evento.getHoraInicio().toLocalTime().isBefore(horaFin) && 
                            evento.getHoraFin().toLocalTime().isAfter(horaInicio)) {
                            return false; // Hay conflicto de horario
                        }
                    }
                }
            }
        }
        return true; // No hay conflicto
    }

    @Override
    @Transactional
    public Evento saveEventoCompleto(Evento evento, List<Cantidad_Boletas> cantidadBoletas, 
                                     List<Artista> artistas) {
        // Guardar el evento primero
        Evento eventoGuardado = eventoRepository.save(evento);
        
        // Asociar y guardar las cantidades de boletas
        for (Cantidad_Boletas cantidad : cantidadBoletas) {
            cantidad.setEvento(eventoGuardado);
            cantidadBoletasRepository.save(cantidad);
        }
        
        // Asociar y guardar los artistas
        for (Artista artista : artistas) {
            artista.setEvento(eventoGuardado);
            artistaRepository.save(artista);
        }
        
        return eventoGuardado;
    }

    @Override
    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }

    @Override
    public Optional<Evento> getEventoById(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    @Override
    public List<Cantidad_Boletas> getAllCantidadBoletas() {
        return cantidadBoletasRepository.findAll();
    }

    @Override
    public Cantidad_Boletas saveCantidadBoletas(Cantidad_Boletas cantidadBoletas) {
        return cantidadBoletasRepository.save(cantidadBoletas);
    }

    @Override
    public List<Cantidad_Boletas> getCantidadBoletasByEvento(Long eventoId) {
        return cantidadBoletasRepository.findAll().stream()
                .filter(cb -> cb.getEvento() != null && cb.getEvento().getId().equals(eventoId))
                .collect(Collectors.toList());
    }
}
