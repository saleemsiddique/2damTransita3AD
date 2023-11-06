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

    @Query(value = "SELECT * FROM cliente c WHERE c.apellidos LIKE :apellido%", nativeQuery = true)
    Set<Cliente> findByApellidoStartingWith(@Param("apellido") String apellido);

    @Query(value = "SELECT * FROM cliente c WHERE c.nombre_usuario LIKE :nombreUsuario%", nativeQuery = true)
    Set<Cliente> findByNombreUsuarioStartingWith(@Param("nombreUsuario") String nombreUsuario);

    @Query(value = "SELECT c.* FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario " +
            "WHERE ru.id_rol = 1 OR ru.id_rol = 2", nativeQuery = true)
    Set<Cliente> findByRoleAdminOrModerator();


    @Query(value = "SELECT c.* FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario " +
            "WHERE ru.id_rol = 3", nativeQuery = true)
    Set<Cliente> findByRoleUsuario();






    /*@Query(value = "SELECT * FROM cliente c WHERE c.role LIKE :ROLE_USUARIO", nativeQuery = true)
    Set<Cliente> findByRoleUsuario(@Param("rol") String nombreUsuario);*/
}