package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PuntoRepository extends CrudRepository<Punto, Long> {
    String atributos = " P.ID, P.ACCESIBILIDAD, P.DESCRIPCION, P.FOTO, P.LATITUD, P.LONGITUD, P.TIPO, P.VISIBILIDAD ";
    String atributosRank = " RankedPoints.ID, RankedPoints.ACCESIBILIDAD, RankedPoints.DESCRIPCION, RankedPoints.FOTO, RankedPoints.LATITUD," +
            " RankedPoints.LONGITUD, RankedPoints.TIPO, RankedPoints.VISIBILIDAD ";
    Set<Punto> findAll();

    @Query(value = "SELECT"+atributos+"FROM PUNTO P ORDER BY P.ID ASC LIMIT 1", nativeQuery = true)
    Punto getPrimerPunto();

    @Query(value = "SELECT"+ atributosRank +"FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY P.ID) AS RowNum FROM PUNTO P) AS RankedPoints R WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal", nativeQuery = true)
    Set<Punto> findAllByPages(@Param("idInicial")int idInicial, @Param("idFinal") int idFinal);

    @Query(value = "SELECT"+ atributosRank + "FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY P.ID) AS RowNum FROM PUNTO P " +
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

    @Query(value = "SELECT"+ atributos +"FROM  PUNTO P " +
            "WHERE (:tipoPunto IS NULL OR P.tipo = :tipoPunto) " +
            "AND (:accesibilidadPunto IS NULL OR P.accesibilidad = :accesibilidadPunto) " +
            "AND (:visibilidadPunto IS NULL OR P.visibilidad = :visibilidadPunto)" ,
            nativeQuery = true)
    List<Punto> buscarPuntosConFiltrosMapa(@Param("tipoPunto") String tipoPunto,
                                       @Param("accesibilidadPunto") String accesibilidadPunto,
                                       @Param("visibilidadPunto") String visibilidadPunto);

    long count();

    @Query(value = "SELECT COUNT(P.ID) FROM PUNTO P " +
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

    @Query(value = "SELECT "+ atributos +" FROM PUNTO P WHERE P.latitud = :latitud AND P.longitud = :longitud", nativeQuery = true)
    Optional<Punto> findByLatitudLongitud(@Param("latitud") Double latitud, @Param("longitud") Double longitud);

    Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto);

    Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto);

    @Query(value = "SELECT "+ atributos +" FROM PUNTO P WHERE (COALESCE(:accesibilidad, '') = '' OR P.ACCESIBILIDAD = :accesibilidad) AND (COALESCE(:visibilidad, '') = '' OR P.VISIBILIDAD = :visibilidad)", nativeQuery = true)
    Set<Punto> findByAccesibilidadVisibilidad(@Param("accesibilidad") String accesibilidad, @Param("visibilidad") String visibilidad);

    @Query(value = "SELECT " + atributos + " FROM PUNTO P " +
            "WHERE " +
            "    IF (:parametro = 'id', P.id = :valor, 1)" +
            "    AND IF (:parametro = 'accesibilidad', P.ACCESIBILIDAD = :valor, 1)" +
            "    AND IF (:parametro = 'tipo', P.TIPO = :valor, 1)" +
            "    AND IF (:parametro = 'visibilidad', P.VISIBILIDAD = :valor, 1)"
            , nativeQuery = true)
    Set<Punto> findByFiltro(@Param("parametro") String parametro,
                                 @Param("valor") String valor);


    @Query(value = "SELECT "+ atributos +" FROM PUNTO P " +
            "INNER JOIN favoritos F ON P.id = F.id_punto " +
            "WHERE F.id_usuario = :idCliente AND P.visibilidad = 'FAVORITO'",
            nativeQuery = true)
    Set<Punto> findPuntosByClienteId(@Param("idCliente") Long idCliente);

    @Query(value = "SELECT "+ atributos +" FROM Punto P " +
            "INNER JOIN favoritos F ON P.id = F.id_punto " +
            "WHERE P.latitud = :latitud " +
            "AND P.longitud = :longitud " +
            "AND F.id_usuario = :idCliente",
            nativeQuery = true)
    Punto findPuntoByCoordinatesAndCliente(@Param("latitud") double latitud,
                                                 @Param("longitud") double longitud,
                                                 @Param("idCliente") long idCliente);
}