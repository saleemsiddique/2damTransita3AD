package com.es.iesmz.transita3.Transita.domain;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity

@Table (name="zona")


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Zona {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonProperty("id")
        @Schema(description = "ID de Zona", example = "1", required = true)
        private Long id;

        @NotBlank
        @Size(max = 20)
        @Column(name = "nombre")
        @Schema(description = "Nombre de Zona", example = " Parque Central", required = true)
        private String nombre;


        @JsonIgnore
        @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL)
        private List<Punto> puntos;
}
