package com.es.iesmz.transita3.Transita.payload.request;

import javax.validation.constraints.NotBlank;

public class AdminLoginRequest {
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String contrasenya;

    @NotBlank
    private int telefono;

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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
