package com.es.iesmz.transita3.Transita.service;


import com.es.iesmz.transita3.Transita.domain.UsuarioMunicipio;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.repository.UsuarioMunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
@Service
public class UsuarioMunicipioServiceImpl implements UsuarioMunicipioService{
    @Autowired
    private UsuarioMunicipioRepository usuarioMunicipioRepository;

    public Set<UsuarioMunicipio> findAll() {
        return usuarioMunicipioRepository.findAll();
    }

    @Override
    public Optional<UsuarioMunicipio> findById(long id) {
        return usuarioMunicipioRepository.findById(id);
    }

    @Override
    public UsuarioMunicipio addUsuarioMunicipio(UsuarioMunicipio usuarioMunicipio) {
        return usuarioMunicipioRepository.save(usuarioMunicipio);
    }

    @Override
    public Set<UsuarioMunicipio> findByNombreStartingWith(String nombre) {
        return usuarioMunicipioRepository.findByNombreStartingWith(nombre);
    }

    @Override
    public Set<UsuarioMunicipio> findByApellidoStartingWith(String apellido) {
        return usuarioMunicipioRepository.findByApellidoStartingWith(apellido);
    }

    @Override
    public Set<UsuarioMunicipio> findByNombreUsuarioStartingWith(String nombreUsuario) {
        return  usuarioMunicipioRepository.findByNombreUsuarioStartingWith(nombreUsuario);
    }

    @Override
    public UsuarioMunicipio findByTelefono(String telefono){
        return usuarioMunicipioRepository.findByTelefono(telefono);
    }

    @Override
    public UsuarioMunicipio findByNombreUsuario(String nombreUsuario) {
        return usuarioMunicipioRepository.findByNombreUsuario(nombreUsuario);
    }


    public UsuarioMunicipio modifyUsuarioMunicipio(long id, UsuarioMunicipio newUsuarioMunicipio) {
        UsuarioMunicipio user = usuarioMunicipioRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        newUsuarioMunicipio.setId(user.getId());
        return usuarioMunicipioRepository.save(newUsuarioMunicipio);
    }


    public void deleteUsuarioMunicipio(long id) {
        usuarioMunicipioRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        usuarioMunicipioRepository.deleteById(id);
    }
}
