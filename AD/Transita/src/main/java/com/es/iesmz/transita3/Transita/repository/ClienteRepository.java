package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.ECliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    Set<Cliente> findAll();
    Set<Cliente> findByNombre(String nombre);
    Set<Cliente> findByApellidos(String apellido);
    Set<Cliente> findByEstadoCuenta(ECliente estadoCliente);
    Cliente findByNombreUsuario(String nombreUsuario);
    Boolean existsByNombreUsuario(String nombreUsuario);


    @Query(value = "SELECT * FROM (SELECT C.*, ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum FROM CLIENTE C) " +
            "AS RankedPoints WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findAllByPages(@Param("idInicial")int idInicial, @Param("idFinal") int idFinal);

    @Query(value = "SELECT * FROM (SELECT C.*, ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:estado IS NULL OR C.ESTADO = :estado) " +
            "AND RU.ID_ROL = 3) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findAllByPagesFiltrado(@Param("idInicial") int idInicial, @Param("idFinal") int idFinal, @Param("estado") int estado);

    @Query(value = "SELECT * FROM cliente c WHERE c.nombre LIKE :nombre%", nativeQuery = true)
    Set<Cliente> findByNombreStartingWith(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM cliente c WHERE c.apellidos LIKE :apellido%", nativeQuery = true)
    Set<Cliente> findByApellidoStartingWith(@Param("apellido") String apellido);

    @Query(value = "SELECT * FROM cliente c WHERE c.nombre_usuario LIKE :nombreUsuario%", nativeQuery = true)
    Set<Cliente> findByNombreUsuarioStartingWith(@Param("nombreUsuario") String nombreUsuario);

    @Query(value = "SELECT c.* FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario " +
            "WHERE ru.id_rol = 1 OR ru.id_rol = 2", nativeQuery = true)
    Set<Cliente> findByRoleAdminOrModerator();

    @Query(value = "SELECT * FROM (SELECT C.*, ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:rol IS NULL OR RU.ID_ROL = :rol)) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findUsuarioMunicipioWithFilter(
            @Param("rol") int rol,
            @Param("idInicial") int idInicial,
            @Param("idFinal") int idFinal);


    @Query(value = "SELECT * FROM (SELECT C.*, ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (RU.ID_ROL = 1 OR RU.ID_ROL = 2)) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findUsuarioMunicipioWithRowNum(
            @Param("idInicial") int idInicial,
            @Param("idFinal") int idFinal);



    @Query(value = "SELECT c.* FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario WHERE ru.id_rol = 3", nativeQuery = true)
    Set<Cliente> findByRoleUsuario();

    @Query(value = "SELECT c.* FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario WHERE ru.id_rol = 3 AND c.estado = 0", nativeQuery = true)
    Set<Cliente> findByRoleUsuarioAndEstadoDesactivado();

    @Query(value = "SELECT c.* FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario WHERE ru.id_rol = 3 AND c.estado = 1", nativeQuery = true)
    Set<Cliente> findByRoleUsuarioAndEstadoActivado();

    @Query(value = "SELECT C.* FROM CLIENTE C INNER JOIN ROLES_USUARIO R ON C.ID = R.ID_USUARIO WHERE R.ID_ROL=:rol", nativeQuery = true)
    Set<Cliente> findByRole(@Param("rol")int rol);

    @Query(value = "SELECT COUNT(*) FROM CLIENTE C " +
            "WHERE (:estado IS NULL OR C.ESTADO = :estado) " ,
            nativeQuery = true)
    long countClienteConFiltros(@Param("estado") int estado);

    @Query(value = "SELECT COUNT(*) FROM CLIENTE C INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:rol IS NULL OR RU.ID_ROL = :rol)",
            nativeQuery = true)
    long countClientesWithRoleFilter(@Param("rol") int rol);

    @Query(value = "SELECT COUNT(*) FROM CLIENTE C INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (RU.ID_ROL = 1 OR RU.ID_ROL = 2)",
            nativeQuery = true)
    long countClientesWithRole();







    /*@Query(value = "SELECT * FROM cliente c WHERE c.role LIKE :ROLE_USUARIO", nativeQuery = true)
    Set<Cliente> findByRoleUsuario(@Param("rol") String nombreUsuario);*/
}