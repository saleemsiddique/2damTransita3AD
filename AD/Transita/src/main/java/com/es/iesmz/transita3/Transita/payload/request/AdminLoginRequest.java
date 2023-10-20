package com.es.iesmz.transita3.Transita.payload.request;

import javax.validation.constraints.NotBlank;

public class AdminLoginRequest {
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String contrasenya;

}
