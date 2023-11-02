package com.es.iesmz.transita3.Transita.security.services;

import com.es.iesmz.transita3.Transita.domain.UsuarioMunicipio;
import com.es.iesmz.transita3.Transita.repository.UsuarioMunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuarioMunicipioDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioMunicipioRepository usuarioMunicipioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        UsuarioMunicipio user = usuarioMunicipioRepository.findByNombreUsuario(nombreUsuario);

        return UsuarioMunicipioDetailsImpl.build(user);
    }
}
