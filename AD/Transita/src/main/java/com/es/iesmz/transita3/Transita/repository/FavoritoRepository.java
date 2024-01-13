package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.Favorito;
import com.es.iesmz.transita3.Transita.domain.Incidencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FavoritoRepository extends CrudRepository<Favorito, Long> {
    Optional<Favorito> findById(long id);
    @Query(value = "SELECT * FROM favorito f WHERE f.cliente_id = :id", nativeQuery = true)
    Set<Favorito> findByidCliente(Long id);
}
