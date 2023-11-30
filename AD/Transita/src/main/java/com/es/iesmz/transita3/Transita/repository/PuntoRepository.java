package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.EVisibilidad;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.es.iesmz.transita3.Transita.domain.Punto;
import org.springframework.data.repository.query.Param;


import java.util.Optional;
import java.util.Set;

public interface PuntoRepository extends CrudRepository<Punto, Long> {
    Set<Punto> findAll();

    @Query(value = "SELECT * FROM PUNTO P ORDER BY P.ID ASC LIMIT 1", nativeQuery = true)
    Punto getPrimerPunto();

    @Query(value = "SELECT * FROM (SELECT P.*, ROW_NUMBER() OVER (ORDER BY P.ID) AS RowNum FROM PUNTO P) AS RankedPoints WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal", nativeQuery = true)
    Set<Punto> findAllByPages(@Param("idInicial")int idInicial, @Param("idFinal") int idFinal);

    long count();

    Optional<Punto> findById(long id);

    Set<Punto> findByTipoPunto(TipoPunto tipoPunto);

    Set<Punto> findByLatitud(double latitud);

    Set<Punto> findByLongitud(double longitud);

    Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto);

    Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto);

    @Query(value = "SELECT * FROM PUNTO P WHERE (COALESCE(:tipo, '') = '' OR P.TIPO = :tipo) AND (COALESCE(:accesibilidad, '') = '' OR P.ACCESIBILIDAD = :accesibilidad) AND (COALESCE(:visibilidad, '') = '' OR P.VISIBILIDAD = :visibilidad)", nativeQuery = true)
    Set<Punto> findByTipoAccesibilidadVisibilidad(@Param("tipo") String tipo, @Param("accesibilidad") String accesibilidad, @Param("visibilidad") String visibilidad);

}