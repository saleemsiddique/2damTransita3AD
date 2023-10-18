package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByNombreUsuario(String username);

    Boolean existsByNombreUsuario(String username);

    Boolean existsByEmail(String email);

    Boolean existsByTelefono(int telefono);
}