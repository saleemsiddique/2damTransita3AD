package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.ERole;
import com.es.iesmz.transita3.Transita.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(ERole nombre);

}