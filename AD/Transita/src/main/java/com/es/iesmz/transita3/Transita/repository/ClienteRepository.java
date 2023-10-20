package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    Set<Cliente> findAll();
    Set<Cliente> findByNombre(String nombre);
    Set<Cliente> findByApellidos(String apellido);
    Cliente findByNombreUsuario(String nombreUsuario);


    Boolean existsByNombreUsuario(String nombreUsuario);

    @Query(value = "SELECT * FROM cliente c WHERE c.nombre LIKE :nombre%", nativeQuery = true)
    Set<Cliente> findByNombreStartingWith(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM cliente c WHERE c.nombre LIKE :apellido%", nativeQuery = true)
    Set<Cliente> findByApellidoStartingWith(@Param("apellido") String apellido);

}