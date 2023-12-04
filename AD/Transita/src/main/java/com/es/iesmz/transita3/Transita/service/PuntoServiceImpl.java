package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.EVisibilidad;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import com.es.iesmz.transita3.Transita.exception.PuntoNotFoundException;
import com.es.iesmz.transita3.Transita.repository.PuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PuntoServiceImpl implements PuntoService{

    @Autowired
    private PuntoRepository puntoRepository;
    @Override
    public Set<Punto> findAll() {
        return puntoRepository.findAll();
    }

    @Override
    public Optional<Punto> findById(long id) {
        return puntoRepository.findById(id);
    }

    @Override
    public Set<Punto> findByTipoPunto(TipoPunto tipoPunto) {
        return puntoRepository.findByTipoPunto(tipoPunto);
    }

    @Override
    public Set<Punto> findByLatitud(double latitud) {
        return puntoRepository.findByLatitud(latitud);
    }

    @Override
    public Set<Punto> findByLongitud(double longitud) {
        return puntoRepository.findByLongitud(longitud);
    }

    @Override
    public Optional<Punto> findByLatitudLongitud(Double latitud, Double longitud) {
        return puntoRepository.findByLatitudLongitud(latitud, longitud);
    }

    @Override
    public Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto) {
        return puntoRepository.findByAccesibilidadPunto(accesibilidadPunto);
    }

    @Override
    public Set<Punto> findByTipoAccesibilidadVisibilidad(String tipoPunto, String accesibilidadPunto, String visibilidad) {
        return puntoRepository.findByTipoAccesibilidadVisibilidad(tipoPunto, accesibilidadPunto, visibilidad);
    }

    @Override
    public Set<Punto> findByVisibilidadPunto(EVisibilidad visibilidadPunto) {
        return puntoRepository.findByVisibilidadPunto(visibilidadPunto);
    }

    @Override
    public Set<Punto> findAllByPages(int idInicial, int idFinal) {
        return puntoRepository.findAllByPages(idInicial, idFinal);
    }

    @Override
    public Punto getPrimerPunto() {
        return puntoRepository.getPrimerPunto();
    }

    @Override
    public Punto addPunto(Punto punto) {
        return puntoRepository.save(punto);
    }

    @Override
    public Punto modifyPunto(long id, Punto nuevoPunto) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new PuntoNotFoundException(id));
        nuevoPunto.setId(punto.getId());
        return puntoRepository.save(nuevoPunto);
    }

    @Override
    public void deletePunto(long id) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new PuntoNotFoundException(id));
        puntoRepository.deleteById(id);
    }

    @Override
    public long countPunto() {
        return puntoRepository.count();
    }

    @Override
    public long countPuntoConFiltros(String tipoPunto, String accesibilidadPunto, String visibilidad) {
        return puntoRepository.countPuntoConFiltros(tipoPunto, accesibilidadPunto, visibilidad);
    }

    @Override
    public List<Punto> buscarPuntosConFiltros(String tipo, String accesibilidad, String visibilidad, int ini, int fin) {
        return puntoRepository.buscarPuntosConFiltros(tipo, accesibilidad, visibilidad, ini, fin);
    }
}
