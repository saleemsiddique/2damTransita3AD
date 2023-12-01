package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.EVisibilidad;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PuntoService {

    Set<Punto> findAll();
    Optional<Punto> findById(long id);
    Set<Punto> findByTipoPunto(TipoPunto tipoPunto);
    Set<Punto> findByLatitud(double latitud);
    Set<Punto> findByLongitud(double longitud);
    Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto);
    Set<Punto> findByTipoAccesibilidadVisibilidad(String tipoPunto, String accesibilidadPunto, String visibilidad);

    Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto);
    Set<Punto> findAllByPages(int idInicial, int idFinal);
    Punto getPrimerPunto();
    Punto addPunto(Punto punto);
    Punto modifyPunto(long id, Punto nuevoPunto);
    void deletePunto(long id);
    long countPunto();
    List<Punto> buscarPuntosConFiltros(String tipo, String accesibilidad, String visibilidad, int ini, int fin);
}
