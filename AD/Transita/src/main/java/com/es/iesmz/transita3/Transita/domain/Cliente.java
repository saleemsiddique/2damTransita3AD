package com.es.iesmz.transita3.Transita.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "numero_telefono")
    private String numeroTelefono;

    @Column(name = "email")
    private String email;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contrasenya")
    private String contrasenya;

    /*@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Incidencia> Incidencias = new ArrayList<>();*/
}
