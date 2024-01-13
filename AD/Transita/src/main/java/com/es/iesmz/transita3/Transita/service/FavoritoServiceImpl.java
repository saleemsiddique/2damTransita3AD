package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.Favorito;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.exception.FavoritoNotFoundException;
import com.es.iesmz.transita3.Transita.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
@Service
public class FavoritoServiceImpl implements FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Override
    public Optional<Favorito> findById(long id) {
        return favoritoRepository.findById(id);
    }

    @Override
    public Set<Favorito> findByidCliente(Long id) {
        return favoritoRepository.findByidCliente(id);
    }

    @Override
    public Favorito createFavorito(Favorito newFavorito) {
        return favoritoRepository.save(newFavorito);
    }

    @Override
    public Favorito updateFavorito(long idFavorito, Favorito newFavorito) {
        Favorito favorito = favoritoRepository.findById(idFavorito)
                .orElseThrow(() -> new FavoritoNotFoundException(idFavorito));
        newFavorito.setId(favorito.getId());
        return favoritoRepository.save(newFavorito);
    }

    @Override
    public void deleteFavorito(long idFavorito) {
        favoritoRepository.findById(idFavorito)
                .orElseThrow(() -> new FavoritoNotFoundException(idFavorito));
        favoritoRepository.deleteById(idFavorito);
    }
}
