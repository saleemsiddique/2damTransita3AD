package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.ECliente;

import java.util.Optional;
import java.util.Set;

public interface ClienteService {

    Set<Cliente> findAll();
    Set<Cliente> findAllByPages(int idInicial, int idFinal, String query);
    Set<Cliente> findAllByPagesFiltrado(int idInicial, int idFinal, int rol, String query);
    Optional<Cliente> findById(long id);
    Cliente addCliente(Cliente cliente);
    Set<Cliente> findByNombreStartingWith(String nombre);
    Set<Cliente> findByApellidoStartingWith(String apellido);
    Set<Cliente> findByNombreUsuarioStartingWith(String nombreUsuario);
    Set<Cliente> findByEstadoCuenta(ECliente estadoCliente);
    Set<Cliente> findUsuarioMunicipioWithFilter(int rol, int idInicial, int idFinal, String query);
    Set<Cliente> findUsuarioMunicipioWithRowNum(int idInicial, int idFinal, String query);

    Set<Cliente> searchUser(int idInicial, int idFinal, Integer estado, String query);

    Cliente findByNombreUsuario(String email);
    Cliente modifyCliente(long id, Cliente newCliente);
    Cliente modifyClienteEstado(long id, Cliente newCliente);
    void deleteCliente(long id);

    Set<Cliente> findByRoleAdminOrModerator();
    Set<Cliente> findByRoleUsuario();

    Set<Cliente> findByRoleUsuarioAndEstadoDesactivado();

    Set<Cliente> findByRoleUsuarioAndEstadoActivado();

    Set<Cliente> findByRole(int rol);
    long getNumeroClientesFiltrados(int estado, String query);
    long countCliente(String query);
    long countUsuarioMunicipioFiltrado(int rol);
    long countUsuarioMunicipio(String query);

}
