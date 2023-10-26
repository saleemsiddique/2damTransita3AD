package com.es.iesmz.transita3.Transita.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "incidencia")
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Descripción")
    private String descripcion;
    @Column(name = "Estado")
    private EstadoIncidencia estado;
    @Column(name = "Duracion")
    private int duracion;
    @Column(name = "FechaHora")
    private LocalDate FechaHora;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Punto punto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cliente cliente;

}
