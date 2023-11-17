package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.EVisibilidad;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import org.springframework.data.repository.CrudRepository;
import com.es.iesmz.transita3.Transita.domain.Punto;


import java.util.Optional;
import java.util.Set;

public interface PuntoRepository extends CrudRepository<Punto, Long> {
    Set<Punto> findAll();

    Optional<Punto> findById(long id);

    Set<Punto> findByTipoPunto(TipoPunto tipoPunto);

    Set<Punto> findByLatitud(double latitud);

    Set<Punto> findByLongitud(double longitud);

    Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto);

    Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto);
}