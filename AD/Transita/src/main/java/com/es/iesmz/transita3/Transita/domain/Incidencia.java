package com.es.iesmz.transita3.Transita.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
