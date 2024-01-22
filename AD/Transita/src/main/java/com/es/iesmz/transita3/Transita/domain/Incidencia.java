package com.es.iesmz.transita3.Transita.domain;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "ID de la incidencia", example = "1", required = true)
    private long id;

    @Column(name = "Descripcion")
    @Schema(description = "Descripción de la incidencia", example = "Reporte de incidente en la carretera", required = true)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado")
    @Schema(description = "Estado de la incidencia", example = "ABIERTA", required = true)
    private EstadoIncidencia estado;

    @Column(name = "Duracion")
    @Schema(description = "Duración de la incidencia", example = "2 horas", required = true)
    private String duracion;

    @Column(name = "FechaHora")
    @Schema(description = "Fecha", example = "2023-10-27", required = true)
    private LocalDate fechaHora;

    @Column(name = "Fotos")
    @Lob
    @Schema(description = "Fotos relacionadas con la incidencia", example = "ruta/de/la/foto.jpg", required = true)
    private String fotos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punto_id")
    private Punto punto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
