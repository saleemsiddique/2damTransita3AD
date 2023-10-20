package com.es.iesmz.transita3.Transita.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClienteSignupRequest {
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

    private Set<String> rol;

    @NotBlank
    @Size(min = 6, max = 40)
    private String contrasenya;
}