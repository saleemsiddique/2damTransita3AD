package com.es.iesmz.transita3.Transita.payload.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UsuarioMunicipioLoginRequest {
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String contrasenya;
}
