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

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "email")
    private String email;

    @Column(name = "contrasenya")
    private String contrasenya;


















    /*@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Incidencia> Incidencias = new ArrayList<>();*/
}
