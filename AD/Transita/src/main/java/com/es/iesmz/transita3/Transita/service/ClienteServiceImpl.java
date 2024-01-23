package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.ECliente;
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
    public Set<Cliente> findAllByPages(int idInicial, int idFinal) {
        return clienteRepository.findAllByPages(idInicial, idFinal);
    }

    @Override
    public Set<Cliente> findAllByPagesFiltrado(int idInicial, int idFinal, int estado) {
        return clienteRepository.findAllByPagesFiltrado(idInicial, idFinal, estado);
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
    public Set<Cliente> findByRoleAdminOrModerator() {
        return clienteRepository.findByRoleAdminOrModerator();
    }

    @Override
    public Set<Cliente> findByRoleUsuario() {
        return clienteRepository.findByRoleUsuario();
    }

    @Override
    public Set<Cliente> findByRoleUsuarioAndEstadoDesactivado() {
        return clienteRepository.findByRoleUsuarioAndEstadoDesactivado();
    }

    @Override
    public Set<Cliente> findByRoleUsuarioAndEstadoActivado() {
        return clienteRepository.findByRoleUsuarioAndEstadoActivado();
    }




    @Override
    public Set<Cliente> findByRole(int rol) {
        return clienteRepository.findByRole(rol);
    }

    @Override
    public long getNumeroClientesFiltrados(int estado) {
        return clienteRepository.countClienteConFiltros(estado);
    }

    @Override
    public long countCliente() {
        return clienteRepository.countCliente();
    }

    @Override
    public long countUsuarioMunicipioFiltrado(int rol) {
        return clienteRepository.countClientesWithRoleFilter(rol);
    }

    @Override
    public long countUsuarioMunicipio() {
        return clienteRepository.countClientesWithRole();
    }

    @Override
    public Set<Cliente> findByNombreUsuarioStartingWith(String nombreUsuario) {
        return  clienteRepository.findByNombreUsuarioStartingWith(nombreUsuario);
    }

    @Override
    public Set<Cliente> findByEstadoCuenta(ECliente estadoCliente) {
        return clienteRepository.findByEstadoCuenta(estadoCliente);
    }

    @Override
    public Set<Cliente> findUsuarioMunicipioWithFilter(int rol, int idInicial, int idFinal) {
        return clienteRepository.findUsuarioMunicipioWithFilter(rol, idInicial, idFinal);
    }

    @Override
    public Set<Cliente> findUsuarioMunicipioWithRowNum(int idInicial, int idFinal) {
        return clienteRepository.findUsuarioMunicipioWithRowNum(idInicial, idFinal);
    }

    @Override
    public Set<Cliente> searchUser(int idInicial, int idFinal, Integer estado, String nombre, String apellidos, String nombreUsuario) {
        return clienteRepository.searchClientesFiltradoPages(idInicial, idFinal, estado, nombre, apellidos, nombreUsuario);
    }

    @Override
    public Cliente findByNombreUsuario(String nombreUsuario) {
        return clienteRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public Cliente modifyCliente(long id, Cliente newCliente) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        newCliente.setId(cliente.getId());
        return clienteRepository.save(newCliente);
    }
    @Override
    public Cliente modifyClienteEstado(long id, Cliente newCliente) {
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
