package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Cliente;

import java.util.Optional;
import java.util.Set;

public interface ClienteService {

    Set<Cliente> findAll();

    Optional<Cliente> findById(long id);
    Cliente addCliente(Cliente cliente);
    Set<Cliente> findByNombreStartingWith(String nombre);
    Set<Cliente> findByApellidoStartingWith(String apellido);
    Cliente findByEmail(String email);
    Cliente modifyCliente(long id, Cliente newCliente);
    void deleteCliente(long id);
}
