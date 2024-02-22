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
    String atributos = " c.id, c.apellidos, c.contrasenya, c.estado, c.nombre, c.nombre_usuario ";
    String atributosRank = " rankedpoints.id, rankedpoints.apellidos, rankedpoints.contrasenya," +
            " rankedpoints.estado, rankedpoints.nombre, rankedpoints.nombre_usuario ";
    Set<Cliente> findAll();
    Set<Cliente> findByNombre(String nombre);
    Set<Cliente> findByApellidos(String apellido);
    Set<Cliente> findByEstadoCuenta(ECliente estadoCliente);
    Cliente findByNombreUsuario(String nombreUsuario);
    Boolean existsByNombreUsuario(String nombreUsuario);


    @Query(value = "select "+atributosRank+" from (select"+atributos+", row_number() over (order by c.id) as rownum " +
            "from cliente c " +
            "inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where ru.id_rol = 3 and ((:query is null or c.nombre like %:query%) or c.apellidos like %:query% or c.nombre_usuario like %:query%) ) as rankedpoints " +
            "where rankedpoints.rownum between :idinicial and :idfinal",
            nativeQuery = true)
    Set<Cliente> findAllByPages(@Param("idinicial") int idInicial, @Param("idfinal") int idFinal, @Param("query") String query);


    @Query(value = "select "+atributosRank+" from (select"+atributos+", row_number() over (order by c.id) as rownum " +
            "from cliente c " +
            "inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (:estado is null or c.estado = :estado) " +
            "and ru.id_rol = 3 and ((:query is null or c.nombre like %:query% or c.apellidos like %:query% or c.nombre_usuario like %:query%)) ) as rankedpoints " +
            "where rankedpoints.rownum between :idinicial and :idfinal",
            nativeQuery = true)
    Set<Cliente> findAllByPagesFiltrado(@Param("idinicial") int idInicial, @Param("idfinal") int idFinal, @Param("estado") int estado, @Param("query") String query);

    @Query(value = "select "+atributosRank+" from (select "+atributos+", row_number() over (order by c.id) as rownum " +
            "from cliente c " +
            "inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (:estado is null or c.estado = :estado) " +
            "and ru.id_rol = 3 " +
            "and ((:query is null or c.nombre like %:query%) or c.apellidos like %:query% or nombre_usuario like %:query%) ) as rankedpoints " +
            "where rankedpoints.rownum between :idinicial and :idfinal",
            nativeQuery = true)
    Set<Cliente> searchClientesFiltradoPages(
            @Param("idinicial") int idInicial,
            @Param("idfinal") int idFinal,
            @Param("estado") Integer estado,
            @Param("query") String query);


    @Query(value = "select "+ atributos +" from cliente " +
            "where " +
            "    if (:parametro = 'id', cliente.id = :valor, 1)" +
            "    and if (:parametro = 'nombre', cliente.nombre = :valor, 1)" +
            "    and if (:parametro = 'apellidos', cliente.apellidos = :valor, 1) " +
            "    and if (:parametro = 'nombre_usuario', cliente.nombre_usuario = :valor, 1);"
            , nativeQuery = true)
    Set<Cliente> findByFiltro(@Param("parametro") String parametro,
                              @Param("valor") String valor);






    @Query(value = "select "+atributos+" from cliente c where c.nombre like %:nombre%", nativeQuery = true)
    Set<Cliente> findByNombreStartingWith(@Param("nombre") String nombre);

    @Query(value = "select "+atributos+" from cliente c where c.apellidos like %:apellido%", nativeQuery = true)
    Set<Cliente> findByApellidoStartingWith(@Param("apellido") String apellido);

    @Query(value = "select "+atributos+" from cliente c where c.nombre_usuario like %:nombreUsuario%", nativeQuery = true)
    Set<Cliente> findByNombreUsuarioStartingWith(@Param("nombreUsuario") String nombreUsuario);

    @Query(value = "select "+atributos+" from cliente c inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where ru.id_rol = 1 or ru.id_rol = 2", nativeQuery = true)
    Set<Cliente> findByRoleAdminOrModerator();

    @Query(value = "select "+atributosRank+" from (select"+atributos+", row_number() over (order by c.id) as rownum " +
            "from cliente c " +
            "inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (:rol is null or ru.id_rol = :rol) and ((:query is null or c.nombre like %:query%) or c.apellidos like %:query% or nombre_usuario like %:query%)) as rankedpoints " +
            "where rankedpoints.rownum between :idinicial and :idfinal",
            nativeQuery = true)
    Set<Cliente> findUsuarioMunicipioWithFilter(
            @Param("rol") int rol,
            @Param("idinicial") int idInicial,
            @Param("idfinal") int idFinal,
            @Param("query") String query);



    @Query(value = "select "+atributosRank+" from (select"+atributos+", row_number() over (order by c.id) as rownum " +
            "from cliente c " +
            "inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (ru.id_rol = 1 or ru.id_rol = 2) and ((:query is null or c.nombre like %:query%) or c.apellidos like %:query% or nombre_usuario like %:query%)) as rankedpoints " +
            "where rankedpoints.rownum between :idinicial and :idfinal",
            nativeQuery = true)
    Set<Cliente> findUsuarioMunicipioWithRowNum(
            @Param("idinicial") int idInicial,
            @Param("idfinal") int idFinal,
            @Param("query") String query);


    @Query(value = "select "+atributos+" from cliente c inner join roles_usuario ru on c.id = ru.id_usuario where ru.id_rol = 3", nativeQuery = true)
    Set<Cliente> findByRoleUsuario();

    @Query(value = "select "+atributos+" from cliente c inner join roles_usuario ru on c.id = ru.id_usuario where ru.id_rol = 3 and c.estado = 0", nativeQuery = true)
    Set<Cliente> findByRoleUsuarioAndEstadoDesactivado();

    @Query(value = "select "+atributos+" from cliente c inner join roles_usuario ru on c.id = ru.id_usuario where ru.id_rol = 3 and c.estado = 1", nativeQuery = true)
    Set<Cliente> findByRoleUsuarioAndEstadoActivado();

    @Query(value = "select "+atributos+" from cliente c inner join roles_usuario ru on c.id = ru.id_usuario where ru.id_rol=:rol", nativeQuery = true)
    Set<Cliente> findByRole(@Param("rol")int rol);

    @Query(value = "select count(c.id) from cliente c " +
            "inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (:estado is null or c.estado = :estado) " +
            "and ru.id_rol = 3 " +
            "and ((:query is null or c.nombre like %:query%) or c.apellidos like %:query% or nombre_usuario like %:query%)",
            nativeQuery = true)
    long countClienteConFiltros(@Param("estado") int estado, @Param("query") String query);



    @Query(value = "select count(c.id) from cliente c inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (:rol is null or ru.id_rol = :rol)",
            nativeQuery = true)
    long countClientesWithRoleFilter(@Param("rol") int rol);

    @Query(value = "select count(c.id) from cliente c inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (ru.id_rol = 1 or ru.id_rol = 2) " +
            "and ((:query is null or c.nombre like %:query%) or c.apellidos like %:query% or nombre_usuario like %:query%)",
            nativeQuery = true)
    long countClientesWithRole(@Param("query") String query);

    @Query(value = "select count(c.id) from cliente c inner join roles_usuario ru on c.id = ru.id_usuario " +
            "where (ru.id_rol = 3) and " +
            "((:query is null or c.nombre like %:query%) or c.apellidos like %:query% or nombre_usuario like %:query%)",
            nativeQuery = true)
    long countCliente(@Param("query") String query);

}
