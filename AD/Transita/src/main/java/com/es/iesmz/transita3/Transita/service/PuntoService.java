package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.EVisibilidad;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PuntoService {

    Set<Punto> findAll();
    Optional<Punto> findById(long id);
    Set<Punto> findByTipoPunto(TipoPunto tipoPunto);
    Set<Punto> findByLatitud(double latitud);
    Set<Punto> findByLongitud(double longitud);
    Optional<Punto> findByLatitudLongitud(Double latitud, Double longitud);
    Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto);
    Set<Punto> findByAccesibilidadVisibilidad(String accesibilidadPunto, String visibilidad);
    Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto);
    Set<Punto> findAllByPages(int idInicial, int idFinal);
    Set<Punto> findPuntosConIncidenciasAceptadasYVisibilidadGlobal();

    Set<Punto> findPuntosConIncidencias();

    Set<Punto> findPuntosByClienteId(Long id);

    Punto findPuntoByCoordinatesAndCliente(double latitud, double longitud, long id);

    Punto getPrimerPunto();

    Punto addPunto(Punto punto);

    Punto addPuntoconFav(Punto punto, Long clienteId);

    @Transactional
    Punto removeClienteFromPunto(Long puntoId, Long clienteId);

    Punto modifyPunto(long id, Punto nuevoPunto);

    Punto agregarClienteAlPunto(long id, long clienteId);
    void deletePunto(long id);
    long countPunto();
    long countPuntoConFiltros(String tipoPunto, String accesibilidadPunto, String visibilidad);

    List<Punto> buscarPuntosConFiltros(String tipo, String accesibilidad, String visibilidad, int ini, int fin);
    List<Punto> buscarPuntosConFiltrosMapa(String tipo, String accesibilidad, String visibilidad);
}
