package com.es.iesmz.transita3.Transita.repository;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Optional<Cliente> findByNombreUsuario(String username);

  Boolean existsByNombreUsuario(String username);

  Boolean existsByEmail(String email);
}
