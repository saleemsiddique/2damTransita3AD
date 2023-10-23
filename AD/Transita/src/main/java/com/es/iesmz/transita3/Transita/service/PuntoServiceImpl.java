package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import com.es.iesmz.transita3.Transita.exception.PuntoNotFoundException;
import com.es.iesmz.transita3.Transita.repository.PuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Set<Punto> findByAccesibilidadPunto(AccesibilidadPunto accesibilidadPunto) {
        return puntoRepository.findByAccesibilidadPunto(accesibilidadPunto);
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
}
