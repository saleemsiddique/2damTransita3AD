package com.es.iesmz.transita3.Transita.security.services;

import com.es.iesmz.transita3.Transita.domain.UsuarioMunicipio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsuarioMunicipioDetailsImpl {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    @JsonIgnore
    private String contrasenya;
    private String telefono;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioMunicipioDetailsImpl(Long id, String nombre, String apellidos, String nombreUsuario, String contrasenya, String telefono, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
        this.telefono = telefono;
        this.authorities = authorities;
    }

    public static UsuarioMunicipioDetailsImpl build(UsuarioMunicipio usuarioMunicipio){
        List<GrantedAuthority> authorities = usuarioMunicipio.getRols().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre().name())).collect(Collectors.toList());
        return new UsuarioMunicipioDetailsImpl(
                usuarioMunicipio.getId(),
                usuarioMunicipio.getNombre(),
                usuarioMunicipio.getApellidos(),
                usuarioMunicipio.getNombreUsuario(),
                usuarioMunicipio.getContrasenya(),
                usuarioMunicipio.getTelefono(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
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
    public String getTelefono(){return telefono;}

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
        UsuarioMunicipioDetailsImpl user = (UsuarioMunicipioDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
