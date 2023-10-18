package com.es.iesmz.transita3.Transita.security.services;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsuarioDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String nombreUsuario;

  private String email;

  @JsonIgnore
  private String contrasenya;

  private Collection<? extends GrantedAuthority> authorities;

  public UsuarioDetailsImpl(Long id, String nombreUsuario, String email, String contrasenya,
                            Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.nombreUsuario = nombreUsuario;
    this.email = email;
    this.contrasenya = contrasenya;
    this.authorities = authorities;
  }

  public static UsuarioDetailsImpl build(Cliente cliente) {
    List<GrantedAuthority> authorities = cliente.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getNombre().name()))
        .collect(Collectors.toList());

    return new UsuarioDetailsImpl(
        cliente.getId(),
        cliente.getNombreUsuario(),
        cliente.getEmail(),
        cliente.getContrasenya(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return contrasenya;
  }

  @Override
  public String getUsername() {
    return nombreUsuario;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UsuarioDetailsImpl user = (UsuarioDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
