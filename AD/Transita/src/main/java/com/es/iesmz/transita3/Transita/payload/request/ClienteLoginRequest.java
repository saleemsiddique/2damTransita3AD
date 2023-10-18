package com.es.iesmz.transita3.Transita.payload.request;

import javax.validation.constraints.NotBlank;


public class ClienteLoginRequest {
	@NotBlank
  private String nombreUsuario;

	@NotBlank
	private String contrasenya;

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
}
