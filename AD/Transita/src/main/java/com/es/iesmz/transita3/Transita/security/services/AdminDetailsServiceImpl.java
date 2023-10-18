package com.es.iesmz.transita3.Transita.security.services;

import com.es.iesmz.transita3.Transita.domain.Admin;
import com.es.iesmz.transita3.Transita.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese nombre de usuario: " + nombreUsuario));

        return AdminDetailsImpl.build(admin);
    }

}
