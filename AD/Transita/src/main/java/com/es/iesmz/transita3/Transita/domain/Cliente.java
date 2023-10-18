package com.es.iesmz.transita3.Transita.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "cliente",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "nombreUsuario"),
      @UniqueConstraint(columnNames = "email") 
    })
public class Cliente {
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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "roles_usuario",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol"))
  private Set<Rol> rols = new HashSet<>();

  public Cliente() {
  }

  public Cliente(String nombreUsuario, String email, String contrasenya) {
    this.nombreUsuario = nombreUsuario;
    this.email = email;
    this.contrasenya = contrasenya;
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
    return rols;
  }

  public void setRoles(Set<Rol> rols) {
    this.rols = rols;
  }
}
