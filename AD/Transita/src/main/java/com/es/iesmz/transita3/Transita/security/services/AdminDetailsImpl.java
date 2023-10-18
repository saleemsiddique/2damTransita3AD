package com.es.iesmz.transita3.Transita.security.services;

import com.es.iesmz.transita3.Transita.domain.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AdminDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nombreUsuario;

    private String email;

    private int telefono;

    @JsonIgnore
    private String contrasenya;

    private Collection<? extends GrantedAuthority> authorities;

    public AdminDetailsImpl(Long id, String nombreUsuario, String email, int telefono, String contrasenya,
                              Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.telefono = telefono;
        this.contrasenya = contrasenya;
        this.authorities = authorities;
    }

    public static AdminDetailsImpl build(Admin admin) {
        List<GrantedAuthority> authorities = admin.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre().name()))
                .collect(Collectors.toList());

        return new AdminDetailsImpl(
                admin.getId(),
                admin.getNombreUsuario(),
                admin.getEmail(),
                admin.getTelefono(),
                admin.getContrasenya(),
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

    public int getTelefono(){
        return telefono;
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
        AdminDetailsImpl adminDetails = (AdminDetailsImpl) o;
        return Objects.equals(id, adminDetails.id);
    }
}
