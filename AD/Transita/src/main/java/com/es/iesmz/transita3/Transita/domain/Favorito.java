package com.es.iesmz.transita3.Transita.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Favorito")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Schema(description = "ID del favorito", example = "1", required = true)
    private long id;

    @Column(name = "Latitud")
    @Schema(description = "Latitud del favorito", example = "40.71A28", required = true)
    private double latitud;

    @Column(name = "Longitud")
    @Schema(description = "Longitud del favorito", example = "-74.0060", required = true)
    private double longitud;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @Schema(description = "ID del cliente asociado al favorito", example = "1", required = true)
    private Cliente cliente;
}
