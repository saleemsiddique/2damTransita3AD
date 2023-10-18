package com.es.iesmz.transita3.Transita.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Set;


public class ClienteSignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String nombreUsuario;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> rol;

  @NotBlank
  @Size(min = 6, max = 40)
  private String contrasenya;

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

  public Set<String> getRol() {
    return this.rol;
  }

  public void setRol(Set<String> rol) {
    this.rol = rol;
  }
}
