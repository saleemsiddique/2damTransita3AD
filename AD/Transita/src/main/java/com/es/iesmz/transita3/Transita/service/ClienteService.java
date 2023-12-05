package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.ECliente;

import java.util.Optional;
import java.util.Set;

public interface ClienteService {

    Set<Cliente> findAll();
    Set<Cliente> findAllByPages(int idInicial, int idFinal);
    Set<Cliente> findAllByPagesFiltrado(int idInicial, int idFinal, int estado);
    Optional<Cliente> findById(long id);
    Cliente addCliente(Cliente cliente);
    Set<Cliente> findByNombreStartingWith(String nombre);
    Set<Cliente> findByApellidoStartingWith(String apellido);
    Set<Cliente> findByNombreUsuarioStartingWith(String nombreUsuario);
    Set<Cliente> findByEstadoCuenta(ECliente estadoCliente);

    Cliente findByNombreUsuario(String email);
    Cliente modifyCliente(long id, Cliente newCliente);
    Cliente modifyClienteEstado(long id, Cliente newCliente);
    void deleteCliente(long id);

    Set<Cliente> findByRoleAdminOrModerator();
    Set<Cliente> findByRoleUsuario();

    Set<Cliente> findByRoleUsuarioAndEstadoDesactivado();

    Set<Cliente> findByRoleUsuarioAndEstadoActivado();
    Set<Cliente> findByRole(int rol);
    long getNumeroClientesFiltrados(int estado);
    long countCliente();
}
