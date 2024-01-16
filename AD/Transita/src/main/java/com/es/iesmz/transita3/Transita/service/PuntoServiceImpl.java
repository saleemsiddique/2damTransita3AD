package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.*;
import com.es.iesmz.transita3.Transita.exception.PuntoNotFoundException;
import com.es.iesmz.transita3.Transita.repository.ClienteRepository;
import com.es.iesmz.transita3.Transita.repository.PuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PuntoServiceImpl implements PuntoService{

    @Autowired
    private PuntoRepository puntoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

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
    public Set<Punto> findPuntosConIncidenciasAceptadasYVisibilidadGlobal() {
        return puntoRepository.findPuntosConIncidenciasAceptadasYVisibilidadGlobal();
    }

    @Override
    public Set<Punto> findPuntosByClienteId(Long id) {
        return puntoRepository.findPuntosByClienteId(id);
    }

    @Override
    public Punto findPuntoByCoordinatesAndCliente(double latitud, double longitud, long id) {
        return puntoRepository.findPuntoByCoordinatesAndCliente(latitud, longitud, id);
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
    @Transactional  // Asegura que la operación se realiza en una transacción
    public Punto addPuntoconFav(Punto punto, Long clienteId) {
        Punto nuevoPunto = puntoRepository.save(punto);  // Guarda el Punto

        Optional<Cliente> optionalCliente = clienteService.findById(clienteId);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            nuevoPunto.getClientes().add(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }

        return puntoRepository.save(nuevoPunto);  // Guarda nuevamente el Punto

    }


    @Override
    public Punto modifyPunto(long id, Punto nuevoPunto) {
        Punto punto = puntoRepository.findById(id)
                .orElseThrow(() -> new PuntoNotFoundException(id));
        nuevoPunto.setId(punto.getId());
        return puntoRepository.save(nuevoPunto);
    }

    @Override
    public Punto agregarClienteAlPunto(long id, long clienteId) {
        Optional<Punto> optionalPunto = puntoRepository.findById(id);
        Optional<Cliente> optionalCliente = clienteRepository.findById(clienteId);

        if (optionalPunto.isPresent() && optionalCliente.isPresent()) {
            Punto punto = optionalPunto.get();
            Cliente cliente = optionalCliente.get();

            punto.getClientes().add(cliente);
            puntoRepository.save(punto);

            return punto;
        } else {
            throw new RuntimeException("Punto o Cliente no encontrado");
        }
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
