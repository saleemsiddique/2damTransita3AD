package com.es.iesmz.transita3.Transita.payload.response;

import lombok.*;

import java.util.List;

@Data
@Getter
@ToString
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    private String contrasenya;
    private List<String> roles;

    public JwtResponse(String token, Long id, String nombre, String apellidos, String nombreUsuario, String contrasenya, List<String> roles) {
        this.token = token;
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
        this.roles = roles;
    }
}