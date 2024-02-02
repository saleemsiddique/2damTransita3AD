package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.EstadoIncidencia;
import com.es.iesmz.transita3.Transita.domain.Incidencia;
import com.es.iesmz.transita3.Transita.domain.Punto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IncidenciaRepository extends CrudRepository<Incidencia, Long> {
    String atributos = " I.ID, I.DESCRIPCION, I.DURACION, I.ESTADO, I.FECHA_HORA, I.FOTOS, I.CLIENTE_ID, I.PUNTO_ID ";
    String atributosRank = " RankedPoints.ID, RankedPoints.DESCRIPCION, RankedPoints.DURACION, RankedPoints.ESTADO," +
            " RankedPoints.FECHA_HORA, RankedPoints.FOTOS, RankedPoints.CLIENTE_ID, RankedPoints.PUNTO_ID ";

    Set<Incidencia> findAll();

    Optional<Incidencia> findById(long id);

    Set<Incidencia> findByEstado(EstadoIncidencia estado);

    Set<Incidencia> findByDuracion(String duracion);

    @Query(value = "SELECT"+atributos+"FROM incidencia i WHERE i.punto_id = :puntoId", nativeQuery = true)
    Set<Incidencia> findByIncidenciaByPuntoId(@Param("puntoId") Long puntoId);

    @Query(value = "SELECT"+atributos+"FROM incidencia i WHERE i.cliente_id = :clienteId", nativeQuery = true)
    Set<Incidencia> findByIncidenciaByClienteId(@Param("clienteId") Long clienteId);

    @Query(value = "SELECT"+atributosRank+"FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY I.ID) AS RowNum FROM INCIDENCIA I) AS RankedPoints WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal", nativeQuery = true)
    Set<Incidencia> findAllByPages(@Param("idInicial")int idInicial, @Param("idFinal") int idFinal);

    @Query(value = "SELECT"+atributosRank+"FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY I.ID) AS RowNum FROM INCIDENCIA I " +
            "WHERE (:estado IS NULL OR I.ESTADO = :estado)) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Incidencia> findIncidenciaConFiltros(@Param("estado") String estado,
                                             @Param("idInicial") int idInicial,
                                             @Param("idFinal") int idFinal);

    @Query(value = "SELECT COUNT(I.ID) FROM INCIDENCIA I" +
            " WHERE (:estado IS NULL OR I.ESTADO = :estado)",
            nativeQuery = true)
    long countPuntoConFiltros(@Param("estado") String estado);
}