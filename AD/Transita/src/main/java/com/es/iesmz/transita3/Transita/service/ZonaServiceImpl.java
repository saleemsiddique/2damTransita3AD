package com.es.iesmz.transita3.Transita.service;
import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.Zona;
import com.es.iesmz.transita3.Transita.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ZonaServiceImpl implements ZonaService {
    @Autowired
    private ZonaRepository zonaRepository;

    @Override
    public Set<Zona> findAll() {
        List<Zona> zonasList = zonaRepository.findAll();
        return new HashSet<>(zonasList);
    }

    @Override
    public Optional<Zona> findById(long id) {
        return zonaRepository.findById(id);
    }

    @Override
    public Zona addZona(Zona zona) {
        return zonaRepository.save(zona);
    }

    @Override
    public Set<Zona> findByNombreStartingWith(String nombre) {
        return zonaRepository.findByNombreStartingWith(nombre);
    }

    @Override
    public Zona findByNombre(String nombre) {
        return (Zona) zonaRepository.findByNombre(nombre);
    }

    @Override
    public Zona modifyZona(long id, Zona newZona) {
        Optional<Zona> optionalZona = zonaRepository.findById(id);
        if (optionalZona.isPresent()) {
            Zona zona = optionalZona.get();
            zona.setNombre(newZona.getNombre());

            // Puedes continuar actualizando otros campos según tus necesidades
            return zonaRepository.save(zona);
        } else {
            throw new RuntimeException("Zona no encontrada con id: " + id);
        }
    }

    @Override
    public void deleteZona(long id) {
        zonaRepository.deleteById(id);
    }
}
