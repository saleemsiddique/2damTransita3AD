package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Set<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findByDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    @Override
    public Optional<Cliente> findById(long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente addCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Set<Cliente> findByNombreStartingWith(String nombre) {
        return clienteRepository.findByNombreStartingWith(nombre);
    }

    @Override
    public Set<Cliente> findByApellidoStartingWith(String apellido) {
        return clienteRepository.findByApellidoStartingWith(apellido);
    }

    @Override
    public Cliente findByNumeroTelefono(String numeroTelefono) {
        return clienteRepository.findByNumeroTelefono(numeroTelefono);
    }

    @Override
    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public Cliente modifyCliente(long id, Cliente newCliente) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        newCliente.setId(cliente.getId());
        return clienteRepository.save(newCliente);
    }

    @Override
    public void deleteCliente(long id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        clienteRepository.deleteById(id);
    }
}
