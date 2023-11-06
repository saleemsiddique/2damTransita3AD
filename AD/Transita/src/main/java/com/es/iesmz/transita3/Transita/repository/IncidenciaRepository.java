package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.EstadoIncidencia;
import com.es.iesmz.transita3.Transita.domain.Incidencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface IncidenciaRepository extends CrudRepository<Incidencia, Long> {

    Set<Incidencia> findAll();

    Optional<Incidencia> findById(long id);

    Set<Incidencia> findByEstado(EstadoIncidencia estado);

    Set<Incidencia> findByDuracion(String duracion);

    @Query(value = "SELECT * FROM incidencia i WHERE i.cliente_id = :clienteId", nativeQuery = true)
    Set<Incidencia> findByIncidenciaByClienteId(@Param("clienteId") Long clienteId);


}