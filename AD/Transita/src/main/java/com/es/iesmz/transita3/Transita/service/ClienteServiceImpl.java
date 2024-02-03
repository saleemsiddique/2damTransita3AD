package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.Utils.EmailUtil;
import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.ECliente;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Set;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public Set<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Set<Cliente> findAllByPages(int idInicial, int idFinal, String query) {
        return clienteRepository.findAllByPages(idInicial, idFinal, query);
    }

    @Override
    public Set<Cliente> findAllByPagesFiltrado(int idInicial, int idFinal, int estado, String query) {
        return clienteRepository.findAllByPagesFiltrado(idInicial, idFinal, estado, query);
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
    public long getNumeroClientesFiltrados(int estado, String query) {
        return clienteRepository.countClienteConFiltros(estado, query);
    }

    @Override
    public long countCliente(String query) {
        return clienteRepository.countCliente(query);
    }

    @Override
    public long countUsuarioMunicipioFiltrado(int rol) {
        return clienteRepository.countClientesWithRoleFilter(rol);
    }

    @Override
    public long countUsuarioMunicipio(String query) {
        return clienteRepository.countClientesWithRole(query);
    }

    @Override
    public String setPassword(String email) {
        Cliente cliente = clienteRepository.findByNombreUsuario(email);

        // Generar una contraseña aleatoria
        String newPassword = generateRandomPassword();

        cliente.setContrasenya(encoder.encode(newPassword));
        clienteRepository.save(cliente);

        try {
            emailUtil.sendSetPasswordEmail(email, newPassword);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send set password email. Please try again.");
        }

        return "New password set successfully. Check your email for the new password.";
    }


    // Método para generar una contraseña aleatoria
    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!_";
        int length = 12; // Longitud de la contraseña

        StringBuilder newPassword = new StringBuilder();

        // Uso de SecureRandom para mayor seguridad
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            newPassword.append(characters.charAt(index));
        }

        return newPassword.toString();
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
    public Set<Cliente> findUsuarioMunicipioWithFilter(int rol, int idInicial, int idFinal, String query) {
        return clienteRepository.findUsuarioMunicipioWithFilter(rol, idInicial, idFinal, query);
    }

    @Override
    public Set<Cliente> findUsuarioMunicipioWithRowNum(int idInicial, int idFinal, String query) {
        return clienteRepository.findUsuarioMunicipioWithRowNum(idInicial, idFinal, query);
    }

    @Override
    public Set<Cliente> searchUser(int idInicial, int idFinal, Integer estado, String query) {
        return clienteRepository.searchClientesFiltradoPages(idInicial, idFinal, estado, query);
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
