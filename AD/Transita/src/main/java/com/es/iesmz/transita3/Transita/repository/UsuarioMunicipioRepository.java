package com.es.iesmz.transita3.Transita.repository;


import com.es.iesmz.transita3.Transita.domain.UsuarioMunicipio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioMunicipioRepository extends CrudRepository<UsuarioMunicipio, Long> {
    Set<UsuarioMunicipio> findAll();
    Set<UsuarioMunicipio> findByNombre(String nombre);
    Set<UsuarioMunicipio> findByApellidos(String apellidos);
    UsuarioMunicipio findByNombreUsuario(String nombreUsuario);
    UsuarioMunicipio findByTelefono(String telefono);

    Boolean existsByNombreUsuario(String nombreUsuario);

    @Query(value = "SELECT * FROM USUARIOMUNICIPIO u WHERE u.nombre LIKE :nombre%", nativeQuery = true)
    Set<UsuarioMunicipio> findByNombreStartingWith(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM USUARIOMUNICIPIO u WHERE u.apellidos LIKE :apellido%", nativeQuery = true)
    Set<UsuarioMunicipio> findByApellidoStartingWith(@Param("apellido") String apellido);

    @Query(value = "SELECT * FROM USUARIOMUNICIPIO u WHERE u.nombre_usuario LIKE :nombreUsuario%", nativeQuery = true)
    Set<UsuarioMunicipio> findByNombreUsuarioStartingWith(@Param("nombreUsuario") String nombreUsuario);
}
