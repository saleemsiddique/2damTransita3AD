package com.es.iesmz.transita3.Transita.domain;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.Incidencia;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Punto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Punto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Schema(description = "ID del punto", example = "1", required = true)
    private long id;

    @Column(name = "Descripción")
    @Schema(description = "Descripción del punto", example = "Punto de referencia en la carretera", required = true)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo")
    @Schema(description = "Tipo del punto", example = "ACCESO", required = true)
    private TipoPunto tipoPunto;

    @Column(name = "Foto")
    @Schema(description = "Foto del punto", example = "punto.jpg", required = true)
    private String foto;

    @Column(name = "Latitud")
    @Schema(description = "Latitud del punto", example = "40.7128", required = true)
    private double latitud;

    @Column(name = "Longitud")
    @Schema(description = "Longitud del punto", example = "-74.0060", required = true)
    private double longitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "Accesibilidad")
    @Schema(description = "Accesibilidad del punto", example = "ACCESIBLE", required = true)
    private AccesibilidadPunto accesibilidadPunto;

    @Enumerated(EnumType.STRING)
    @Column(name = "Visibilidad")
    @Schema(description = "visibilidad del punto", example = "GLOBAL", required = true)
    private EVisibilidad visibilidadPunto;

    @JsonIgnore
    @OneToMany(mappedBy = "punto", cascade = CascadeType.ALL)
    private List<Incidencia> incidencias;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "favoritos",
            joinColumns = @JoinColumn(name = "id_punto"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Cliente> clientes = new HashSet<>();


}
