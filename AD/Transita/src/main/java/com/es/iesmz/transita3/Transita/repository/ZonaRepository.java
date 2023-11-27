package com.es.iesmz.transita3.Transita.repository;



import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {
    List<Zona> findAll();
    Set<Zona> findByNombre(String nombre);

    @Query(value = "SELECT * FROM zona z WHERE z.nombre LIKE :nombre%", nativeQuery = true)
    Set<Zona> findByNombreStartingWith(@Param("nombre") String nombre);


    Optional<Zona> findById(long id);

    Zona save(Zona zona);

    void deleteById(long id);
}
