package com.es.iesmz.transita3.Transita.payload.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClienteModificarContrasenyaRequest {

    @NotBlank
    @Size(min = 6, max = 40)
    private String contrasenya;

}



