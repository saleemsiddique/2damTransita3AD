package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.EstadoIncidencia;
import com.es.iesmz.transita3.Transita.domain.Incidencia;

import java.util.Optional;
import java.util.Set;

public interface  IncidenciaService {
    Set<Incidencia> findAll();

    Optional<Incidencia> findById(long id);

    Set<Incidencia> findByEstado(EstadoIncidencia estado);

    Set<Incidencia> findByDuracion(String duracion);

    Incidencia addIncidencia(Incidencia incidencia);

    Incidencia modifyIncidencia(long id, Incidencia nuevaIncidencia);

    void deleteIncidencia(Long id);

    Set<Incidencia> findByIncidenciaByClienteId(long clienteId);
    Set<Incidencia> findIncidenciaByPagesFiltro(String estado, int idInicial, int idFinal);
    Set<Incidencia> findAllIncidenciasByPages(int idInicial, int idFinal);

    long countIncidencia(String estado);
    long count();

}
