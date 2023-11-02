package com.es.iesmz.transita3.Transita.service;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.UsuarioMunicipio;

import java.util.Optional;
import java.util.Set;

public interface UsuarioMunicipioService {
    Set<UsuarioMunicipio> findAll();
    Optional<UsuarioMunicipio> findById(long id);
    UsuarioMunicipio findByTelefono(String telefono);
    UsuarioMunicipio addUsuarioMunicipio(UsuarioMunicipio usuarioMunicipio);
    Set<UsuarioMunicipio> findByNombreStartingWith(String nombre);
    Set<UsuarioMunicipio> findByApellidoStartingWith(String apellido);
    Set<UsuarioMunicipio> findByNombreUsuarioStartingWith(String nombreUsuario);

    UsuarioMunicipio findByNombreUsuario(String email);
    UsuarioMunicipio modifyUsuarioMunicipio(long id, UsuarioMunicipio UsuarioMunicipio);
    void deleteUsuarioMunicipio(long id);
}
