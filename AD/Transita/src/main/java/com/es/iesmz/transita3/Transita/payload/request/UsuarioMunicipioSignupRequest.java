package com.es.iesmz.transita3.Transita.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UsuarioMunicipioSignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 20)
    private String apellidos;

    @NotBlank
    @Size(max = 50)
    @Email
    private String nombreUsuario;

    @NotBlank
    @Size(max = 20)
    private String telefono;

    private Set<String> rol;

    @NotBlank
    @Size(min = 6, max = 40)
    private String contrasenya;
}
