package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PuntoRepository extends CrudRepository<Punto, Long> {
    String atributos = " p.id, p.accesibilidad, p.descripcion, p.foto, p.latitud, p.longitud, p.tipo, p.visibilidad ";
    String atributosRank = " rankedpoints.id, rankedpoints.accesibilidad, rankedpoints.descripcion, rankedpoints.foto, rankedpoints.latitud," +
            " rankedpoints.longitud, rankedpoints.tipo, rankedpoints.visibilidad ";
    Set<Punto> findAll();

    @Query(value = "select"+atributos+"from punto p order by p.id asc limit 1", nativeQuery = true)
    Punto getPrimerPunto();

    @Query(value = "select"+ atributosRank +"from (select"+atributos+", row_number() over (order by p.id) as rownum from punto p) as rankedpoints r where rankedpoints.rownum between :idinicial and :idfinal", nativeQuery = true)
    Set<Punto> findAllByPages(@Param("idinicial")int idInicial, @Param("idfinal") int idFinal);

    @Query(value = "SELECT"+ atributosRank + "from (select"+atributos+", row_number() over (order by p.id) as rownum from punto p " +
            "where (:tipopunto is null or p.tipo = :tipopunto) " +
            "and (:accesibilidadpunto is null or p.accesibilidad = :accesibilidadpunto) " +
            "and (:visibilidadpunto is null or p.visibilidad = :visibilidadpunto)) as rankedpoints " +
            "where rankedpoints.rownum between :idinicial and :idfinal",
            nativeQuery = true)
    List<Punto> buscarPuntosConFiltros(@Param("tipopunto") String tipoPunto,
                                       @Param("accesibilidadpunto") String accesibilidadPunto,
                                       @Param("visibilidadpunto") String visibilidadPunto,
                                       @Param("idinicial") int idInicial,
                                       @Param("idfinal") int idFinal);

    @Query(value = "select"+ atributos +"from  punto p " +
            "where (:tipopunto is null or p.tipo = :tipopunto) " +
            "and (:accesibilidadpunto is null or p.accesibilidad = :accesibilidadpunto) " +
            "and (:visibilidadpunto is null or p.visibilidad = :visibilidadpunto)" ,
            nativeQuery = true)
    List<Punto> buscarPuntosConFiltrosMapa(@Param("tipopunto") String tipoPunto,
                                       @Param("accesibilidadpunto") String accesibilidadPunto,
                                       @Param("visibilidadpunto") String visibilidadPunto);

    long count();

    @Query(value = "select count(p.id) from punto p " +
            "where (:tipopunto is null or p.tipo = :tipopunto) " +
            "and (:accesibilidadpunto is null or p.accesibilidad = :accesibilidadpunto) " +
            "and (:visibilidadpunto is null or p.visibilidad = :visibilidadpunto)",
            nativeQuery = true)
    long countPuntoConFiltros(@Param("tipopunto") String tipoPunto,
                         @Param("accesibilidadpunto") String accesibilidadPunto,
                         @Param("visibilidadpunto") String visibilidadPunto);

    @Query(value = "select distinct p from punto p " +
            "join fetch p.incidencias i " +
            "where i.estado in ('aceptado', 'enproceso') " +
            "and p.visibilidadpunto = 'global'", nativeQuery = true)
    Set<Punto> findPuntosConIncidenciasAceptadasYVisibilidadGlobal();

    Optional<Punto> findById(long id);

    Set<Punto> findByTipoPunto(TipoPunto tipoPunto);

    Set<Punto> findByLatitud(double latitud);

    Set<Punto> findByLongitud(double longitud);

    @Query(value = "select "+ atributos +" from punto p where p.latitud = :latitud and p.longitud = :longitud", nativeQuery = true)
    Optional<Punto> findByLatitudLongitud(@Param("latitud") Double latitud, @Param("longitud") Double longitud);

    Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto);

    Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto);

    @Query(value = "select "+ atributos +" from punto p where (coalesce(:accesibilidad, '') = '' or p.accesibilidad = :accesibilidad) and (coalesce(:visibilidad, '') = '' or p.visibilidad = :visibilidad)", nativeQuery = true)
    Set<Punto> findByAccesibilidadVisibilidad(@Param("accesibilidad") String accesibilidad, @Param("visibilidad") String visibilidad);

    @Query(value = "select " + atributos + " from punto p " +
            "where " +
            "    if (:parametro = 'id', p.id = :valor, 1)" +
            "    and if (:parametro = 'accesibilidad', p.accesibilidad = :valor, 1)" +
            "    and if (:parametro = 'tipo', p.tipo = :valor, 1)" +
            "    and if (:parametro = 'visibilidad', p.visibilidad = :valor, 1)"
            , nativeQuery = true)
    Set<Punto> findByFiltro(@Param("parametro") String parametro,
                                 @Param("valor") String valor);


    @Query(value = "select "+ atributos +" from punto p " +
            "inner join favoritos f on p.id = f.id_punto " +
            "where f.id_usuario = :idcliente and p.visibilidad = 'favorito'",
            nativeQuery = true)
    Set<Punto> findPuntosByClienteId(@Param("idcliente") Long idCliente);

    @Query(value = "select "+ atributos +" from punto p " +
            "inner join favoritos f on p.id = f.id_punto " +
            "where p.latitud = :latitud " +
            "and p.longitud = :longitud " +
            "and f.id_usuario = :idcliente",
            nativeQuery = true)
    Punto findPuntoByCoordinatesAndCliente(@Param("latitud") double latitud,
                                                 @Param("longitud") double longitud,
                                                 @Param("idcliente") long idCliente);
}