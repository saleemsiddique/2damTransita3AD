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
    String atributos = " C.ID, C.APELLIDOS, C.CONTRASENYA, C.ESTADO, C.NOMBRE, C.NOMBRE_USUARIO ";
    String atributosRank = " RankedPoints.ID, RankedPoints.APELLIDOS, RankedPoints.CONTRASENYA," +
            " RankedPoints.ESTADO, RankedPoints.NOMBRE, RankedPoints.NOMBRE_USUARIO ";
    Set<Cliente> findAll();
    Set<Cliente> findByNombre(String nombre);
    Set<Cliente> findByApellidos(String apellido);
    Set<Cliente> findByEstadoCuenta(ECliente estadoCliente);
    Cliente findByNombreUsuario(String nombreUsuario);
    Boolean existsByNombreUsuario(String nombreUsuario);


    @Query(value = "SELECT"+atributosRank+"FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE RU.ID_ROL = 3 AND ((:query IS NULL OR C.NOMBRE LIKE %:query%) OR C.APELLIDOS LIKE %:query% OR C.NOMBRE_USUARIO LIKE %:query%) ) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findAllByPages(@Param("idInicial") int idInicial, @Param("idFinal") int idFinal, @Param("query") String query);


    @Query(value = "SELECT"+atributosRank+"FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:estado IS NULL OR C.ESTADO = :estado) " +
            "AND RU.ID_ROL = 3 AND ((:query IS NULL OR C.NOMBRE LIKE %:query% OR C.APELLIDOS LIKE %:query% OR C.NOMBRE_USUARIO LIKE %:query%)) ) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findAllByPagesFiltrado(@Param("idInicial") int idInicial, @Param("idFinal") int idFinal, @Param("estado") int estado, @Param("query") String query);

    @Query(value = "SELECT"+atributosRank+"FROM (SELECT "+atributos+", ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:estado IS NULL OR C.ESTADO = :estado) " +
            "AND RU.ID_ROL = 3 " +
            "AND ((:query IS NULL OR C.NOMBRE LIKE %:query%) OR C.APELLIDOS LIKE %:query% OR NOMBRE_USUARIO LIKE %:query%) ) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> searchClientesFiltradoPages(
            @Param("idInicial") int idInicial,
            @Param("idFinal") int idFinal,
            @Param("estado") Integer estado,
            @Param("query") String query);


    @Query(value = "SELECT "+ atributos +" FROM CLIENTE " +
            "WHERE " +
            "    IF (:parametro = 'id', CLIENTE.id = :valor, 1)" +
            "    AND IF (:parametro = 'nombre', CLIENTE.NOMBRE = :valor, 1)" +
            "    AND IF (:parametro = 'apellidos', CLIENTE.APELLIDOS = :valor, 1) " +
            "    AND IF (:parametro = 'nombre_usuario', CLIENTE.NOMBRE_USUARIO = :valor, 1);"
            , nativeQuery = true)
    Set<Cliente> findByFiltro(@Param("parametro") String parametro,
                              @Param("valor") String valor);






    @Query(value = "SELECT "+atributos+" FROM cliente c WHERE c.nombre LIKE %:nombre%", nativeQuery = true)
    Set<Cliente> findByNombreStartingWith(@Param("nombre") String nombre);

    @Query(value = "SELECT"+atributos+"FROM cliente c WHERE c.apellidos LIKE %:apellido%", nativeQuery = true)
    Set<Cliente> findByApellidoStartingWith(@Param("apellido") String apellido);

    @Query(value = "SELECT"+atributos+"FROM cliente c WHERE c.nombre_usuario LIKE %:nombreUsuario%", nativeQuery = true)
    Set<Cliente> findByNombreUsuarioStartingWith(@Param("nombreUsuario") String nombreUsuario);

    @Query(value = "SELECT"+atributos+"FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario " +
            "WHERE ru.id_rol = 1 OR ru.id_rol = 2", nativeQuery = true)
    Set<Cliente> findByRoleAdminOrModerator();

    @Query(value = "SELECT"+atributosRank+"FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:rol IS NULL OR RU.ID_ROL = :rol) AND ((:query IS NULL OR C.NOMBRE LIKE %:query%) OR C.APELLIDOS LIKE %:query% OR NOMBRE_USUARIO LIKE %:query%)) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findUsuarioMunicipioWithFilter(
            @Param("rol") int rol,
            @Param("idInicial") int idInicial,
            @Param("idFinal") int idFinal,
            @Param("query") String query);



    @Query(value = "SELECT"+atributosRank+"FROM (SELECT"+atributos+", ROW_NUMBER() OVER (ORDER BY C.ID) AS RowNum " +
            "FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (RU.ID_ROL = 1 OR RU.ID_ROL = 2) AND ((:query IS NULL OR C.NOMBRE LIKE %:query%) OR C.APELLIDOS LIKE %:query% OR NOMBRE_USUARIO LIKE %:query%)) AS RankedPoints " +
            "WHERE RankedPoints.RowNum BETWEEN :idInicial AND :idFinal",
            nativeQuery = true)
    Set<Cliente> findUsuarioMunicipioWithRowNum(
            @Param("idInicial") int idInicial,
            @Param("idFinal") int idFinal,
            @Param("query") String query);


    @Query(value = "SELECT"+atributos+"FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario WHERE ru.id_rol = 3", nativeQuery = true)
    Set<Cliente> findByRoleUsuario();

    @Query(value = "SELECT"+atributos+"FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario WHERE ru.id_rol = 3 AND c.estado = 0", nativeQuery = true)
    Set<Cliente> findByRoleUsuarioAndEstadoDesactivado();

    @Query(value = "SELECT"+atributos+"FROM cliente c INNER JOIN roles_usuario ru ON c.id = ru.id_usuario WHERE ru.id_rol = 3 AND c.estado = 1", nativeQuery = true)
    Set<Cliente> findByRoleUsuarioAndEstadoActivado();

    @Query(value = "SELECT"+atributos+"FROM CLIENTE C INNER JOIN ROLES_USUARIO R ON C.ID = R.ID_USUARIO WHERE R.ID_ROL=:rol", nativeQuery = true)
    Set<Cliente> findByRole(@Param("rol")int rol);

    @Query(value = "SELECT COUNT(C.ID) FROM CLIENTE C " +
            "INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:estado IS NULL OR C.ESTADO = :estado) " +
            "AND RU.ID_ROL = 3 " +
            "AND ((:query IS NULL OR C.NOMBRE LIKE %:query%) OR C.APELLIDOS LIKE %:query% OR NOMBRE_USUARIO LIKE %:query%)",
            nativeQuery = true)
    long countClienteConFiltros(@Param("estado") int estado, @Param("query") String query);



    @Query(value = "SELECT COUNT(C.ID) FROM CLIENTE C INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (:rol IS NULL OR RU.ID_ROL = :rol)",
            nativeQuery = true)
    long countClientesWithRoleFilter(@Param("rol") int rol);

    @Query(value = "SELECT COUNT(C.ID) FROM CLIENTE C INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (RU.ID_ROL = 1 OR RU.ID_ROL = 2) " +
            "AND ((:query IS NULL OR C.NOMBRE LIKE %:query%) OR C.APELLIDOS LIKE %:query% OR NOMBRE_USUARIO LIKE %:query%)",
            nativeQuery = true)
    long countClientesWithRole(@Param("query") String query);

    @Query(value = "SELECT COUNT(C.ID) FROM CLIENTE C INNER JOIN ROLES_USUARIO RU ON C.ID = RU.ID_USUARIO " +
            "WHERE (RU.ID_ROL = 3) AND " +
            "((:query IS NULL OR C.NOMBRE LIKE %:query%) OR C.APELLIDOS LIKE %:query% OR NOMBRE_USUARIO LIKE %:query%)",
            nativeQuery = true)
    long countCliente(@Param("query") String query);

}