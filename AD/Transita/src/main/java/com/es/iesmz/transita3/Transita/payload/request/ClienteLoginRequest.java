package com.es.iesmz.transita3.Transita.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClienteLoginRequest {
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String contrasenya;
}