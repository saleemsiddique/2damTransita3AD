package com.es.iesmz.transita3.Transita.payload.response;

import com.es.iesmz.transita3.Transita.domain.ECliente;
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
    private ECliente estado;
    private List<String> roles;

    public JwtResponse(String token, Long id, String nombre, String apellidos, String nombreUsuario, String contrasenya, ECliente estado, List<String> roles) {
        this.token = token;
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
        this.estado = estado;
        this.roles = roles;
    }
}