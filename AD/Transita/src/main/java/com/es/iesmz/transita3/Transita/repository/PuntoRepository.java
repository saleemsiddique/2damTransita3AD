package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.EVisibilidad;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.es.iesmz.transita3.Transita.domain.Punto;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PuntoRepository extends CrudRepository<Punto, Long> {
    Set<Punto> findAll();

    @Query(value = "SELECT * FROM PUNTO P ORDER BY P.ID ASC LIMIT 1", nativeQuery = true)
    Punto getPrimerPunto();

    @Query(value = "SELECT * FROM (SELECT P.*, ROW_NUMBER() OVER (ORDER BY P.ID) AS RowNum FROM PUNTO P) AS RankedPoints WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal", nativeQuery = true)
    Set<Punto> findAllByPages(@Param("idInicial")int idInicial, @Param("idFinal") int idFinal);

    @Query(value = "SELECT * FROM (SELECT P.*, ROW_NUMBER() OVER (ORDER BY P.ID) AS RowNum FROM PUNTO P " +
            "WHERE (:tipoPunto IS NULL OR P.tipo = :tipoPunto) " +
            "AND (:accesibilidadPunto IS NULL OR P.accesibilidad = :accesibilidadPunto) " +
            "AND (:visibilidadPunto IS NULL OR P.visibilidad = :visibilidadPunto)) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    List<Punto> buscarPuntosConFiltros(@Param("tipoPunto") String tipoPunto,
                                       @Param("accesibilidadPunto") String accesibilidadPunto,
                                       @Param("visibilidadPunto") String visibilidadPunto,
                                       @Param("idInicial") int idInicial,
                                       @Param("idFinal") int idFinal);

    long count();

    @Query(value = "SELECT COUNT(*) FROM PUNTO P " +
            "WHERE (:tipoPunto IS NULL OR P.tipo = :tipoPunto) " +
            "AND (:accesibilidadPunto IS NULL OR P.accesibilidad = :accesibilidadPunto) " +
            "AND (:visibilidadPunto IS NULL OR P.visibilidad = :visibilidadPunto)",
            nativeQuery = true)
    long countPuntoConFiltros(@Param("tipoPunto") String tipoPunto,
                         @Param("accesibilidadPunto") String accesibilidadPunto,
                         @Param("visibilidadPunto") String visibilidadPunto);

    @Query("SELECT DISTINCT p FROM Punto p " +
            "JOIN FETCH p.incidencias i " +
            "WHERE i.estado IN ('ACEPTADO', 'ENPROCESO') " +
            "AND p.visibilidadPunto = 'GLOBAL'")
    Set<Punto> findPuntosConIncidenciasAceptadasYVisibilidadGlobal();

    Optional<Punto> findById(long id);

    Set<Punto> findByTipoPunto(TipoPunto tipoPunto);

    Set<Punto> findByLatitud(double latitud);

    Set<Punto> findByLongitud(double longitud);

    @Query(value = "SELECT * FROM PUNTO P WHERE P.latitud = :latitud AND P.longitud = :longitud", nativeQuery = true)
    Optional<Punto> findByLatitudLongitud(@Param("latitud") Double latitud, @Param("longitud") Double longitud);

    Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto);

    Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto);

    @Query(value = "SELECT * FROM PUNTO P WHERE (COALESCE(:tipo, '') = '' OR P.TIPO = :tipo) AND (COALESCE(:accesibilidad, '') = '' OR P.ACCESIBILIDAD = :accesibilidad) AND (COALESCE(:visibilidad, '') = '' OR P.VISIBILIDAD = :visibilidad)", nativeQuery = true)
    Set<Punto> findByTipoAccesibilidadVisibilidad(@Param("tipo") String tipo, @Param("accesibilidad") String accesibilidad, @Param("visibilidad") String visibilidad);

    @Query(value = "SELECT P.* FROM PUNTO P " +
            "INNER JOIN favoritos F ON P.id = F.id_punto " +
            "WHERE F.id_usuario = :idCliente",
            nativeQuery = true)
    Set<Punto> findPuntosByClienteId(@Param("idCliente") Long idCliente);

    @Query(value = "SELECT P.* FROM Punto P " +
            "INNER JOIN favoritos F ON P.id = F.id_punto " +
            "WHERE P.latitud = :latitud " +
            "AND P.longitud = :longitud " +
            "AND F.id_usuario = :idCliente",
            nativeQuery = true)
    Punto findPuntoByCoordinatesAndCliente(@Param("latitud") double latitud,
                                                 @Param("longitud") double longitud,
                                                 @Param("idCliente") long idCliente);
}