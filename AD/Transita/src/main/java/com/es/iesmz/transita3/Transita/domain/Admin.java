package com.es.iesmz.transita3.Transita.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nombreUsuario"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "telefono")
        })
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String nombreUsuario;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String contrasenya;

    @NotBlank
    @Size(max = 9)
    private int telefono;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "roles_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Rol> roles = new HashSet<>();

    public Admin() {
    }

    public Admin(String nombreUsuario, String email, String contrasenya, int telefono) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenya = contrasenya;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> rols) {
        this.roles = rols;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
