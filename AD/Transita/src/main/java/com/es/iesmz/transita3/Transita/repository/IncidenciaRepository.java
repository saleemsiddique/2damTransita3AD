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
    String atributos = " i.id, i.descripcion, i.duracion, i.estado, i.fecha_hora, i.fotos, i.cliente_id, i.punto_id ";
    String atributosRank = " rankedpoints.id, rankedpoints.descripcion, rankedpoints.duracion, rankedpoints.estado," +
            " rankedpoints.fecha_hora, rankedpoints.fotos, rankedpoints.cliente_id, rankedpoints.punto_id ";

    Set<Incidencia> findAll();

    Optional<Incidencia> findById(long id);

    Set<Incidencia> findByEstado(EstadoIncidencia estado);

    Set<Incidencia> findByDuracion(String duracion);

    @Query(value = "select" + atributos + "from incidencia i where i.punto_id = :puntoId", nativeQuery = true)
    Set<Incidencia> findByIncidenciaByPuntoId(@Param("puntoId") Long puntoId);

    @Query(value = "select" + atributos + "from incidencia i where i.cliente_id = :clienteId", nativeQuery = true)
    Set<Incidencia> findByIncidenciaByClienteId(@Param("clienteId") Long clienteId);

    @Query(value = "select" + atributosRank + "from (select" + atributos + ", row_number() over (order by i.id) as rownum from incidencia i) as rankedpoints where rankedpoints.rownum between :idinicial and :idfinal", nativeQuery = true)
    Set<Incidencia> findAllByPages(@Param("idinicial") int idInicial, @Param("idfinal") int idFinal);

    @Query(value = "select" + atributosRank + "from (select" + atributos + ", row_number() over (order by i.id) as rownum from incidencia i " +
            "where (:estado is null or i.estado = :estado)) as rankedpoints " +
            "where rankedpoints.rownum between :idinicial and :idfinal",
            nativeQuery = true)
    Set<Incidencia> findIncidenciaConFiltros(@Param("estado") String estado,
                                             @Param("idinicial") int idInicial,
                                             @Param("idfinal") int idFinal);

    @Query(value = "select count(i.id) from incidencia i" +
            " where (:estado is null or i.estado = :estado)",
            nativeQuery = true)
    long countPuntoConFiltros(@Param("estado") String estado);

    @Query(value = "select " + atributos + " from incidencia i " +
            "where " +
            "    if (:parametro = 'id', i.id = :valor, 1)" +
            "    and if (:parametro = 'estado', i.estado = :valor, 1)" +
            "    and if (:parametro = 'fecha_hora', i.fecha_hora = :valor, 1) "
            , nativeQuery = true)
    Set<Incidencia> findByFiltro(@Param("parametro") String parametro,
                                 @Param("valor") String valor);
}
