package com.es.iesmz.transita3.Transita.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String nombreUsuario;
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken, Long id, String nombreUsuario, String email, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.nombreUsuario = nombreUsuario;
    this.email = email;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public List<String> getRoles() {
    return roles;
  }
}
