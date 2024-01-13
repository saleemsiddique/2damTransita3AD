package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Favorito;
import com.es.iesmz.transita3.Transita.domain.Incidencia;

import java.util.Optional;
import java.util.Set;

public interface FavoritoService {

    Optional<Favorito> findById(long id);

    Set<Favorito> findByidCliente(Long id);

    Favorito createFavorito(Favorito newFavorito);

    Favorito updateFavorito(long idFavorito, Favorito newfavorito);

    void deleteFavorito(long idFavorito);
}
