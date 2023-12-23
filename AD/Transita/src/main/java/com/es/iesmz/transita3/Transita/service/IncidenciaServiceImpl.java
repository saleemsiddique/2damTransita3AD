package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.EstadoIncidencia;
import com.es.iesmz.transita3.Transita.domain.Incidencia;
import com.es.iesmz.transita3.Transita.exception.IncidenciaNotFoundException;
import com.es.iesmz.transita3.Transita.repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
@Service
public class IncidenciaServiceImpl implements IncidenciaService{
    @Autowired
    private IncidenciaRepository incidenciaRepository;
    @Override
    public Set<Incidencia> findAll() {
        return incidenciaRepository.findAll();
    }

    @Override
    public Optional<Incidencia> findById(long id) {
        return incidenciaRepository.findById(id);
    }

    @Override
    public Set<Incidencia> findByIncidenciaByClienteId(long clienteId) {
        return incidenciaRepository.findByIncidenciaByClienteId(clienteId);
    }

    @Override
    public Set<Incidencia> findIncidenciaByPagesFiltro(String estado, int idInicial, int idFinal) {
        return incidenciaRepository.findIncidenciaConFiltros(estado, idInicial, idFinal);
    }

    @Override
    public Set<Incidencia> findAllIncidenciasByPages(int idInicial, int idFinal) {
        return incidenciaRepository.findAllByPages(idInicial, idFinal);
    }

    @Override
    public Set<Incidencia> findByPunto(long id) {
        return incidenciaRepository.findByIncidenciaByPuntoId(id);
    }

    @Override
    public long countIncidencia(String estado) {
        return incidenciaRepository.countPuntoConFiltros(estado);
    }

    @Override
    public long count(){
        return incidenciaRepository.count();
    }

    @Override
    public Set<Incidencia> findByDuracion(String duracion) {
        return incidenciaRepository.findByDuracion(duracion);
    }

    @Override
    public Set<Incidencia> findByEstado(EstadoIncidencia estado) {
        return incidenciaRepository.findByEstado(estado);
    }

    @Override
    public Incidencia addIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    public Incidencia modifyIncidencia(long id, Incidencia nuevaIncidencia) {
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new IncidenciaNotFoundException(id));
        nuevaIncidencia.setId(incidencia.getId());
        return incidenciaRepository.save(nuevaIncidencia);
    }

    @Override
    public void deleteIncidencia(Long id) {
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new IncidenciaNotFoundException(id));
        incidenciaRepository.deleteById(id);
    }

}
